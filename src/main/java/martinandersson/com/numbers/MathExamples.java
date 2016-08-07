package martinandersson.com.numbers;

import static java.lang.System.out;

/**
 * Math examples.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class MathExamples
{
    public static void main(String... ignored) {
        double d1 = 100.675,
               d2 = 100.500,
               d3 = 100.499;
        
        out.println(Math.round(d1)); // 101
        out.println(Math.round(d2)); // 101
        out.println(Math.round(d3)); // 100
    }
}