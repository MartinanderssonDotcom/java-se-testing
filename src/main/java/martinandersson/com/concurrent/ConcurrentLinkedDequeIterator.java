package martinandersson.com.concurrent;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Question 1<p>
 * 
 * What happens if client code use an iterator extracted from a {@code
 * ConcurrentLinkedDeque}, get {@code true} from the {@code hasNext()} method,
 * then call {@code next()} but before calling {@code remove()}, another client
 * remove the head?<p>
 * 
 * Answer: nothing happens<p><p>
 * 
 * 
 * 
 * Question 2<p>
 * 
 * What happens if client code use an iterator extracted from a {@code
 * ConcurrentLinkedDeque}, get {@code true} from the {@code hasNext()} method,
 * but before calling {@code next()}, another client remove the head?<p>
 * 
 * Answer: the element is returned from the iterator even though it was removed
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class ConcurrentLinkedDequeIterator
{
    public static void main(String... ignored) {
        // Q1
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        
        deque.add("Hello World");
        
        Iterator<String> it = deque.iterator();
        
        if (!it.hasNext())
            throw new AssertionError("We just added Hello World for fuck sake.");
        
        String helloWorld = it.next();
        
        if (!"Hello World".equals(helloWorld))
            throw new AssertionError("We just added Hello World for fuck sake.");
        
        // Other thread remove before iterator has had a chance to remove
        helloWorld = deque.remove();
        
        if (!"Hello World".equals(helloWorld))
            throw new AssertionError("We just added Hello World for fuck sake.");
        
        it.remove(); // throws nothing
        
        
        // Q2
        deque = new ConcurrentLinkedDeque<>();
        deque.add("Hello World");
        it = deque.iterator();
        
        if (!it.hasNext())
            throw new AssertionError("We just added Hello World for fuck sake.");
        
        // Other thread remove before iterator has had a chance to retrieve:
        helloWorld = deque.remove();
        
        if (!"Hello World".equals(helloWorld))
            throw new AssertionError("We just added Hello World for fuck sake.");
        
//        TimeUnit.SECONDS.sleep(2); // Same result with or without sleep
        
        helloWorld = it.next();
        
        System.out.println("What did I get from it.next()? " + helloWorld); // "Hello World"
    }
}