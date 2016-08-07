package martinandersson.com.numbers;

import java.text.DecimalFormat;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class DoubleToStringExamples
{
    private static final DecimalFormat DECIMAL_CUTTER = new DecimalFormat();
    
    public static void main(String... ignored) {
        // 3.33
        System.out.println(limitDecimals(2,
                3.3333333333333333333333333));
        
        // 33333.3
        System.out.println(limitDecimals(1,
                33333.3333333333333333333333333));
        
        // 0
        System.out.println(limitDecimals(0,
                .3333333333333333333333333));
        
        // 1234568
        System.out.println(limitDecimals(0,
                1234567.999999999));
        
        // 0.1234
        System.out.println(limitDecimals(4,
                .123444444449));
        
        // 5.38
        System.out.println(limitDecimals(2,
                5.38499999));
        
        // 5.38
        System.out.println(limitDecimals(2,
                -5.38499999));
        
        // 2
        System.out.println(limitDecimals(0,
                2.5));
        
        // -2
        System.out.println(limitDecimals(0,
                -2.5));
        
        // 2.5 is rounded to 2, but 5.5 is rounded to 6:
        System.out.println(limitDecimals(0,
                5.5));
    }
    
    /**
     * Uses {@code RoundingMode.HALF_EVEN}. JavaDoc says:
     * <pre>
     * 
     * Note that this is the rounding mode that statistically minimizes
     * cumulative error when applied repeatedly over a sequence of
     * calculations.
     * </pre>
     * 
     * @param decimals
     * @param val
     * 
     * @return 
     */
    private static String limitDecimals(int decimals, double val) {
        if (decimals < 0) {
            throw new IllegalArgumentException();
        }
        
        // Ex.: "#.##" (capped to 2 decimals)
        // If you wanted EXACTLY 2 decimals, then use 0 instead; "#.00"
        final String pattern = decimals == 0 ? "#" : "#." + IntStream.range(0, decimals)
                .mapToObj(x -> "#")
                .collect(Collectors.joining());
        
        return new DecimalFormat(pattern).format(val);
    }
}