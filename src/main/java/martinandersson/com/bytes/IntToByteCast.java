package martinandersson.com.bytes;

/**
 * Read and write methods of Input- OutputStream accept/return int, which is
 * an unsigned byte. Casting this unsigned version to (byte) does exactly what
 * we want to.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class IntToByteCast
{
    public static void main(String[] args) {
        byte signedByte = -1;
        int unsignedByte = signedByte & (0xff);
        
        // Signed: -1 Unsigned: 255
        System.out.println("Signed: " + signedByte + " Unsigned: " + unsignedByte);
        
        int maxUnsignedByte = 255;
        byte signedMaxByte = (byte) maxUnsignedByte;
        
        // signedMaxByte: -1
        System.out.println("signedMaxByte: " + signedMaxByte);
    }
}