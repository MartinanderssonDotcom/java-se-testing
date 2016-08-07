package martinandersson.com.security;

import java.security.Provider;
import java.security.Security;
import java.util.Set;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class Algorithms
{
    /*
     * Cryptographic services according to:
     *     http://docs.oracle.com/javase/8/docs/technotes/guides/security/crypto/CryptoSpec.html
     */
    private static final String[] SERVICES = {
        "SecureRandom",
        "MessageDigest",
        "Signature",
        "Cipher",
        "Mac",
        "KeyFactory",
        "SecretKeyFactory",
        "KeyPairGenerator",
        "KeyGenerator",
        "KeyAgreement",
        "AlgorithmParameters",
        "AlgorithmParameterGenerator",
        "KeyStore",
        "CertificateFactory",
        "CertPathBuilder",
        "CertPathValidator",
        "CertStore"
    };
    
    public static void main(String[] args) {
        
        // Returns two implementations!?
        System.out.println("Most secure impl of SecureRandom: " + Security.getProperty("securerandom.strongAlgorithms"));
        System.out.println();
        
        System.out.println("Providers:");
        System.out.println("----------");
        
        for (Provider p : Security.getProviders()) {
            System.out.println(p);
        }
        
        System.out.println();
        System.out.println();
        System.out.println("Algorithms supported by..");
        System.out.println("-------------------------");
        System.out.println();
        
        for (String service : SERVICES) {
            Set<String> algorithms = Security.getAlgorithms(service);
            
            System.out.println(service + " -->");
            
            if (algorithms.isEmpty()) {
                System.out.println("\tNONE!");
            }
            else {
                for (String a : algorithms) {
                    System.out.println("\t" + a);
                }
            }
            
            System.out.println();
        }
    }
}