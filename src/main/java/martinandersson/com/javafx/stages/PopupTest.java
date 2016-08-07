package martinandersson.com.javafx.stages;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * Demonstrates a dumb popup. Lesson learned: Popup is a "window" without a
 * window. No decoration, is transparent, null fill. Only the contents as-is!
 * Also, the popup's content "float" and is unaffected by dragging around or
 * resizing the owner.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class PopupTest extends Application
{
    public static void main(String... args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
        
        displayPopup(stage);
    }
    
    private void displayPopup(Stage owner) {
        Popup popup = new Popup();
        
        TextField input = new TextField();
        input.setPromptText("Enter something");
        
        Button button = new Button("Ok!");
        
        HBox box = new HBox();
        
        box.setPadding(new Insets(10, 20, 10, 20));
        box.setSpacing(10.);
        box.setAlignment(Pos.CENTER);
        
        box.getChildren().addAll(input, button);
        
//        popup.setScene(new Scene(box));
        
//        stage.setWidth(300);
//        stage.setHeight(100);
        
        popup.getContent().add(box);
        box.requestFocus();
        
        popup.show(owner);
        System.out.println("HEJDÅ");
    }
}