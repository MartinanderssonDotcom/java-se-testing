package martinandersson.com.loops;

import static java.lang.System.out;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class UsingWhileInsteadOfFor
{
    public static void main(String... ignored) {
        int n = 3;
        
        while (n-- > 0) {
            out.println("Hello!"); // Prints three times.
        }
    }
}