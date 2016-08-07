package martinandersson.com.strings;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class StringSplit
{
    private static final String WEIRD_TEXT =
            "Two-words Firsthand (SVVC) Prices, Dividends, weird chars \",\"description\":\"<p>End of day<x> open </x>, high, <x>low, close split/dividend adjusted open, high, Ex-Dividend 1 (<a href=\\\"http://www.crsp.com/products/documentation/crsp-calculations\\";
    
    public static void main(String... ignored) {
        String twoParts = "1-2";
        
        System.out.println(Arrays.asList(twoParts.split("-"))); // [1, 2]
        
        String[] parts = twoParts.split("-", 2);
        
        System.out.println("parts.length: " + parts.length); // 2
        System.out.println("parts[0]: " + parts[0]);         // 1
        System.out.println("parts[1]: " + parts[1]);         // 2
        
        String hasNoDelimiter = "123";
        parts = hasNoDelimiter.split("-", 2);
        
        System.out.println("parts.length: " + parts.length); // 1
        System.out.println("parts[0]: " + parts[0]);         // 123
        
        splitStringToWords();
        
        //-2 12
        printAllNumbers("There are more than -2 and less than 12 numbers here");
        
        // -2 1 12 6 7
        printAllNumbers("There are more than -2.1 and less than +12 numbers here 6\t7");
        
        // 1 2 4 4 5 6 7 8 9 10 11
        printAllNumbers("There are more than1 an2d x4ess ,4,than 5 numbers 6,7,8 9, 10, 11- here");
    }
    
    private static void splitStringToWords() {
        Arrays.stream(WEIRD_TEXT.split("\\b")).forEach(System.out::println);
    }
    
    // http://stackoverflow.com/a/2367418
    private static void printAllNumbers(String input) {
        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(input);
        while (m.find()) {
            System.out.print(m.group());
            System.out.print(" ");
        }
        
        System.out.println();
    }
}