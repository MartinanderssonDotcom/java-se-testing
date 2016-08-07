package martinandersson.com.images;

import java.util.stream.Stream;
import javax.imageio.ImageIO;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class SupportedImageFormats
{
    public static void main(String... ignored)
    {
        Stream.of(ImageIO.getReaderFormatNames()).forEach(System.out::println);
        /*
        
        JPG
        jpg
        bmp
        BMP
        gif
        GIF
        WBMP
        png
        PNG
        jpeg
        wbmp
        JPEG
        
        */
    }
}