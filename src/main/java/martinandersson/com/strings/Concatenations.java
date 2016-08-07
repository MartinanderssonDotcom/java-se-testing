package martinandersson.com.strings;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class Concatenations
{
    public static void main(String... ignored) {
        // Compiler builds into one literal (JLS 8 section 15.28):
        String str = "1" + "2" + "3" + "4" + "5";
        
        // Compiled to: str = str + "6"
        str += "6";
        
        // Also compiled to: str = str + new Object().toString() + new Object();
        str += new Object().toString() + new Object();
        
        /*
         * The problem with the previous two statements is that people online, see:
         *     http://stackoverflow.com/a/14927864/1268003
         * 
         * ..(also see the Java Performance book somewhere) argue that these two
         * statements is compiler replaced to use a StringBuilder. Some refer to
         * JLS 8 section 15.8.1. Using JD-GUI 0.3.6, they didn't.
         */
    }
}