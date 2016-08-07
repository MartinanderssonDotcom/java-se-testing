package martinandersson.com.streams;

import static java.lang.System.out;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import martinandersson.com.streams.Person.Sex;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class ParallelGroupingBy
{
    public static void main(String... ignored) {
        Set<Person> persons = createPersons(10_000_000);
        
        long then = System.nanoTime();
        
        Map<Sex, List<Person>> mapped = persons.stream().parallel()
                .collect(Collectors.groupingByConcurrent(Person::getGender));
        
        // 1462 ms
        out.println("groupingByConcurrent(): " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - then));
        out.println("Impl: " + mapped.getClass()); // java.util.concurrent.ConcurrentHashMap
        
        then = System.nanoTime();
        
        mapped = persons.stream()
                .collect(Collectors.groupingBy(Person::getGender));
        
        // 822 ms (optimization - whatever runs last executes fastest)
        out.println("groupingBy(): " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - then));
        out.println("Impl: " + mapped.getClass()); // java.util.HashMap
        

    }
    
    private static Set<Person> createPersons(int sizePerGender) {
        Set<Person> persons = new HashSet<>();
        
        for (int i = 0; i < sizePerGender; ++i) {
            persons.add(new Person(i % 2 == 0 ? Sex.MALE : Sex.FEMALE));
        }
        
        return persons;
    }
}

class Person
{
    enum Sex { MALE, FEMALE }
    
    private final Sex gender;
    
    Person(Sex gender) {
        this.gender = gender;
    }
    
    public Sex getGender() {
        return gender;
    }
}