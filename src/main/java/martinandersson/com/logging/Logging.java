package martinandersson.com.logging;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;

/**
 * A "logging framework" within JUL ({@code java.util.logging}).<p>
 * 
 * Configure/enable our framework by calling {@link #configure()} first thing
 * you do in the application entry point.<p>
 * 
 * If you enable our logging framework, then logging will be silent; all console
 * handlers will be removed from the root logger (usually only 1). But, last 1
 * 000 log records (no matter logging level) are held in memory and if a thread
 * die because of an uncaught exception, these log records are pushed to disk in
 * a "logs" subfolder of the working directory. Each thread death translate to
 * one log file.<p>
 * 
 * Similarly, if the root logger ever see a log record with level WARNING or
 * higher, then all previous 1 000 log records are also sent to disk. One file
 * per "warning" record.<p>
 * 
 * "Warning" messages are thusly expected to not crash the application, but be
 * of a very "unexpected" nature. If system administrator ever see real log
 * files on disk, then these are cause for concern and should be thoroughly
 * examined.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public final class Logging
{
    private static final Logger LOGGER = Logger.getLogger(Logging.class.getName());
    
    private static final AtomicBoolean CONFIGURED = new AtomicBoolean();
    
    
    
    private Logging() {
        // Empty
    }
    
    
    /**
     * Configure/enable our logging framework, as described in {@link Logging}.
     * 
     * @throws IllegalStateException if this method has been called before
     */
    public static void configure() {
        if (CONFIGURED.getAndSet(true)) {
            throw new IllegalStateException("Already configured.");
        }
        
        if (Thread.getDefaultUncaughtExceptionHandler() != null) {
            LOGGER.severe(() ->
                    "Default uncaught exception handler already registered: " + Thread.getDefaultUncaughtExceptionHandler());
            
            System.exit(1);
        }
        
        final Logger root = Logger.getLogger("");
        
        final Level prevLevel = root.getLevel();
        root.setLevel(Level.ALL);
        
        List<Handler> prevHandlers = Arrays.stream(root.getHandlers())
                .filter(h -> h instanceof ConsoleHandler)
                .collect(toList());
        
        prevHandlers.forEach(root::removeHandler);
        
        final OnDemandMemoryHandler memory = new OnDemandMemoryHandler();
        
        memory.setAfterPublish(level -> {
            if (level.intValue() >= Level.WARNING.intValue()) {
                try {
                    pushFromMemoryToDisk(memory, level.getLocalizedName());
                }
                catch (Throwable t) {
                    rollback(root, prevLevel, memory, prevHandlers);
                    LOGGER.severe("Throwable intercepted while writing log to disk. Root logger rolled back.");
                    throw t;
                }
            }
        });
        
        root.addHandler(memory);
        
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            LOGGER.log(Level.SEVERE, "Thread will die due to uncaught throwable.", throwable);
            
            try {
                pushFromMemoryToDisk(memory, thread.getId() + "_crash");
            }
            catch (Throwable t) {
                rollback(root, prevLevel, memory, prevHandlers);
                LOGGER.severe("Throwable in uncaught exception handler caused our logging framework to rollback to defaults. Most likely all previous log records has been lost.");
                LOGGER.log(Level.SEVERE, "Uncaught throwable that will be suppressed.", throwable);
                
                // TODO: Might not want to log this guy (we are not the handler of him after all)..
                LOGGER.log(Level.SEVERE, "New throwable when pushing an error log.", t);
                throw t;
            }
        });
    }
    
    private static void pushFromMemoryToDisk(OnDemandMemoryHandler memory, String suffix) {
        Handler file = newFileHandler(suffix);

        try {
            memory.pushTo(file);
        }
        finally {
            file.close();
        }
    }
    
    private static FileHandler newFileHandler(String suffix) {
        Path logs = Paths.get("logs");
        
        if (!Files.exists(logs)) {
            try {
                Files.createDirectory(logs);
            }
            catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        else if (!Files.isDirectory(logs)) {
            throw new UncheckedIOException(new IOException(
                    "A \"logs\" file exists in the working directory. Can not be used as log directory."));
        }
        
        final FileHandler toFile;
        
        try {
            toFile = new FileHandler(
                "logs/" + Instant.now().toString().replace(':', '-') + '_' +  suffix + ".log");
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        
        toFile.setFormatter(new PrettyFormatter());
        
        return toFile;
    }
    
    private static void rollback(
            Logger root,
            Level prevLevel,
            OnDemandMemoryHandler memory,
            List<Handler> prevHandlers)
    {
            Thread.setDefaultUncaughtExceptionHandler(null);
            root.removeHandler(memory);
            prevHandlers.forEach(root::addHandler);
            root.setLevel(prevLevel);
    }
}