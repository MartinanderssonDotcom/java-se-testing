package martinandersson.com.threads;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Difference between Condition.signal() (Object.notify()) versus
 * Condition.signalAll() (Object.notifyAll())?<p>
 * 
 * One or all dormant threads wake up, simple as fuck.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class SignalAll
{
    public static void main(String... ignored) throws InterruptedException {
        final Lock lock = new ReentrantLock();
        
        
        // signal()
        // --------
        
        final Condition cnd1 = lock.newCondition();
        
        startThread(lock, cnd1);
        startThread(lock, cnd1);
        startThread(lock, cnd1);

        TimeUnit.SECONDS.sleep(1);
        
        lock.lock();
        
        try {
            // Only one guy wake up..
            System.out.println("Administator will call signal()..");
            cnd1.signal();
        }
        finally {
            lock.unlock();
        }
        
        TimeUnit.SECONDS.sleep(1);
        
        
        // signalAll()
        // -----------
        
        final Condition cnd2 = lock.newCondition();
        
        startThread(lock, cnd2);
        startThread(lock, cnd2);
        startThread(lock, cnd2);
        
        TimeUnit.SECONDS.sleep(1);
        
        lock.lock();
        
        try {
            // All wake up..
            System.out.println("Administator will call signalAll()..");
            cnd2.signalAll();
        }
        finally {
            lock.unlock();
        }
        
        TimeUnit.SECONDS.sleep(1);
    }
    
    private static void startThread(Lock lock, Condition condition) {
        Thread t = new Thread(() -> threadJob(lock, condition));
        t.setDaemon(true);
        t.start();
    }
    
    private static void threadJob(Lock lock, Condition condition) {
        lock.lock();
        
        try {
            System.out.println(id() + " will call await()..");
            
            try {
                condition.await();
            }
            catch (InterruptedException e) {
                System.out.println(id() + " interrupted!");
                return;
            }
            
            System.out.println(id() + " awake!");
        }
        finally {
            lock.unlock();
        }
    }
    
    private static String id() {
        return "#" + Thread.currentThread().getId();
    }
}