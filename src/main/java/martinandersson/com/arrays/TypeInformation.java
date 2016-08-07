package martinandersson.com.arrays;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class TypeInformation
{
    public static void main(String... ignored) {
        // [I@659e0bfd
        System.out.println("int[0].toString(): " + new int[0]);
        
        // [Ljava.lang.String;@2a139a55
        System.out.println("String[0].toString(): " + new String[0]);
    }
}