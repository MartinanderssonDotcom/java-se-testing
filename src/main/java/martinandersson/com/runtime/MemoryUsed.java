package martinandersson.com.runtime;

import static java.lang.System.out;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class MemoryUsed
{
    public static void main(String... ignored) {
        Runtime rt = Runtime.getRuntime();
        
        out.println("Total memory: " + toMb(rt.totalMemory()));
        out.println("Max memory: " + toMb(rt.maxMemory()));
        out.println("Used memory: " + toMb(rt.totalMemory() - rt.freeMemory()));
        out.println("Free memory: " + toMb(rt.freeMemory()));
    }
    
    private static double toMb(long bytes) {
        return bytes / 1024. / 1024.;
    }
}