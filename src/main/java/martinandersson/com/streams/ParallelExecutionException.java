package martinandersson.com.streams;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Runtime exceptions in parallel streams.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class ParallelExecutionException
{
    private static long clientId;
    
    private static final AtomicBoolean THROW_EX = new AtomicBoolean();
    
    private static final LongAdder ELEMS_PROCESSED = new LongAdder();
    
    
    
    public static void main(String... ignored) {
        clientId = Thread.currentThread().getId();
        System.out.println("Client thread Id: " + clientId);
        
        
        
        /*
         * Iterate through tons of numbers, in parallel, let first thread that
         * is not the one executing main() throw a runtime exception only once.
         * 
         * A bad IntStream impl. could suppress this exception and just continue
         * processing elements. I find that Oracle's JDK re-throw the exception
         * wrapped in another RuntimeException.
         */
        
        try (IntStream ints = IntStream.range(0, 1_000_000_000)) {
            ints.parallel()
                .forEach(ParallelExecutionException::crashAndBurn);
            
            System.out.println("IntStream didn't throw anything.");
        }
        catch (RuntimeException e) {
            System.out.println("Stack trace from IntStream.");
            e.printStackTrace(System.out);
        }
        
        // "34 535 163" on my machine.
        System.out.println("IntStream processed this many elements: " +
                ELEMS_PROCESSED.sumThenReset());
        
        
        
        /*
         * Take two uses a "normal" Stream. The results are a bit strange
         * though.
         * 
         * Had crashAndBurn() not thrown an exception. We would see that the
         * following Stream processes exactly 1 000 000 000 elements. If - as
         * the code currently do - crashAndBurn() throw the exception, then..
         * 
         * 1) The client thread will never become aware of it. You'll see in the
         * console that "Stream didn't throw anything." is printed. But,
         * "Throwing RuntimeException from.." is also printed.
         * 
         * You will also find that:
         * 
         * 2) The actual amount of elements processed is even greater than the
         * limit. On my machine: 1 203 762 296.
         * 
         * 3) The RuntimeException thrown is completely "lost". Not even
         * re-thrown when the stream is closed.
         */
        
        THROW_EX.set(false);
        
        AtomicInteger i = new AtomicInteger();
        
        try (Stream<Integer> ints = Stream.generate(i::getAndIncrement)) {
            ints.limit(1_000_000_000)
                .parallel()
                .forEach(ParallelExecutionException::crashAndBurn);
            
            System.out.println("Stream didn't throw anything.");
        }
        catch (RuntimeException e) {
            System.out.println("Stack trace from Stream.");
            e.printStackTrace(System.out);
        }
        
        // "1 284 790 946" on my machine.
        System.out.println("Stream processed this many elements: " +
                ELEMS_PROCESSED.sumThenReset());
    }
    
    private static void crashAndBurn(int i) {
        long myId = Thread.currentThread().getId();
        
        if (i >= 1_000_000 && myId != clientId && THROW_EX.compareAndSet(false, true)) {
            System.out.println("Throwing RuntimeException from " + myId);
            
            throw new RuntimeException(
                    "Crashing thread Id: " + myId);
        }
        
        ELEMS_PROCESSED.increment();
    }
}