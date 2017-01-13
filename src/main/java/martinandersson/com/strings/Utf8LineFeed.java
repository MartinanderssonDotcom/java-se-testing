package martinandersson.com.strings;

import java.nio.charset.StandardCharsets;

/**
 * Verifies that UTF-8 encoding of {@code '\n'} equals both {@code 1010}
 * (binary) and Java's UTF-16 char representation.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class Utf8LineFeed
{
    public static void main(String... ignored) {
        char lf1 = '\n';
        
        byte lf2 = 0b1010;
        
        byte[] bytes = "\n".getBytes(StandardCharsets.UTF_8);
        
        dump(bytes);
        
        if (bytes.length != 1) {
            throw new AssertionError();
        }
        
        // => true
        System.out.println("Char (Java's UTF-16) same as UTF-8 byte: " + (lf1 == bytes[0]));
        
        // => true
        System.out.println("UFT-8 \\n == 0b1010: " + (bytes[0] == lf2));
    }
    
    private static void dump(byte[] bytes) {
        System.out.print(bytes.length + " byte(s): ");
        
        for (byte b : bytes) {
            System.out.print(Integer.toBinaryString(b) + " (" + b + ")");
        }
        
        System.out.println();
    }
}