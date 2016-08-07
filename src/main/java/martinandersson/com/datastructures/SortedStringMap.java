package martinandersson.com.datastructures;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class SortedStringMap
{
    private static final Map<String, String> NOT_SORTED = new HashMap<>();
    
    private static final SortedMap<String, String> SORTED = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    
    
    
    public static void main(String... ignored)
    {
//        String maxChar1 = new String("B" + Character.MAX_VALUE);
//        String maxChar2 = new String("B" + Character.MAX_VALUE);
//        
//        out.println(maxChar1.equals(maxChar2));    // true
//        out.println(maxChar1.compareTo(maxChar2)); // 0
//        out.println("B".compareTo(maxChar2));      // -1
//        out.println(maxChar2.compareTo("B"));      // +1
//        
//        out.println("B".compareTo("C")); // -1
//        out.println("C".compareTo("B")); // +1
//        
//        out.println("\"B\".compareTo(\"Be\")" + "B".compareTo("Be")); // -1 (B lower than Be)
//        
//        out.println();
        
        populateWithDummyData(NOT_SORTED);
        List<String> matches = dumbSearch("be");
        printAll("Dumb search using regular Map", matches);
        
        // Okay to use .submap(), but submap cannot match Character.MAX_VALUE, toKey is exclusive!
        populateWithDummyData(SORTED);
        
        // Note that BERit-value is not retained when using the String ignore case comparator!
        printAll("All keys before smart search:", new ArrayList<>(SORTED.values()));
        out.println();
        
        matches = smartSearch("be");
        printAll("Smart search 1 using sorted Map", matches);
        
        SORTED.clear();
        
        // ALL KEYS after "be" are returned:
        populateWithDummyData(SORTED);
        matches = getTailMap("be");
        printAll("Smart search 2 using sorted Map", matches);
    }
    
    private static void populateWithDummyData(Map<String, String> map) {
        // In alphabetic order:
        
        String[][] keyValues1 = {
            {"Anna", "Anna-value"},
            {"Ba", "Ba-value"},
            {"Be", "Be-value"},
            {"Berit", "Berit-value"},
            {"BERit", "BERit-value"},
            {"Bertil", "Bertil-value"},
            {"B" + Character.MAX_VALUE, "B" + Character.MAX_VALUE + "-value"}, // Work with: ((char) (Character.MAX_VALUE - 1))
            {"Ceasar", "Ceasar-value"},
            {"Martin", "Martin-value"}
        };
        
        List<String[]> keyValues2 = Arrays.asList(keyValues1);
        Collections.shuffle(keyValues2);
        
        keyValues2.stream().forEach(strArr ->
            map.put(strArr[0], strArr[1]));
    }
    
    private static void printAll(String printTitle, List<String> values) {
        out.println(printTitle);
        
        values.stream().forEach(out::println);
        
        out.println();
        out.println();
    }
    
    private static List<String> dumbSearch(String query) {
        List<String> matches = new ArrayList<>();
        
        NOT_SORTED.forEach((k, v) -> {
            if (k.toLowerCase().startsWith(query)) {
                matches.add(v);
            }
        });
        
        return matches;
    }
    
    private static List<String> smartSearch(String query) {
        // http://stackoverflow.com/a/13531376/1268003
        Collection<String> matches = SORTED.subMap(query, query + Character.MAX_VALUE).values();
        
        // WARNING: Don't use tailmap, it returned all the tail including Ceasar!
        
        return matches instanceof List ?
                (List) matches :
                new ArrayList<>(matches);
    }
    
    private static List<String> getTailMap(String query) {
        Collection<String> matches = SORTED.tailMap(query).values();
        
        return matches instanceof List ?
                (List) matches :
                new ArrayList<>(matches);
    }
}