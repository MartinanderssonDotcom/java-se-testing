package martinandersson.com.exceptions;

import static java.lang.System.out;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class StackOverflowErrorTest
{
    private static int recursiveCallCount = 0;
    
    public static void main(String... ignored) {
        try {
            recursiveFunc1();
        }
        catch (StackOverflowError e) {
            e.printStackTrace(out);
            
            // "23032" on my machine.
            out.println("Managed to call the recursive function 1 this many times: " + recursiveCallCount);
        }
        
        recursiveCallCount = 0;
        
        // Q: Will an anonymous function that call enclosing function cause a StackOverflowError?
        // A: Yes.
        
        Runnable callback = new Runnable(){
            @Override public void run() {
                recursiveFunc2(this);
            }
        };
        
        try {
            recursiveFunc2(callback);
        }
        catch (StackOverflowError e) {
            e.printStackTrace(out);
            
            // "3844" on my machine.
            out.println("Managed to call the recursive function 2 this many times: " + recursiveCallCount);
        }
    }
    
    private static void recursiveFunc1() {
        ++recursiveCallCount;
        recursiveFunc1();
    }
    
    private static void recursiveFunc2(Runnable callback) {
        ++recursiveCallCount;
        callback.run();
    }
}