package martinandersson.com.security;

import java.security.MessageDigest;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class DigestSingleChar
{
    public static void main(String... ignored) throws Exception {
        byte[] input = {'A'}; // one byte which is 65
        
        byte[] key = MessageDigest.getInstance("SHA-256").digest(input);
        System.out.println("Key length: " + key.length * 8); // 256!
        
        for (byte b : key) {
            System.out.print(b);
            System.out.print(" ");
        }
        
        System.out.println();
    }
}