package martinandersson.com.arrays;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class CastingArray
{
    public static void main(String... ignored) {
        String[] strings = {};
        
        // Don't even need cast:
        Object[] objects = strings;
        
        System.out.println("Survived String[] to Object[].");
        
        objects = new Object[0];
        
        try {
            strings = (String[]) objects;
        }
        catch (ClassCastException e) {
            System.out.println("Did NOT survive Object[] to String[].");
        }
    }
}