package martinandersson.com.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class MapWithNulls
{
    public static void main(String... ignored) {
        List<String> strings = Arrays.asList( "Hello", null );
        
        strings.stream().map(s -> { return s == "Hello"? "Got something" : null;})
                .forEach(System.out::println); // <-- null was cascaded into the new stream and into the output!
        
        strings.stream().map(s -> { return s == "Hello"? "Got something" : null;})
                .filter(Objects::nonNull)
                .forEach(System.out::println); // <-- now it work
        
        
        // What if we ask to map to something that returns null?
        List<MappableTo> mappables = Arrays.asList( new MappableTo(new Object()), new MappableTo(null) );
        
        // Prints object.. null.
        mappables.stream().map(MappableTo::getTo).forEach(System.out::println);
    }
    
    static class MappableTo
    {
        Object to;
        
        MappableTo(Object to) {
            this.to = to;
        }
        
        Object getTo() {
            return to;
        }
    }
}