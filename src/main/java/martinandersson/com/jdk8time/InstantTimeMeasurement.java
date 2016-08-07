package martinandersson.com.jdk8time;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class InstantTimeMeasurement
{
    public static void main(String... ignored) throws InterruptedException {
        Instant start = Instant.now();
        
        takeSomeRandomTime(TimeUnit.MILLISECONDS, 17, 17);
//        takeSomeRandomTime(TimeUnit.NANOSECONDS, 234, 234);
        
        Instant finished = Instant.now();
        
        printDifference(start, finished);
    }
    
    static void takeSomeRandomTime(TimeUnit timeUnit, int from, int to) throws InterruptedException {
        int x = new Random().nextInt(to - from + 1) + from;
        timeUnit.sleep(x);
    }
    
    static void printDifference(Instant start, Instant finished) {
        Duration time = Duration.between(start, finished);
        System.out.println("ms: " + time.toMillis());
    }
}