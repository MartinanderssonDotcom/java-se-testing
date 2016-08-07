package martinandersson.com.streams;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class ForEach_ConcurrentModification
{
    public static void main(String... args) {
        Set<String> myset = new HashSet<>();
        
        // Make everything a success:
//        myset = Collections.newSetFromMap(new ConcurrentHashMap<>());
        
        myset.add("hello1");
        myset.add("hello2");
        myset.add("hello3");
        myset.add("hello4");
        
        try {
            myset.stream().forEach(str -> {
                System.out.print("Set removing: " + str);
                myset.remove(str);
                System.out.println("..done.");
            });
        }
        catch (ConcurrentModificationException e) {
            System.out.println("HashSet.remove() threw ConcurrentModificationException..");
            e.printStackTrace(System.out);
        }
        
        // What's left? Nothing.. prints 0.
        System.out.println("myset.size(): " + myset.size());
        
        
        
        // Same for HashMap:
        Map<String, String> someMap = new HashMap<>();
        someMap.put("1", "1");
        someMap.put("2", "2");
        someMap.put("3", "3");
        someMap.put("4", "4");
        
        try {
            someMap.keySet().stream().forEach(key -> {
                System.out.print("HashMap removing: " + key);
                someMap.remove(key);
                System.out.println("..done.");
            });
        }
        catch (ConcurrentModificationException e) {
            System.out.println("HashMap.remove() threw ConcurrentModificationException..");
            e.printStackTrace(System.out);
        }
        
        // What's left? Nothing.. prints 0.
        System.out.println("someMap.size(): " + someMap.size());
    }
}