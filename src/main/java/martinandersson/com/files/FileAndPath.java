package martinandersson.com.files;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import static java.lang.System.out;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Demonstrates the "default" absolute path amongst other things.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class FileAndPath
{
    public static void main(String... ignored) {
        // Current dir:
        // D:\Martin\Programmering\java-se-testing
        out.println("1: " + Paths.get("").toAbsolutePath());
        
        // Dots are actually kept as-is:
        // D:\Martin\Programmering\java-se-testing\..\java-se-testing
        out.println("2: " + Paths.get("../java-se-testing").toAbsolutePath());
        
        try {
            // How do we remove such dots?
            // D:\Martin\Programmering\java-se-testing
            out.println("3: " + Paths.get("../java-se-testing").toRealPath());
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        
        // Adding a file to a directory:
        // D:\Martin\Programmering\java-se-testing\somefile.txt
        out.println("4: " + Paths.get("").resolve("somefile.txt").toAbsolutePath());
        
        
        File f = new File("testfile.txt");
        Path p = f.toPath();
        
        // testfile.txt
        out.println(f);
        out.println(p);
        out.println(p.getFileName());
        
        // D:\Martin\MA\lo\NB\javase-testing\testfile.txt
        out.println(f.getAbsolutePath());
        out.println(p.toAbsolutePath());
        out.println(Paths.get("testfile.txt").toAbsolutePath());
    }
}