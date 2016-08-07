package martinandersson.com.classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Using anonymous class with a change listener might be tricky. Lesson
 * learned: Anonymous classes can use generic types and pass the type parameter.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class AnonymousClassTypeParameter
{
    public static void main(String... ignored) {
        GenericInterface<String> test = new GenericInterface<String>() {
            @Override public String repeat(String value) {
                return value;
            }
        };
        
        System.out.println(test.repeat("Work just fine!"));
        
        SimpleStringProperty prop = new SimpleStringProperty("default");
        
        /*
         * Look at the source code of ChangeListener. Type parameter to
         * INTERFACE is T. Type parameter to METHOD is ? extends String.
         */
        prop.addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("Changed from: " + oldValue + " to " + newValue);
            }
        });
        
        /*
         * What does NOT work is like with any class; use a wildcard in the
         * actual type parameter declaration!
         */
//        prop.addListener(new ChangeListener<? extends String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                System.out.println("Changed from: " + oldValue + " to " + newValue);
//            }
//        });
    }
}

interface GenericInterface<T> {
    T repeat(T value);
}
