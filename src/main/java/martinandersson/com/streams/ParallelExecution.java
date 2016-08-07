package martinandersson.com.streams;

import static java.lang.System.out;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * Docs say that a "stream pipeline" may execute in parallel.<p>
 * 
 * Is that the terminal operation only or all intermediate operations such as
 * filters and mappers?
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class ParallelExecution
{
    public static void main(String[] args) {
        int n = Runtime.getRuntime().availableProcessors();
        
        if (n < 2) {
            throw new UnsupportedOperationException();
        }
        
        final Set<Getter> objects = new HashSet<>();
        
        while (n-- > 0) {
            objects.add(new Getter());
        }
        
        Stream<Getter> stream = objects.stream();
        
        // false
        out.println(objects.stream().isParallel());
        
        // true
        out.println(objects.stream().parallel().isParallel());
        
        // isParallel() actually says whether or not it will execute in parallel.
        // ordered streams can execute in parallel too??
        // true
        out.println(objects.stream().parallel().sorted().isParallel());
        
        // Is foreach executed in parallel? Yes.
        println("--------------------------");
        objects.parallelStream()
                .forEach(Getter::getIdSlow);
        
        // Is the filter executed in parallel? Yes.
        // One object is passed through the filter and foreach per thread.
        // The whole chain, or "stream pipeline", is therefore said to execute in parallel.
        println("--------------------------");
        objects.parallelStream()
                .filter(obj -> {
                    println("in filter of " + obj.getIdFast());
                    return true;
                })
                .forEach(Getter::getIdSlow);
        
        // Is the mapper executed in parallel? Yes, with same notes as previously.
        println("--------------------------");
        objects.parallelStream()
                .mapToInt(obj -> {
                    println("in mapper of " + obj.getIdFast());
                    return obj.getIdFast();
                })
                .forEach(id -> println("in foreach of " + id));
    }
    
    static void println(String msg) {
        System.out.println(Thread.currentThread().getId() + ": " + msg);
    }
}

class Getter implements Comparable<Getter>
{
    private static final AtomicInteger SEQ = new AtomicInteger(1);
    
    final int id = SEQ.getAndIncrement();
    
    int getIdFast() {
        return id;
    }
    
    int getIdSlow() {
        ParallelExecution.println("in getter of " + id);
        
        try {
            TimeUnit.SECONDS.sleep(1);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        
        return id;
    }
    
    @Override
    public int compareTo(Getter other) {
        Objects.requireNonNull(other);
        return Integer.compare(this.id, other.id);
    }
}