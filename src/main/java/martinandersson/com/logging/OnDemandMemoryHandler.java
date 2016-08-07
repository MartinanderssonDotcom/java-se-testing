package martinandersson.com.logging;

import java.lang.reflect.Field;
import java.util.function.Consumer;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.MemoryHandler;

/**
 * Mostly same semantics as {@code MemoryHandler}.<p>
 * 
 * {@code MemoryHandler} support "push to a predefined handler". This
 * specialized class support "push to a specified handler on demand".<p>
 * 
 * Both classes clear the buffer after push. In effect, this means that log
 * records are not duplicated across files.<p>
 * 
 * {@code push()} will crash. Use {@code pushTo(Handler)}.<p>
 * 
 * Please not that {@code flush()} and {@code setPushLevel()} will also crash.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class OnDemandMemoryHandler extends MemoryHandler
{
    private static final Handler VOID = new Handler() {
        @Override public void publish(LogRecord record) {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override public void flush() {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override public void close() throws SecurityException {
            // Empty
        }
    };
    
    
    
    private final Field buffer,
                        start,
                        count;
    
    private Consumer<Level> afterPublish;
    
    
    
    public OnDemandMemoryHandler() {
        // Level.OFF says we need to push() programmatically.
        super(VOID, 1_000, Level.OFF);
        
        try {
            buffer = MemoryHandler.class.getDeclaredField("buffer");
            buffer.setAccessible(true);
            
            start = MemoryHandler.class.getDeclaredField("start");
            start.setAccessible(true);
            
            count = MemoryHandler.class.getDeclaredField("count");
            count.setAccessible(true);
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        
        afterPublish = null;
    }
    
    
    
    /**
     * Throws {@code UnsupportedOperationException}.
     * 
     * @throws UnsupportedOperationException always
     */
    @Override
    public void push() {
        throw new UnsupportedOperationException("Use pushTo(Handler) instead.");
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void publish(LogRecord record) {
        if (afterPublish == null) {
            super.publish(record);
            return;
        }
        
        if (!isLoggable(record)) {
            return;
        }
        
        super.publish(record);
        
        afterPublish.accept(record.getLevel());
    }
    
    /**
     * Push all log records in memory to the specified handler.<p>
     * 
     * The log records held in memory is not cleared after this operation.
     * 
     * @param handler  target handler
     */
    public synchronized void pushTo(Handler handler) {
        int c = getCount();
        int s = getStart();
        LogRecord[] b = getBuffer();
        
        for (int i = 0; i < c; i++) {
            int ix = (s + i) % b.length;
            LogRecord record = b[ix];
            handler.publish(record);
        }
        
        emptyBuffer();
    }
    
    /**
     * Set an after publish callback.<p>
     * 
     * This guy is invoked after each [successfully] published log record.<p>
     * 
     * If {@code afterPublish} is {@code null}, then a previously stored level
     * subscriber is removed.
     * 
     * @param afterPublish  the level subscriber (may be {@code null})
     */
    public synchronized void setAfterPublish(Consumer<Level> afterPublish) {
        this.afterPublish = afterPublish;
    }
    
    
    
    private LogRecord[] getBuffer() {
        final LogRecord[] b;
        
        try {
            b = (LogRecord[]) buffer.get(this);
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        
        return b;
    }
    
    private int getStart() {
        final int s;
        
        try {
            s = start.getInt(this);
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        
        return s;
    }
    
    private int getCount() {
        final int c;
        
        try {
            c = count.getInt(this);
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        
        return c;
    }
    
    private void emptyBuffer() {
        try {
            start.set(this, 0);
            count.set(this, 0);
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}