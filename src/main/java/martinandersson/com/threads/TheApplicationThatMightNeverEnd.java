package martinandersson.com.threads;

import java.util.concurrent.TimeUnit;

/**
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class TheApplicationThatMightNeverEnd
{
    public static void main(String... ignored) throws InterruptedException {
        Worker w = new Worker();
        w.start();
        
        TimeUnit.SECONDS.sleep(1);
        
        w.shutdown();
    }
}

class Worker extends Thread
{
    /*
     * Not marked volatile. See JLS 8 17.3. On my machine, this app is not
     * broken.
     */
    private boolean stop = false;
    
    public void shutdown() {
        stop = true;
    }
    
    @Override
    public void run() {
        while (!stop) {
            System.out.println("Worker thread doin' some work..");
            
            try {
                TimeUnit.SECONDS.sleep(1);
            }
            catch (InterruptedException e) {
                System.out.println("Worker interrupted. Quiting..");
            }
        }
    }
}