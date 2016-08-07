package martinandersson.com.threads;

import java.util.concurrent.TimeUnit;

/**
 * Two ways to wake up a sleeping thread: notify and interrupt.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class NotifyAndInterruptWakeup
{
    public static void main(String... ignored) throws InterruptedException {
        Object monitor = new Object();
        
        Runnable sleepy = () -> {
            synchronized(monitor) {
                try {
                    monitor.wait();
                    System.out.println("Worker resumed..");
                } catch (InterruptedException ex) {
                    System.out.println("Worker interrupted!");
                }
            }
        };
        
        Thread t = new Thread(sleepy);
        t.start();
        
        TimeUnit.SECONDS.sleep(1);
        
        synchronized(monitor) {
            System.out.println("Admin gonna notify..");
            monitor.notifyAll();
        }
        
        t = new Thread(sleepy);
        t.start();
        
        TimeUnit.SECONDS.sleep(1);
        
        synchronized(monitor) {
            System.out.println("Admin gonna interrupt..");
            t.interrupt();
        }
    }
}