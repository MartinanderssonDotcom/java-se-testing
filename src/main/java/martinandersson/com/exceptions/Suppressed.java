package martinandersson.com.exceptions;

import static java.lang.System.out;
import java.util.stream.Stream;

/**
 * Lesson learned: The suppressed Exception is not particularly interesting.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class Suppressed
{
    public static void main(String... ignored) {
        try {
            executeCrashingStream();
        }
        catch (Exception e) {
            // UnsupportedOperationException
            out.println("Caught Exception: " + e);
            
            Throwable[] suppressed = e.getSuppressed();
            
            out.println("Count of suppressed exceptions: " + suppressed.length);
            
            // IllegalStateException
            Stream.of(suppressed).map(t -> "\t" + t.toString()).forEach(System.out::println);
            
            out.println("Causing Exception: " + e.getCause());
        }
    }
    
    static void executeCrashingStream() {
        try(UltraCrashingStream crash = new UltraCrashingStream();) {
            crash.readCrash();
        }
    }
}

class UltraCrashingStream implements AutoCloseable
{
    public int readCrash() {
        out.println("ENTRY readCrash()");
        throw new UnsupportedOperationException(
                "This method will never be implemented properly.");
    }
    
    @Override
    public void close() throws RuntimeException {
        out.println("ENTRY close()");
        throw new IllegalStateException(
                "You must never close a UltraCrashingStream!");
    }
}