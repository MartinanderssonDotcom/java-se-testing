package martinandersson.com.numbers;

import static java.lang.System.out;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class IntegerOverflow
{
    public static void main(String... args) {
        out.println("Integer max value: " + Integer.MAX_VALUE);
        out.println("Max value calculated: " + ( (long) ((Math.pow(2D, 32) / 2) - 1))); // In Swedish: (2^32 / 2) - 1
        out.println("Math.abs(-2147483648): " + Math.abs(-2147483648));
        out.println("Same as Integer.MAX_VALUE + 1: " + (Integer.MAX_VALUE + 1));
        
        // Prefer static compare to instead of mathematical expressions:
        int five = 5;
        int seven = 7;
        List<Integer> sorted = Arrays.asList(five, seven);
        Collections.sort(sorted, (first, second) -> first - second);
        out.println(sorted); // OK [5, 7]
        
        int negativeFive = -5;
        sorted = Arrays.asList(five, negativeFive);
        Collections.sort(sorted, (first, second) -> first - second);
        out.println(sorted); // OK [-5, 5]
        
        int superLow = Integer.MIN_VALUE;
        sorted = Arrays.asList(five, superLow);
        Collections.sort(sorted, (first, second) -> { out.println("first: " + first); out.println("second: " + second); return first - second; });
        out.println(sorted); // FAIL [5, -2147483648]
        
        Collections.sort(sorted, Integer::compare);
        out.println(sorted); // OK [-2147483648, 5]
    }
}
