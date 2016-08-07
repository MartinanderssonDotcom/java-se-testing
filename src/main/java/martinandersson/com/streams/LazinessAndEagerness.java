package martinandersson.com.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Q: If I first create a stream out of a list, then add an element, then read
 * the stream - will my stream contain the element added after stream
 * construction?<p>
 * 
 * A: Yes.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class LazinessAndEagerness
{
    public static void main(String... ignored) {
        List<String> strings = new ArrayList<>();
        strings.add("first");
        
        Stream<String> stream = strings.stream();
        
        strings.add("second");
        
        // first, second..
        stream.forEach(System.out::println);
    }
}