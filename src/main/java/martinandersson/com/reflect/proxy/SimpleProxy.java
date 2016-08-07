package martinandersson.com.reflect.proxy;

import static java.lang.System.out;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Example proxy.<p>
 * 
 * Java's JDK only has support for building proxies of an instance if they can
 * share the interface. Proxying a "real class" require other library code from
 * third parties (who do low-level bytecode inspection during runtime).
 */
public class SimpleProxy 
{
    public static void main(String... args) {
        Counter simpleCounter = new SimpleCounter();
        Counter proxyRef = proxy(simpleCounter);
        
        // Proxy intercept and double the amount:
        out.println("Expected 1 but is: " + proxyRef.incrementByAndGet(1)); // 2
    }
    
    /**
     * Will proxy a counter and make sure each and every call to {@linkplain
     * Counter#incrementByAndGet(int)} will see its argument doubled.<p>
     * 
     * This implementation is crap and must not be used in production code.
     * 
     * @param counter real counter to be proxied, may be {@code final}
     * @return the proxy reference
     */
    private static Counter proxy(Counter counter) {
        InvocationHandler handler = (proxy, method, args) -> {
            out.println("Proxy about to intercept: " + method);
            Integer doubled = ((Integer) args[0]) * 2;
            return method.invoke(counter, doubled);
        };
        
        return (Counter) Proxy.newProxyInstance(
                counter.getClass().getClassLoader(), new Class[]{Counter.class}, handler);
    }
}

interface Counter
{
    /**
     * @param value to increment the counter with
     * @return the new value
     */
    int incrementByAndGet(int value);
}

final class SimpleCounter implements Counter
{
    int value = 0;

    @Override
    public int incrementByAndGet(int value) {
        return this.value += value;
    }
}