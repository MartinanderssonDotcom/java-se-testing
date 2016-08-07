package martinandersson.com.random;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Q: Does ThreadLocalRandom produce same number sequence if executed in two
 * different run sessions (one program run, then another one)?<p>
 * 
 * A: No.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class ThreadLocalRandomTast
{
    public static void main(String... args) {
        int n = 10;
        
        while(n-- > 0) {
            System.out.println(ThreadLocalRandom.current().nextInt());
        }
        
        System.out.println();

        
        ThreadLocalRandom.current().ints()
                .limit(10)
                .forEach(System.out::println);
    }
}