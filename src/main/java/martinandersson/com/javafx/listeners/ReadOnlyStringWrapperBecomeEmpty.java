package martinandersson.com.javafx.listeners;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

/**
 * When we register a change listener on a read only String property, is it
 * triggered when going from initial state of the property to having content,
 * from having content to the empty String, from empty String to null?<p>
 * 
 * Yes, all three times.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class ReadOnlyStringWrapperBecomeEmpty
{
    public static void main(String... ignored) {
        ReadOnlyStringWrapper wrapper = new ReadOnlyStringWrapper();
        ReadOnlyStringProperty prop = wrapper.getReadOnlyProperty();
        
        prop.addListener(observable ->
                System.out.println("CHANGE DETECTED! Current text: " + prop.get()));
        
        // InvalitationListener called all three times:
        wrapper.set("X");
        wrapper.set("");
        wrapper.set(null);
    }
}