package martinandersson.com.numbers;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class FindIfDoubleToIntLostInformation
{
    public static void main(String[] args) {
        double x = Double.MAX_VALUE;
        
        int y = (int) x;
        
        boolean somethingWasLost = (double) y != x;
        
        // true
        System.out.println(somethingWasLost);
    }
}
