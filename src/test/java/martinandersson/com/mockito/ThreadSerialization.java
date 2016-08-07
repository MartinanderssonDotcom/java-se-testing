package martinandersson.com.mockito;

import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class ThreadSerialization
{
    /**
     * If a mock does serialize his calls, then this test will take about 20 seconds.
     * If not, then it should take about 10 seconds. Mockito docs says that a mock
     * is "thread safe", exactly what that means is not outlined. I believe the mock
     * do serialize the calls, meaning that the usage of a mock in concurrent testing
     * has very limited purposes, if any at all. If a mock is shared among threads,
     * then that could mean no concurrency is performed.
     * 
     * Result/Conclusion: The mock do not serialize the calls.
     */
    @Test
    public void threadSerialization_objectCustomAnswer() throws InterruptedException {
        final Object objectMock = Mockito.mock(Object.class);
        
        when(objectMock.toString()).thenAnswer(new Answer<String>() {
            public String answer(InvocationOnMock invocation) throws Throwable {
                TimeUnit.SECONDS.sleep(10);
                return "done sleeping..";
            }
        });
        
        Runnable callMock = new Runnable() {
            public void run() {
                final String name = Thread.currentThread().getName();
                System.out.println(name + " will call mock..");
                objectMock.toString();
                System.out.println(name + " done calling mock.");
            }
        };
        
        Thread t1 = new Thread(callMock),
               t2 = new Thread(callMock);
        
        final long startMS = System.currentTimeMillis();
        
        t1.start();
        t2.start();
        
        t1.join();
        t2.join();
        
        System.out.println("Test done. It took (ms): " + (System.currentTimeMillis() - startMS));
    }
}
