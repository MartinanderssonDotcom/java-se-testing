package martinandersson.com.logging;

import java.util.logging.Logger;

/**
 * Will crash in worker thread and main thread.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class MemoryHandlerCrashTest
{
    private static final Logger LOGGER
            = Logger.getLogger(MemoryHandlerCrashTest.class.getName());
    
    public static void main(String... ignored) throws InterruptedException {
        Logging.configure();
        
        Thread worker = new Thread(MemoryHandlerCrashTest::runApp);
        
        worker.start();
        worker.join();
        
        LOGGER.info("Worker finished somehow someway. Will do what he did.");
        runApp();
    }
    
    private static void runApp() {
        int n = 50;
        
        while (n-- > 0) {
            LOGGER.info("Hello, World!");
            
            if (n == 10) {
                LOGGER.warning("A warning message.");
            }
        }
        
        throw new RuntimeException("End of life.");
    }
}