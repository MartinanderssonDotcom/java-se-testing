package martinandersson.com.numbers;

/**
 * A double can be divided by 0. Result is infinity.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class DoubleExamples
{
    public static void main(String... ignored) {
        // Does not crash, prints "Infinity":
        double infinity = 123.123 / 0;
        
        System.out.println("Infinity is infinite: " + Double.isInfinite(infinity));
        System.out.println("infinity + 1: " + (infinity + 1));
        
        System.out.println("Infinity is NaN: " + Double.isNaN(infinity));
        System.out.println("Infinity from 0 div is less than or equal to zero: " + (infinity <= 0));
        System.out.println("Infinity from 0 div is greater than zero: " + (infinity > 0));
        
        double overflow = Double.MAX_VALUE + 1;
        System.out.println("Double does not overflow, is infinite: " + Double.isInfinite(overflow));
        
        System.out.println("Res is NaN: " + Double.isNaN(overflow));
        System.out.println("Res from overflow is less than or equal to zero: " + (overflow <= 0));
        System.out.println("Res from overflow is greater than zero: " + (overflow > 0));
        
        double x = 1 / 3;
        System.out.println("Integer division? " + x); // Yes.
        
        x = (double) 1 / 3;
        System.out.println("Integer division? " + x); // No.
    }
}