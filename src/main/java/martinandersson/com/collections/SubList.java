package martinandersson.com.collections;

import static java.lang.System.out;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Examines List.sublist().
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class SubList
{
    public static void main(String... ignored) {
        List<Integer> ints = Arrays.asList(0, 1, 2);
        
        // fromIndex is "inclusive", toIndex is "exclusive", but.. they can be
        // the same. In which case an empty list is returned (JavaDoc says).
        
        // All empty:
        out.println(ints.subList(1, 1));
        out.println(ints.subList(2, 2));
        
        // I can specify an inclusive fromIndex which obviously doesn't exist (index 3):
        out.println(ints.subList(3, 3));
        
        // I can specify an exclusive toIndex which obviously doesn't exist (index -1):
        out.println(ints.subList(0, 0));
        
        // If "from 3" is correct, I would imagine there's an element there, but no:
        try {
            out.println(ints.subList(3, 4));
        }
        catch (IndexOutOfBoundsException e) {
            out.println("Go figure, \"from 3\" is incorrect after all.");
        }
        
        // if "to 0" is correct, I would imagine there's an element there, but no:
        try {
            out.println(ints.subList(-1, 0));
        }
        catch (IndexOutOfBoundsException e) {
            out.println("Go figure, \"to 0\" is incorrect after all.");
        }
        
        // So if we want to peek what's left in a tail, how do we do it?
        final int lastIndex = ints.size() - 1;
        for (int i = 0; i < ints.size(); ++i) {
            out.print(ints.get(i) + ". What's left? ");
            out.println(i == lastIndex ? Collections.emptyList() : ints.subList(i + 1, ints.size()));
        }
    }
}