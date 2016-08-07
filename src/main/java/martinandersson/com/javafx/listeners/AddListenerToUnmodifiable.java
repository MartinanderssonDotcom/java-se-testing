package martinandersson.com.javafx.listeners;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;

/**
 * Will test whether or not a client can add a new listener to an unmodifiable
 * collection.<p>
 * 
 * Answer: Yes in the short run =) In the long run, NO. See my own question and
 * answer here: http://stackoverflow.com/q/25224170/1268003
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class AddListenerToUnmodifiable
{
    public static void main(String... ignored) {
        ObservableSet<String> strings = FXCollections.observableSet();
        
        strings.addListener((SetChangeListener<String>) change -> {
            System.out.println("Listener #1 caught: " + change.getElementAdded());
        });
        
        ObservableSet<String> unmodifiable = FXCollections.unmodifiableObservableSet(strings);
        
        unmodifiable.addListener((SetChangeListener<String>) change -> {
            System.out.println("Listener #2 caught: " + change.getElementAdded());
        });
        
        strings.add("Hello World!"); // <-- YES. Second listener is printed.
        
        // Using different thread to add:
        new Thread(() -> {
            strings.add("Hello says another thread."); // <-- yes. Both listeners catch this.
        }).start();
    }
}