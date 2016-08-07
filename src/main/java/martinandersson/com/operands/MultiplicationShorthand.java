package martinandersson.com.operands;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class MultiplicationShorthand
{
    public static void main(String... ignored) {
        double a = 0.5, b = 0.5;
        
        a *= b + 1;
        
        // The result is NOT a * b + 1 (1.25).
        // It is a * (b + 1)..
        System.out.println("a: " + a); // 0.75
        
        // ..same as:
        
        a = 0.5;
        a *= (b + 1);
        
        System.out.println("a: " + a); // 0.75
    }
}