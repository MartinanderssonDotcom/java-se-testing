
package martinandersson.com.datastructures;

import static java.lang.System.out;
import java.util.HashMap;
import java.util.Map;

/**
 * Be vary that Map.values() return a view that is backed by the real map and
 * changes of each write through.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class ModifyValueSet
{
    public static void main(String... ignored) {
        Map<String, String> theMap = new HashMap<>();
        theMap.put("hello", "world");
        
        theMap.values().remove("world");
        
        // Writes read through and vice versa:
        out.println(theMap.values()); // Prints [].. = EMPTY map
    }
}