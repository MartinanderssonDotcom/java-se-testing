package martinandersson.com.concurrent;

import static java.lang.System.out;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Concurrent counting.<p>
 * 
 * Unexpected: {@code LongAdder} is <i>faster</i> than POJO counter using
 * {@code N >= 3}.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class ConcurrentCounting
{
    /**
     * Wait-duration for locked counters before giving up.<p>
     * 
     * Timeout should only happen for tests under very high contention.
     */
    public static final long TIMEOUT = 20_000L;
    
    
    
    public static void main(String... ignored) throws InterruptedException {
        
        // Number of threads..
        final int N = Runtime.getRuntime().availableProcessors();
        
        // ..each incrementing the counter this many times
        final int M = 1_000_000;
        
        // Amount of warmups first:
        int W = 10;
        
        Supplier<Counter>[] counters = (Supplier<Counter>[]) new Supplier[]{
            UnsynchronizedCounter::new,
            SynchronizedCounter::new,
            VolatileCounter::new,
            WriteLockedCounter::new,
            ReadWriteLockedCounter::new,
            StampLockedCounter::new,
            CASCounter::new,
            ShardedCASCounter::new
        };
        
        while (W-- > 0)
            concurrentIncrementOf(N, M, shuffledCopy(counters));
        
        concurrentIncrementOf(N, M, counters).forEach(System.out::println);
    }
    
    static <T> T[] shuffledCopy(T[] array) {
        T[] copy = Arrays.copyOf(array, array.length);
        Arrays.sort(copy, (a, b) -> ThreadLocalRandom.current().nextBoolean() ? -1 : 1);
        return copy;
    }
    
    static List<ExecutionReport> concurrentIncrementOf(int threads, int iterations, Supplier<Counter>... counters) {
        return Stream.of(counters)
                .map(c -> concurrentIncrementOf(c.get(), threads, iterations))
                .collect(Collectors.toList());
    }
    
    static ExecutionReport concurrentIncrementOf(Counter counter, final int threads, final int iterations) {
        out.print("Setting up threads for " + counter.getClass().getSimpleName() + ".. ");
        
        Instant[] before = {null};
        
        CyclicBarrier ready = new CyclicBarrier(threads, () -> {
            out.println("done, executing..");
            before[0] = Instant.now();
        });
        
        CountDownLatch finished = new CountDownLatch(threads);
        
        IntStream.range(0, threads).forEach(x1 ->
        {
            new Thread(() -> {
                try {
                    ready.await();
                }
                catch (BrokenBarrierException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                
                IntStream.range(0, iterations).forEach(x2 -> counter.increment());
                
                finished.countDown();
            }).start();
        });
        
        final Instant after;
        
        try {
            finished.await();
            after = Instant.now();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        
        return new ExecutionReport(counter, threads * iterations, Duration.between(before[0], after));
    }
    
    /**
     * A report detailing the outcome of a concurrent execution of contained
     * counter.<p>
     * 
     * Note that reported elapsed time is only accurate for writes. This number
     * is only one of three factors weighed in when design a new software
     * architecture. Reads are equally important. Last factor is the ratio
     * between writer- and reader threads.<p>
     * 
     * Also note that many runs in random order producing an average, using a
     * system-specific estimate of writers/readers, has to be done before a
     * decision can be made.
     */
    private static class ExecutionReport {
        
        static String thousandGrouping(Number any) {
            return String.format("%,d", any);
        }
        
        final Counter counter;
        final long expectedCount;
        final Duration timeElapsed;
        
        ExecutionReport(Counter counter, long expectedCount, Duration timeElapsed) {
            this.counter = counter;
            this.expectedCount = expectedCount;
            this.timeElapsed = timeElapsed;
        }
        
        long getCountDifference() {
            return expectedCount - counter.getValue();
        }
        
        @Override
        public String toString() {
            return "ExecutionReport[ " +
                        "counter=" + (getCountDifference() > 0 ? "!" : "") + counter.getClass().getSimpleName() +
                      ", expectedCount=" + thousandGrouping(expectedCount) +
                      ", actualCount=" + thousandGrouping(counter.getValue()) +
                      ", missed incrementations=" + thousandGrouping(getCountDifference()) +
                      ", timeElapsed (ms)=" + thousandGrouping(timeElapsed.toMillis()) +
                    " ]";
        }
    }
}

interface Counter {
    void increment();
    long getValue();
}

/**
 * Implements no synchronization mechanism.<p>
 * 
 * Is thread-safe.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class UnsynchronizedCounter implements Counter {
    long value = 0L;
    
    @Override
    public void increment() {
        ++value; // <-- not an atomic operation
    }
    
    @Override
    public long getValue() {
        return value;
    }
}

/**
 * Implements low-level thread synchronization using the synchronized keyword.<p>
 * 
 * Is thread-safe.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class SynchronizedCounter implements Counter {
    long value = 0L;
    
    @Override
    synchronized public void increment() {
        ++value;
    }
    
    @Override
    public long getValue() {
        return value;
    }
}

/**
 * Write/read counter directly from main memory, bypassing CPU registers.<p>
 * 
 * Is not thread-safe.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class VolatileCounter implements Counter {
    volatile long value = 0L;
    
    @Override
    public void increment() {
        ++value;
    }
    
    @Override
    public long getValue() {
        return value;
    }
}

/**
 * Write-locked counter using {@code ReentrantLock}.
 * 
 * Is thread-safe.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class WriteLockedCounter implements Counter {
    long value = 0L;
    
    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public void increment() {
        try {
            lock.tryLock(ConcurrentCounting.TIMEOUT, TimeUnit.MILLISECONDS);
            ++value;
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (lock.isHeldByCurrentThread())
                lock.unlock();
        }
    }

    /**
     * Will not use the lock.
     * 
     * @return the current counter value
     */
    @Override
    public long getValue() {
        return value;
    }
}

/**
 * Read/Write-locked counter using {@code ReentrantReadWriteLock}.
 * 
 * Is thread-safe.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class ReadWriteLockedCounter implements Counter {
    long value = 0L;
    
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public void increment() {
        ReentrantReadWriteLock.WriteLock write = lock.writeLock();
        
        try {
            write.tryLock(ConcurrentCounting.TIMEOUT, TimeUnit.MILLISECONDS);
            ++value;
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (write.isHeldByCurrentThread())
                write.unlock();
        }
    }

    /**
     * Will not use the lock.
     * 
     * @return the current counter value
     */
    @Override
    public long getValue() {
        return value;
    }
}

