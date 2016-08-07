package martinandersson.com.trycatchfinally;

/**
 * We saw in {@linkplain NonworkingTryReturnThenFinally} that traversing the
 * reference and change state of returned object shows. However, the reference
 * may be mutated.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class WorkingTryReturnThenFinally
{
    static String hello = "Hello";
    
    public static void main(String... ignored) {
        // Prints "Hello"
        System.out.println(getHello());
        
        // Prints null
        System.out.println(hello);
    }
    
    private static String getHello() {
        try {
            return hello;
        }
        finally {
            hello = null;
        }
    }
}