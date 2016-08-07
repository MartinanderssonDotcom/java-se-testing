package martinandersson.com.urls;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class InetAddressTest {
    public static void main(String... ignored) throws UnknownHostException, IOException {
        URL url = new URL("https://www.google.com");
        System.out.println("url.getHost(): " + url.getHost()); // <-- www.google.com
        
        // This method will throw a java.net.UnknownHostException if full URL is provided!
        InetAddress google = InetAddress.getByName(url.getHost());
        
        /*
         * On my laptop, at home, prints:
         *     www.google.com/64.233.165.104
         *     64.233.165.104
         *     false !!! (even though google is and cmd can ping)
         * 
         * Same results from my PC at home!
         */
        System.out.println("Google: " + google);
        System.out.println("Google IP: " + google.getHostAddress());
        System.out.println("Google reachable: " + google.isReachable(10_000));
    }
}