package martinandersson.com.strings;

/**
 * Parsing input string to integers.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class ParseInt
{
    public static void main(String... ignored) {
        String testee = "123-456";
        
        try {
            int result = Integer.parseInt(testee);
        }
        catch (NumberFormatException e) {
            System.out.println("Integer.parseInt(\"123-456\") causes NumberFormatException. Doesn't work as in JavaScript!");
        }
        
        int result = Integer.parseInt(testee.split("-")[0]);
        
        System.out.println("Result: " + result); // 123
    }
}