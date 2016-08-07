package martinandersson.com.streams;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class Limit
{
    public static void main(String... ignored) {
        String[] strings = {"first", "second"};
        
        boolean match = Arrays.stream(strings).limit(1).anyMatch(s -> s.equals("second"));
        
        System.out.println("If limit came first, could we find the second element? " + match); // false!!
        
        
        // Limit work on parallel streams to, no matter where we put the limit:
        IntStream.iterate(1, x -> ++x).limit(5).forEach(Limit::print);
        System.out.println("------");
        
        IntStream.iterate(1, x -> ++x).limit(5).parallel().forEach(Limit::print);
        System.out.println("------");
        
        IntStream.iterate(1, x -> ++x).parallel().limit(5).forEach(Limit::print);
        System.out.println("------");
        
        
        AtomicInteger seq = new AtomicInteger();
        
        // Don't add sorted() before limit() or the app will hang.
        // Sorted require him to look at all elements, which never end.
        // Also sorted() after limit() has no effect.
        // Numbers printed are out of order. Thusly, the AtomicInteger was called many times.
        Stream.generate(seq::incrementAndGet).limit(5).parallel().forEach(Limit::print);
    }
    
    private static void print(int number) {
        System.out.println("Thread " + Thread.currentThread().getId() + ": " + number);
    }
}