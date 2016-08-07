package martinandersson.com.strings;

import java.util.regex.Pattern;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class Matches {
    public static void main(String... ignored) {
        String hasWS = "ab ba";
        
        /*
         * False! The entire string must be matched. JavaDoc of
         * Matcher.matches() says "Attempts to match the entire region against
         * the pattern."
         */
        System.out.println("Has whitespace? " + hasWS.matches("\\s")); // FALSE
        
        // Matching only a subset? Compile pattern and use Matches.find().
        System.out.println("Has whitespace? " + Pattern.compile("\\s").matcher(hasWS).find());
    }
}