/**
 * Read/Write-locked counter using {@code StampedLock}.
 * 
 * Is thread-safe.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class StampLockedCounter implements Counter {
    long value = 0L;
    
    private final StampedLock lock = new StampedLock();

    @Override
    @SuppressWarnings("empty-statement")
    public void increment() {
        long stamp = 0L;
        
        try {
            do stamp = lock.tryWriteLock(ConcurrentCounting.TIMEOUT, TimeUnit.MILLISECONDS);
            while (stamp == 0L);
            
            ++value;
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (lock.validate(stamp))
                lock.unlockWrite(stamp);
        }
    }

    /**
     * Will not use the lock.
     * 
     * @return the current counter value
     */
    @Override
    public long getValue() {
        return value;
    }
}

/**
 * Probably a lock-free counter using CAS-instructions for writing.
 * 
 * Is thread-safe.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class CASCounter implements Counter {
    final AtomicLong value = new AtomicLong();
    
    @Override
    public void increment() {
        value.incrementAndGet();
    }
    
    @Override
    public long getValue() {
        return value.get();
    }
}

/**
 * Probably a lock-free sharded counter using CAS-instructions for writing.
 * 
 * Is thread-safe.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class ShardedCASCounter implements Counter {
    final LongAdder value = new LongAdder();
    
    @Override
    public void increment() {
        value.increment();
    }
    
    @Override
    public long getValue() {
        return value.sum();
    }
}