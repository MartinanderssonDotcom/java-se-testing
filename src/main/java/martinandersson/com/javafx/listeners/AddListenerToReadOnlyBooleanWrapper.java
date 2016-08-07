package martinandersson.com.javafx.listeners;

import static java.lang.System.out;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;

/**
 * Can you add listener to the read-only view of a {@code ReadOnlyBooleanWrapper}?
 * Answer: Yes.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class AddListenerToReadOnlyBooleanWrapper
{
    public static void main(String... ignored) {
        ReadOnlyBooleanWrapper wrapper = new ReadOnlyBooleanWrapper();
        ReadOnlyBooleanProperty readOnly = wrapper.getReadOnlyProperty();
        
        // Both are printed
        wrapper.addListener(observable ->
                out.println("wrapper#InvalidationListener called.."));
        
        readOnly.addListener(observable ->
                out.println("readOnly#InvalidationListener called.."));
        
        wrapper.set(true);
    }
}