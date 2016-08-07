package martinandersson.com.streams;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Asking to map through a null element throw NPE.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class ForEach_NullPointerException
{
    public static void main(String... ignored) {
        String[] strings = { "hello", null };
        
        IntStream hashcodes = Arrays.stream(strings)
                .mapToInt(String::hashCode);
        
        hashcodes.forEachOrdered(i ->
                System.out.println("Got hash: " + i));
    }
    
}
