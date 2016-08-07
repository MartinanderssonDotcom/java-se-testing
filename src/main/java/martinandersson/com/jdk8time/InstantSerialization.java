package martinandersson.com.jdk8time;

import static java.lang.System.out;
import java.time.Instant;

/**
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class InstantSerialization
{
    public static void main(String... ignored) {
        Instant now = Instant.now();
        
        String serialized = now.toString();
        out.println("serialized: \"" + serialized + "\".");
        
        Instant deserialized = Instant.parse(serialized);
        out.println("deserialized: " + deserialized);
        
        out.println("Is equal? " + now.equals(deserialized));
    }
 
}