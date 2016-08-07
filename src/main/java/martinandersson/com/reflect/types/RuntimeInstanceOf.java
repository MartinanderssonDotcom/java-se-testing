package martinandersson.com.reflect.types;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class RuntimeInstanceOf
{
    public static void main(String... ignored) {
        System.out.println(Object.class.isInstance("Hello"));            // true, or: String instanceof Object
        System.out.println(Object.class.isInstance(new Object()));       // true, or: Object instanceof Object
        System.out.println(Object.class.isAssignableFrom(String.class)); // true, or: Object x = "";
        System.out.println(Object.class.isAssignableFrom(Object.class)); // true, or: Object x = new Object();
        
        System.out.println(String.class.isInstance(new Object()));       // false, or: Object IS NOT instanceof String
        System.out.println(String.class.isInstance(new String()));       // true,  or: String instanceof String
        System.out.println(String.class.isAssignableFrom(Object.class)); // false, or: Can NOT "String s = new Object()"
        System.out.println(String.class.isAssignableFrom(String.class)); // true,  or: String s = "";
        
        
        System.out.println(Object.class.isInstance(null)); // false (hence, just as instanceof)
        System.out.println(Exception.class.isInstance(new RuntimeException())); // true
    }
}