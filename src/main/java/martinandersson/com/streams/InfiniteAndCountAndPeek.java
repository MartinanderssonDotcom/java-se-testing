package martinandersson.com.streams;

import java.util.stream.Stream;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class InfiniteAndCountAndPeek
{
    public static void main(String... ignored) {
        Stream<Double> infinite = Stream.generate(Math::random);
        
        System.out.println("infinite.isParallel(): " + infinite.isParallel()); // false
        
        // Does not survive. All cores work much at the beginning, then just a little but indefinitely
//        System.out.println("infinite.count(): " + infinite.count());
        
        // Does not survive. All cores work much indefinitely
//        System.out.println("Will sort and get first..");
//        System.out.println("First: " + infinite.sorted().findFirst());
        
        
        System.out.println("infinite.limit(3).count(): " + infinite.limit(3).count()); // prints THREE as expected
        
        // Here, this is thrown: "java.lang.IllegalStateException: stream has already been operated upon or closed"
//        System.out.println("infinite stream is not mutated: " + infinite.count());
        
        Stream<String> duplicates = Stream.of("kalle", "kalle", "oskar");
        System.out.println("duplicates.distinct().count(): " + duplicates.distinct().count()); // prints 2 as expected
        
        String[] names = Stream.of("kalle", "anna", "oskar").peek(System.out::println).toArray(String[]::new);
        
        // The API says: "performing the provided action on each element as elements are consumed from the resulting stream". So:
        System.out.println("Peek printed the names before this line!");
        
        // ..and before element access of array:
        for (String name : names) {
            System.out.println("Read from array: " + name);
        }
    }
}
