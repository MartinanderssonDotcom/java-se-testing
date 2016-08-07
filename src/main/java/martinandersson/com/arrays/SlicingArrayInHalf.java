package martinandersson.com.arrays;

import java.util.Arrays;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class SlicingArrayInHalf
{
    public static void main(String... ignored) {
        byte[] four = {1, 2, 3, 4},
               five = {1, 2, 3, 4, 5},
               six  = {1, 2, 3, 4, 5, 6};
        
        printByteArray("four-half", firstHalfOf(four));
        printByteArray("four-last", lastHalfOf(four));
        
        printByteArray("five-half", firstHalfOf(five));
        printByteArray("five-last", lastHalfOf(five));
        
        printByteArray("six-half", firstHalfOf(six));
        printByteArray("six-last", lastHalfOf(six));
    }
    
    private static byte[] firstHalfOf(byte[] bytes) {
        return Arrays.copyOf(bytes, bytes.length / 2);
    }
    
    private static byte[] lastHalfOf(byte[] bytes) {
        return Arrays.copyOfRange(bytes, bytes.length / 2, bytes.length);
    }
    
    private static void printByteArray(String title, byte[] bytes) {
        System.out.println("Contents of " + title);
        
        for (byte b : bytes) {
            System.out.println("\t" + b);
        }
        
        System.out.println();
    }
}