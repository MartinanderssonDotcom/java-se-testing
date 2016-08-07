package martinandersson.com.datastructures;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class LiveView
{
    public static void main(String... ignored) {
        String[] strings = {"one", "two", "three"};
        
        List<String> asList = Arrays.asList(strings);
        
        try {
            asList.remove(0);
        }
        catch (UnsupportedOperationException e) {
            e.printStackTrace(); // prints
        }
        
        try {
            asList.add("Cp Skadad");
        }
        catch (UnsupportedOperationException e) {
            e.printStackTrace(); // prints
        }
        
        // "Write through" as per JavaDoc documentation really mean only one
        // thing, that you can change an element:
        
        asList.set(1, "XXX");
        
        out.println(Stream.of(strings).collect(Collectors.joining(", ", "[", "]"))); // [one, XXX, three]
        
        // Writes go to the live view too:
        strings[0] = "YYY";
        out.println(asList); // [YYY, XXX, three]
        
        
        
        List<String> asList2 = new ArrayList<>();
        asList2.add("one");
        asList2.add("two");
        asList2.add("three");
        
        String[] fromList = asList2.toArray(new String[3]);
        
        // Does NOT leak through:
        fromList[1] = "XXX";
        
        out.println(asList2); // [one, two, three]
        out.println();
        
        
        /*
         * What about sub lists? JavaDoc of List.subList() says:
         * 
         *     "The returned list is backed by this list, so non-structural
         *      changes in the returned list are reflected in this list, and
         *      vice-versa."
         * 
         * ..which implies that structural changes in the sublist are NOT
         * reflected in the parent. Same JavaDoc later constructs an example
         * using .clear() on the returned sublist, arguing that this is a valid
         * operation that cascades to the parent (!). Okay, so the JavaDoc is a
         * bit crazy. Turns out that sublist view returned from both ArrayList
         * and LinkedList allow and cascade structural modifications from
         * sublist to parent. Proof:
         */
        
        List<String> arrayList = new ArrayList<>();
        Stream.of("one", "two", "three").forEach(arrayList::add);
        List<String> lastElem = arrayList.subList(2, 3);
        out.println("Removed from sublist of ArrayList: " + lastElem.remove(0)); // three
        out.println("Is left in parent: " + arrayList); // [one, two]
        lastElem.add("three");
        out.println("After adding back three to sublist: " + arrayList); // [one, two, three]
        
        List<String> linkedList = new LinkedList<>();
        Stream.of("one", "two", "three").forEach(linkedList::add);
        lastElem = linkedList.subList(2, 3);
        out.println("Removed from sublist of LinkedList: " + lastElem.remove(0)); // three
        out.println("Is left in parent: " + linkedList); // [one, two]
        lastElem.add("three");
        out.println("After adding back three to sublist: " + arrayList); // [one, two, three]
    }
}