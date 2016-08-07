package martinandersson.com.loops;

import java.util.stream.IntStream;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class UsingIntStream
{
    public static void main(String... ignored) {
        // Start inclusive, end exclusive, mean that.. 0, 1 and 2 is printed.
        IntStream.range(0, 3).forEach(System.out::println);
    }
}