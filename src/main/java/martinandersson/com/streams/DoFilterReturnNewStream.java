package martinandersson.com.streams;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Do Stream.filter() return a new Stream object?<p>
 * 
 * Yes they do. And if you try to operate on the old stream, an
 * IllegalStateException is thrown.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class DoFilterReturnNewStream
{
    public static void main(String... ignored) {
        Stream<String> strings = Stream.of("s1", "s2", "s3");
        
        strings.filter(Predicate.isEqual("s2"));
        
        // I didn't save the returned reference from filter. Which strings will be printed?
        
        strings.forEach(System.out::println);
        
        // and the result..
        
        // java.lang.IllegalStateException: stream has already been operated upon or closed
        
        // Lesson learned, you have to keep using the returned streams!!
        // Also google "IllegalStateException" on this page: http://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html
    }
}