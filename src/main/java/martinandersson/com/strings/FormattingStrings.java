package martinandersson.com.strings;

import static java.lang.System.out;
import static java.text.MessageFormat.format;

/**
 * TODO:
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class FormattingStrings
{
    public static void main(String... ignored) {
        final double a = 1.1,
                     b = 2.2,
                     c = 3.3;
        
        // MessageFormat.format() work if the String is rather short.
        //   --> "A: 1,1. B: 2,2. C: 3,3."
        out.println(format("A: {0}. B: {1}. C: {2}.", a, b, c));
        
        // But not if you use a ' character:
        //  --> As value: {0}. B: {1}. C: {2}.
        out.println(format("A's value: {0}. B: {1}. C: {2}.", a, b, c));
        
        /*
         * MessageFormat has some weird issues with the single quote character.
         * It is used "to quote any arbitrary characters except single quotes"
         * and must be closed with another single quote. But this rule "isn't
         * always obvious to localizers".
         */
        
        // Solution (two single quotes):
        //   --> "A's value: 1,1. B: 2,2. C: 3,3."
        out.println(format("A''s value: {0}. B: {1}. C: {2}.", a, b, c));
        
        // Can take many arguments:
        out.println(format("{0} {1} {2} {3}", 1, 2, 3, 4));
        out.println(format("{0} {1} {2} {3} {4}", 1, 2, 3, 4, 5));
        out.println(format("{0} {1} {2} {3} {4} {5}", 1, 2, 3, 4, 5, 6));
    }
}