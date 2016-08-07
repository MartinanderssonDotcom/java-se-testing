package martinandersson.com.strings;

import java.util.StringJoiner;


/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class StringJoinerTest
{
    public static void main(String... ignored) {
        String one   = "one",
               two   = "two",
               three = "three";
        
        StringJoiner joiner = new StringJoiner(", ");
        
        joiner.add(one);
        joiner.add(two);
        joiner.add(three);
        
        System.out.println("Joined: " + joiner.toString());
    }
}
