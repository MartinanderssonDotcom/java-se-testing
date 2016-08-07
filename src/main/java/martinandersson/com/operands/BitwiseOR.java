package martinandersson.com.operands;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class BitwiseOR
{
    public static void main(String... ignored) {
        boolean result = false;
        
        result |= false;
        System.out.println("1 result: " + result); // prints false
        
        result |= true;
        System.out.println("2 result: " + result); // prints true
        
        
        // But once it's true, the value will never change back to false:
        
        result |= false;
        System.out.println("3 result: " + result); // true!
        
        result |= true;
        System.out.println("4 result: " + result); // true
    }
    
}
