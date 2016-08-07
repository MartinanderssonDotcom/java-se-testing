package martinandersson.com.javafx.properties;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Can we mutate a ReadOnlyBooleanWrapper if we return the property as is to
 * client who receive a SimpleBooleanProperty? Answer: Yes. Always use
 * ReadOnlyBooleanWrapper.getReadOnlyProperty().
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class MutatingReadOnlyProperty
{
    public static void main(String... ignored) {
        ReadOnlyBooleanWrapper prop = new ReadOnlyBooleanWrapper();
        
        ReadOnlyBooleanProperty readOnly1 = prop.getReadOnlyProperty();
//        readOnly.set(); // API does not exist
        
        // ClassCastException, ReadOnlyPropertyImpl is not a SimpleBooleanProperty:
//        SimpleBooleanProperty casted = (SimpleBooleanProperty) readOnly;
//        casted.set(true);
        
        // If I leak to client code my wrapper which extend ReadOnlyBooleanProperty:
        ReadOnlyBooleanProperty readOnly2 = prop;
        
        // Then client code can cast to anything mutable:
        SimpleBooleanProperty casted = (SimpleBooleanProperty) readOnly2;
        casted.set(true);
        
        System.out.println("Wrapper value: " + prop.get());
    }
}
