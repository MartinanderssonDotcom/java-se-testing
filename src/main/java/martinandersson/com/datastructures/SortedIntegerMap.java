package martinandersson.com.datastructures;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class SortedIntegerMap
{
    public static void main(String... ignored) {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, Integer.MAX_VALUE);
        Collections.shuffle(integers);
        
        SortedMap<Integer, Integer> integerMapped = new TreeMap<>();
        
        for (Integer i : integers) {
            System.out.println("Putting: " + i);
            integerMapped.put(i, i);
        }
        
        System.out.println("1.compareTo(5): " +
                new Integer(1).compareTo(new Integer(5))); // -1 (1 lower than 5)
        
        // toKey is exclusive!
        
        System.out.println("Sorted values from 4 using TreeMap..");
        integerMapped.subMap(4, Integer.MAX_VALUE).values()
                .forEach(System.out::println);
    }
}