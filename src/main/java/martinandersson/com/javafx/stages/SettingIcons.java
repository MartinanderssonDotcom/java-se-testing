package martinandersson.com.javafx.stages;

import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Demonstrates setting window icons!
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class SettingIcons extends Application
{
    public static void main(String... args) {
        Application.launch(args);
    }
    
    private final static String[] ICON_URLS = {
        "http://icons.iconarchive.com/icons/kyo-tux/aeon/16/Apps-Java-icon.png",
        "http://icons.iconarchive.com/icons/kyo-tux/aeon/24/Apps-Java-icon.png",
        "http://icons.iconarchive.com/icons/kyo-tux/aeon/32/Apps-Java-icon.png",
        "http://icons.iconarchive.com/icons/kyo-tux/aeon/48/Apps-Java-icon.png",
        "http://icons.iconarchive.com/icons/kyo-tux/aeon/64/Apps-Java-icon.png",
        "http://icons.iconarchive.com/icons/kyo-tux/aeon/72/Apps-Java-icon.png",
        "http://icons.iconarchive.com/icons/kyo-tux/aeon/128/Apps-Java-icon.png"
    };

    @Override
    public void start(Stage stage) {
        ObservableList<Image> icons = stage.getIcons();
        System.out.println("Icons: " + icons); // originally empty!
        
        addAllIcons(icons);
        
        stage.show();
    }
    
    private void addAllIcons(List<Image> images) {
        Arrays.stream(ICON_URLS)
                .map(Image::new)
                .forEach(images::add);
    }
}