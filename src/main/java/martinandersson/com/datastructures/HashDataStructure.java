package martinandersson.com.datastructures;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class HashDataStructure
{
    public static void main(String... ignored) {
        KeyValue one = new KeyValue();
        one.value = 1;
        
        KeyValue two = new KeyValue();
        two.value = 2;
        
        Set<KeyValue> keyValueSet = new HashSet<>();
        Map<KeyValue, KeyValue> keyValueMap = new HashMap<>();
        
        // Goes to same bucket but are not equal:
        keyValueSet.add(one);
        keyValueSet.add(two);
        keyValueSet.stream().forEach(e ->
                System.out.println("KeyValue from Set: " + e)); // <-- Both are contained. True that.
        
        // Has the exact same hashcode but we still expect the mapping to be correct:
        keyValueMap.put(one, one);
        keyValueMap.put(two, two);
        
        keyValueMap.forEach((k, v) ->
                System.out.println("KEY: " + k + " VALUE: " + v) );
    }
}

final class KeyValue
{
    int value;
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof KeyValue &&
               value == ((KeyValue) obj).value;
    }
    
    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}