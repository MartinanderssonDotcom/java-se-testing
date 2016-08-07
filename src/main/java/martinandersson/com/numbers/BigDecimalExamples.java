package martinandersson.com.numbers;

import static java.lang.System.out;
import java.math.BigDecimal;

/**
 * {@code BigDecimal} examples.<p>
 * 
 * Findings:<p>
 * 
 * Literal 1 versus 1.0D with or without trim makes no difference. Both is
 * integral and can produce int value exact.<p>
 * 
 * Literal 1.0 versus String "1.0" makes big difference. String make precision
 * go from 1 to 2 and scale from 0 to 1. Despite this, int value exact can be
 * fetched. Trim "reverse" the mistake and make literal and String instances
 * equivalent again. Albeit without trim, one is lead to believe this instance
 * is not integral.<p>
 * 
 * However, trim has no effect on "0.0".<br>
 * See: http://stackoverflow.com/questions/1078953/check-if-bigdecimal-is-integer-value
 * <p>
 * 
 * Solution is found in implementation of isIntegral_2().
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class BigDecimalExamples
{
    public static void main(String... ignored) {
        doubleConstructor();
        stringToBigDecimal();
    }
    
    private static void doubleConstructor() {
        final double before = .1;
        
        BigDecimal bd = new BigDecimal(before);
        
        double after = bd.doubleValue();
        
        System.out.println("Got back same double? " + (before == after));
        
        System.out.println(0.1 == 0.1000000000000000055511151231257827021181583404541015625);
    }
    
    
    private static void stringToBigDecimal() {
        print("int 1", new BigDecimal(1));
        
        print("double 1.0", new BigDecimal(1.0D));
        
        print("String 1", new BigDecimal("1"));
        
        print("String 1.0", new BigDecimal("1.0"));
        
        print("String 1.01", new BigDecimal("1.01"));
        
        print("double 1.01", new BigDecimal(1.01D));
        
        // Summary
        out.println("1.0 literal versus String 1.0, is different. Equal: " + new BigDecimal(1.0D).equals(new BigDecimal("1.0")));
        out.println();
        out.println();
        
        print("int 0", new BigDecimal(0));
        
        print("double 0.0", new BigDecimal(0.0D));
        
        print("String 0", new BigDecimal("0"));
        
        print("String 0.0", new BigDecimal("0.0"));
        
        print("String 0.01", new BigDecimal("0.01"));
        
        print("double 0.01", new BigDecimal(0.01D));
    }
    
    private static void print(String prefix, BigDecimal bd) {
        out.println("\"" + prefix + "\".toString(): " + bd);
        out.println("\"" + prefix + "\".precision(): " + bd.precision());
        out.println("\"" + prefix + "\".scale(): " + bd.scale());
        out.println();
        
        out.println("\"" + prefix + "\".strip.toString(): " + bd.stripTrailingZeros());
        out.println("\"" + prefix + "\".strip.precision(): " + bd.stripTrailingZeros().precision());
        out.println("\"" + prefix + "\".strip.scale(): " + bd.stripTrailingZeros().scale());
        out.println();
        
        out.println("\"" + prefix + "\" is integral 1: " + isIntegral_1(bd));
        out.println("\"" + prefix + "\" is integral 2: " + isIntegral_2(bd));
        out.println();
        
        try {
            out.println("\"" + prefix + ".intValueExact(): " + bd.intValueExact());
        }
        catch (RuntimeException e) {
            out.println("\"" + prefix + ".intValueExact(): " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
        
        out.println();
        out.println();
    }
    
    private static boolean isIntegral_1(BigDecimal bd) {
        // This is the implementation of JSONP's JsonNumber:
        // Impl class. JsonNumberImpl.JsonBigDecimalNumber
        return bd.scale() == 0;
    }
    
    private static boolean isIntegral_2(BigDecimal bd) {
        // Here's my version:
        return bd.signum() == 0 ||
               bd.scale() <= 0  ||
               bd.stripTrailingZeros().scale() == 0;
    }
}