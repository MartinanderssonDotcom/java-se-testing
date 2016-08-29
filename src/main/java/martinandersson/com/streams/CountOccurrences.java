package martinandersson.com.streams;

import java.util.Map;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import java.util.stream.Stream;
import static java.util.stream.Collectors.groupingBy;

/**
 * Count occurrences of Stream elements.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class CountOccurrences
{
    public static void main(String... ignored) {
        Stream<String> strings = Stream.of("A", "B", "B", "C", "C", "C");
        
        // How many times does the letters occur?
        Map<String, Long> occurrences
                = strings.collect(groupingBy(identity(), counting()));
        
        // Prints "{A=1, B=2, C=3}"
        System.out.println(occurrences);
    }
}