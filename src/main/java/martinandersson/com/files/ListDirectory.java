package martinandersson.com.files;

import java.io.IOException;
import static java.lang.System.out;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class ListDirectory
{
    public static void main(String... ignored) throws IOException {
        // List all files, including subdirectories, in "D:\Temp":
        print( Files.list(Paths.get("D:/Temp")) );
        
        // Or (note that this will yield one more entry; "D:\Temp"):
        print( Files.walk(Paths.get("D:/Temp"), 1) );
        
        Path temp = Files.createTempFile(ListDirectory.class.getSimpleName(), null);
        if (!Files.exists(temp) || Files.size(temp) > 0) {
            throw new AssertionError();
        }
        
        // Is an empty file a "regular file"?
        out.println("Empty regular? " + Files.isRegularFile(temp));
        
        // So find all regular files in temp dir:
        print( Files.list(Paths.get("D:/Temp")).filter(Files::isRegularFile) );
    }
    
    private static void print(Stream<Path> paths) {
        try {
            paths.forEach(p -> out.println(p.toString()));
        }
        finally {
            paths.close();
        }
        out.println();
    }
}