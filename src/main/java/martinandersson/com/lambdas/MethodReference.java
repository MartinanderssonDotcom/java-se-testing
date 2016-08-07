package martinandersson.com.lambdas;

import java.util.Arrays;
import java.util.function.IntFunction;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class MethodReference
{
    public static void main(String... ignored)
    {
        /*
         * Decompiled the code using http://www.benf.org/other/cfr/ to:
         *     "newTypedArray = x$0 -> new String[x$0]"
        */
        IntFunction<String[]> newTypedArray = String[]::new;
        
        String[] strings = Arrays.asList("cp", "skadad").stream().toArray(newTypedArray);
    }
    
    public static <T> void typed()
    {
        // This does not compile at all:
//        IntFunction<String[]> newTypedArray = T[]::new;
        
        /*
         * Conclusion: Any motherfucker who says lambdas allow generic array creation can go to hell.
        */
    }
}