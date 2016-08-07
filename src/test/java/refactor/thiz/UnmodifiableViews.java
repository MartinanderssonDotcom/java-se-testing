package refactor.thiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class UnmodifiableViews
{
    @Test(expected = UnsupportedOperationException.class)
    public void arraysAsListReturnsUnmodifiableView() {
        String[] a = new String[]{"hello", "world"};
        List<String> l = Arrays.asList(a);
        
        l.add("!"); // <-- Is the exception thrower.
    }
    
    @Test
    public void arraysAsListReturnsLiveView() {
        String[] a = new String[]{"hello", "world"};
        List<String> l = Arrays.asList(a);
        
        assertEquals("hello", l.get(0));
        
        a[0] = "blabla";
        assertEquals("blabla", l.get(0));
    }
    
    @Test
    public void collectionsUnmodifiableViewIsLiveToo() {
        List<String> org = new ArrayList<>();
        org.add("hello");
        
        List<String> umodifiable = Collections.unmodifiableList(org);
        
        assertEquals("hello", umodifiable.get(0));
        
        org.clear();
        assertEquals(0, umodifiable.size());
    }
    
    @Test
    public void listToArrayReturnsCopy() {
        List<String> org = new ArrayList<>();
        org.add("hello");
        
        String[] arrCopy = org.toArray(new String[0]);
        
        assertEquals(1, arrCopy.length);
        assertEquals("hello", arrCopy[0]);
        
        // Modify array (allowed of course):
        arrCopy[0] = "world";
        
        // But does not affect org. list:
        assertEquals(1, org.size());
        assertEquals("hello", org.get(0));
        
        // And modifications to original list does not affect copy:
        org.clear();
        assertEquals("world", arrCopy[0]);
    }
}
