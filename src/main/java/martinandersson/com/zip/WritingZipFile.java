package martinandersson.com.zip;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Will create a ZIP file containing two text files; each with the text "Hello,
 * World!".<p>
 * 
 * The requirement is to not touch the disk while creating the text files.
 * Writing the ZIP file to disk is the only procedure that will touch the
 * disk.<p>
 * 
 * The ZIP file will be "myZip.zip" in the root of the working directory.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class WritingZipFile
{
    public static void main(String... ignored) throws IOException {
        final Path zip = Paths.get("myZip.zip");
        
        final byte[] bytes = "Hello, World!".getBytes(StandardCharsets.UTF_8);
        
        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zip))) {
            zos.putNextEntry(new ZipEntry("first.txt"));
            zos.write(bytes);
            
            zos.putNextEntry(new ZipEntry("second.txt"));
            zos.write(bytes);
        }
    }
}