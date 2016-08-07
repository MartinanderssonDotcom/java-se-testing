package martinandersson.com.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Examines string joining.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class StringJoining
{
    public static void main(String... ignored) {
        List<String> names = Arrays.asList("One", "Two");
        
        String result = names.stream().collect(Collectors.joining(", "));
        
        System.out.println("Result: " + result); // "One, Two"
        
        // How do Collectors.joining() handle null? We expect string "null".
        String s1 = "NonNull";
        String s2 = null;
        
        String merged = Stream.of(s1, s2).collect(Collectors.joining());
        System.out.println(merged); // NonNullnull (expectation hold)
    }
}