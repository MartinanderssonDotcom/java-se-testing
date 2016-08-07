package martinandersson.com.trycatchfinally;

import java.util.Arrays;

/**
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class NonWorkingTryReturnThenFinally
{
    char[] myCharArr;
    
    char[] getMyCharArr() {
        try {
            return myCharArr;
        }
        finally {
            Arrays.fill(myCharArr, 'x');
            myCharArr = null;
        }
    }
    
    public static void main(String... ignored) {
        NonWorkingTryReturnThenFinally testee = new NonWorkingTryReturnThenFinally();
        testee.myCharArr = "blabla".toCharArray();
        
        char[] returned = testee.getMyCharArr();
        
        // Returned, as expected, is "xxxxxx":
        System.out.println("Returned: " + String.valueOf(returned));
        
        // Prints:
        if (testee.myCharArr == null)
            System.out.println("Left in testee: null");
    }
}
