package martinandersson.com.logging;

import java.util.logging.Logger;

/**
 * Will log warning in worker thread and main thread.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class MemoryHandlerWarningTest
{
    private static final Logger LOGGER
            = Logger.getLogger(MemoryHandlerWarningTest.class.getName());
    
    public static void main(String... ignored) throws InterruptedException {
        Logging.configure();
        
        Thread worker = new Thread(MemoryHandlerWarningTest::runApp);
        
        worker.start();
        worker.join();
        
        LOGGER.info("Worker finished somehow someway. Will do what he did.");
        runApp();
    }
    
    private static void runApp() {
        LOGGER.info("Info message.");
        LOGGER.warning("Warning message.");
        LOGGER.severe("Severe message.");
    }
}