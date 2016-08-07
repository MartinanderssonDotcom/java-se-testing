package martinandersson.com.javafx.stages;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Will demonstrate a "popup" and how to make changes on owner window (using
 * instance fields in this case).
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class ResponseFromDialog extends Application
{
    public static void main(String... args) {
        Application.launch(args);
    }
    
    private Stage view;
    private Button button;

    @Override
    public void start(Stage stage) {
        view = stage;
        
        StackPane pane = new StackPane();
        pane.setPadding(new Insets(10, 25, 10, 25));
        stage.setScene(new Scene(pane));
        
        button = new Button("Click me!");
        button.setOnAction(this::popup);
        
        pane.getChildren().add(button);
        stage.show();
    }
    
    private void popup(ActionEvent e) {
        Stage dialog = new Stage(StageStyle.UNIFIED);
        dialog.setTitle("Surprise");
        
        HBox box = new HBox();
        Scene scene = new Scene(box);
        dialog.setScene(scene);
        
        TextField input = new TextField();
        input.setPromptText("Enter something!");
        
        Button okay = new Button("Okay!");
        
        box.getChildren().addAll(input, okay);
        box.setPadding(new Insets(10, 25, 10, 25));
        box.setSpacing(10.);
        
        okay.setOnAction(actionEvent -> {
            button.setText(input.getText());
            dialog.hide();
        });
        
        dialog.initOwner(view);
        dialog.initModality(Modality.WINDOW_MODAL);
        
        // Make the text field NOT steal focus so that prompt text is visible:
        Platform.runLater(() -> box.requestFocus());
        
        dialog.show();
    }
}