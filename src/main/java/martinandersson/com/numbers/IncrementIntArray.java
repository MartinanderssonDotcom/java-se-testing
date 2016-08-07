package martinandersson.com.numbers;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class IncrementIntArray
{
    public static void main(String... ignored) {
        int[] counter = {0};
        counter[0]++;
        
        System.out.println(counter[0]); // 1
    }
}