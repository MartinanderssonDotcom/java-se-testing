package martinandersson.com.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * This is a copy-paste from:
 *     https://github.com/binwiederhier/cipherinputstream-aes-gcm/blob/master/src/CipherInputStreamIssuesTests.java
 * 
 * BUT using the Java's provider instead of Bounty Castle.<p>
 * 
 * The result is that Test B fail on JDK 1.8.0_20 CipherInputStream is broken)
 * but work on JDK 1.8.0_25. Working the cipher manually (Test A) work in both
 * versions.<p>
 * 
 * Googled through the release documents of both update 20 and 25, not a word
 * about impl change of CipherInputStream anywhere.
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * ALGORITHM RESEARCH
 * ------------------
 * 
 * 
 * 
 * Example and bug of AES/GCM:
 * http://bugs.java.com/bugdatabase/view_bug.do?bug_id=8016249
 * 
 * Also see:
 * http://blog.philippheckel.com/2014/03/01/cipherinputstream-for-aead-modes-is-broken-in-jdk7-gcm/
 * 
 * 
 * 
 * 
 * 
 * Is the "associated data" (AD) required or optional?
 * 
 * Says optional (last paragraph says AD may be of zero length):
 * 
 *     Authenticated encryption [BN00] is a form of encryption that, in addition
 *     to providing confidentiality for the plaintext that is encrypted,
 *     provides a way to check its integrity and authenticity. Authenticated
 *     Encryption with Associated Data, or AEAD [R02], adds the ability to check
 *     the integrity and authenticity of some Associated Data (AD), also called
 *     "additional authenticated data", that is not encrypted.
 * 
 *     [..]
 * 
 *     The associated data A is used to protect information that needs to be
 *     authenticated, but does not need to be kept confidential.  When using an
 *     AEAD to secure a network protocol, for example, this input could include
 *     addresses, ports, sequence numbers, protocol version numbers, and other
 *     fields that indicate how the plaintext or ciphertext should be handled,
 *     forwarded, or processed.  In many situations, it is desirable to
 *     authenticate these fields, though they must be left in the clear to allow
 *     the network or system to function properly. When this data is included in
 *     the input A, authentication is provided without copying the data into the
 *     plaintext.
 * 
 *     Each AEAD algorithm MUST accept any associated data with a length between
 *     zero and A_MAX octets, inclusive, where the value A_MAX is specific to
 *     that algorithm.
 * 
 *     Source: http://www.ietf.org/rfc/rfc5116.txt
 * 
 * JavaDoc of Cipher says:
 * 
 *     Modes such as Authenticated Encryption with Associated Data (AEAD)
 *     provide authenticity assurances for both confidential data and Additional
 *     Associated Data (AAD) that is not encrypted. [..] Both confidential and
 *     AAD data can be used when calculating the authentication tag (similar to
 *     a Mac). This tag is appended to the ciphertext during encryption, and is
 *     verified on decryption.
 *     
 *     AEAD modes such as GCM/CCM perform all AAD authenticity calculations
 *     before starting the ciphertext authenticity calculations.
 * 
 *     Source: http://docs.oracle.com/javase/8/docs/api/javax/crypto/Cipher.html
 * 
 * A is "only authenticated" and "not encrypted":
 * 
 *     A is data which is only authenticated (not encrypted)
 * 
 *     Source: http://en.wikipedia.org/wiki/Galois/Counter_Mode
 * 
 * VERDICT: AD is not required for message authentication of the encrypted
 * cipher text. AD is optional if one need to also send unencrypted metadata and
 * have that data to be authenticated as well.
 * 
 * 
 * 
 * 
 * 
 * 
 * Can AES/GCM be used as a stream cipher?
 * 
 * I think next quote talks specifically about CTR, but it still says that
 * "counter mode":
 * 
 *     [..] turns a block cipher into a stream cipher. It generates the next
 *     keystream block by encrypting successive values of a "counter".
 * 
 *     Source: http://en.wikipedia.org/wiki/Block_cipher_mode_of_operation#Counter_.28CTR.29
 * 
 * and..
 * 
 *     As the name suggests, GCM mode combines the well-known counter mode of
 *     encryption with the new Galois mode of authentication.
 * 
 *     Source: http://en.wikipedia.org/wiki/Galois/Counter_Mode
 * 
 * Next quote says GCM "uses" counter mode:
 * 
 *     I would recommend using GCM mode encryption. It is included in the latest
 *     JDK (1.7) by default. It uses a counter mode encryption (a stream cipher,
 *     no padding required) and adds an authentication tag.
 * 
 *     Source: http://stackoverflow.com/a/9574553/1268003
 * 
 * Is a stream cipher:
 * 
 *     GCM is counter mode with authentication. All the rules for Counter Mode
 *     still apply. Counter mode effectively turns a block cipher into a stream
 *     cipher.
 * 
 *     Source: http://stackoverflow.com/a/5695328/1268003
 * 
 * JavaDoc of Cipher says:
 * 
 *     [..] block ciphers can be turned into byte-oriented stream ciphers by
 *     using an 8 bit mode such as CFB8 or OFB8
 * 
 *     Source: http://docs.oracle.com/javase/8/docs/api/javax/crypto/Cipher.html
 * 
 * So if AES can be used with CFB and OFB to produce a cipher stream, should it
 * not be the same for GCM?
 * 
 * GCM is "ideal" for "packetized data" (i.e, not a stream of bits):
 * 
 *     GCM is ideal for protecting packetized data, because it has minimum
 *     latency and minimum operation overhead.
 * 
 *     Source: http://en.wikipedia.org/wiki/Galois/Counter_Mode
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * What exactly goes into GCM as IV?
 * 
 * JavaDoc of Cipher says:
 * 
 *     Note that GCM mode has a uniqueness requirement on IVs used in encryption
 *     with a given key. When IVs are repeated for GCM encryption, such usages
 *     are subject to forgery attacks. Thus, after each encryption operation
 *     using GCM mode, callers should re-initialize the cipher objects with GCM
 *     parameters which has a different IV value.
 * 
 *     Source: http://docs.oracle.com/javase/8/docs/api/javax/crypto/Cipher.html
 * 
 * Okay after "each encryption operation". Sure, Wikipedia says about the same:
 * 
 *     security depends on choosing a unique initialization vector for every
 *     encryption performed with the same key (see stream cipher attack). For
 *     any given key and initialization vector combination, GCM is limited to
 *     encrypting 2^39 - 256 bits of plain text.
 * 
 *     Source: http://en.wikipedia.org/wiki/Galois/Counter_Mode
 * 
 * But the provided numbers is still equivalent to 68.72 gig plaintext.
 *     Source: http://www.wolframalpha.com/input/?i=%28%282%5E39%29-256%29+bits+to+gigabytes
 * 
 * This might shed some light:
 * 
 *     Thus if at any point you reuse PRNG stream then it maybe possible to
 *     obtain plain text with a simple XOR. Its important to note that the same
 *     Key+Nonce will always produce the same PRNG stream. In a protocol the
 *     same Key+Nonce can be used for the life of the session, so long as the
 *     mode's counter doesn't wrap (int overflow). An example protocol you could
 *     have two parties and they have a pre-shared secret key, then they could
 *     negotiate a new cryptographic Nonce for each session (Remember nonce
 *     means use ONLY ONCE).
 * 
 *     Source: http://stackoverflow.com/a/5695328/1268003
 * 
 * VERDICT: The symmetric key generated by SRP (Secure Remote Protocol) is
 * random and no chat data (my intention) sent during one session will ever go
 * past 68.72 gig. But, do the IV need to be unique? What if an IV has already
 * been used with another key in another session, perhaps by another user? I'm
 * not certain. Given that the key is random, and my IV will be a hash of that,
 * I see it as a no brainer.
 * 
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class AesGcmCipherInputStreamBug {
	private static final SecureRandom secureRandom = new SecureRandom();
	
//	static {
//		Security.addProvider(new BouncyCastleProvider());
//	}
	
	public static void main(String args[]) throws Exception {
		System.out.println("----------------------------------------------------------------------------------");

		testA_JavaxCipherWithAesGcm();
		testB_JavaxCipherInputStreamWithAesGcm();

		System.out.println("----------------------------------------------------------------------------------");
	}
	
	public static void testA_JavaxCipherWithAesGcm() throws InvalidKeyException, InvalidAlgorithmParameterException, IOException, NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		
		// Encrypt (not interesting in this example)
		byte[] randomKey = createRandomArray(16);
		byte[] randomIv = createRandomArray(16);		
		byte[] originalPlaintext = "Confirm 100$ pay".getBytes("ASCII"); 		
		byte[] originalCiphertext = encryptWithAesGcm(originalPlaintext, randomKey, randomIv);
		
		// Attack / alter ciphertext (an attacker would do this!) 
		byte[] alteredCiphertext = Arrays.copyOf(originalCiphertext, originalCiphertext.length);
		alteredCiphertext[8] = (byte) (alteredCiphertext[8] ^ 0x08); // <<< Change 100$ to 900$			
		
		// Decrypt with regular CipherInputStream (from JDK6/7)
		Cipher cipher = Cipher.getInstance("AES_128/GCM/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(randomKey, "AES"), new GCMParameterSpec(96, randomIv));
		
		try {
			cipher.doFinal(alteredCiphertext);		
			//  ^^^^^^ INTERESTING PART ^^^^^^	
			//
			//  The GCM implementation in BouncyCastle and the Cipher class in the javax.crypto package 
			//  behave correctly: A BadPaddingException is thrown when we try to decrypt the altered ciphertext.
			//  The code below is not executed.
			//
			
			System.out.println("Test A: javac.crypto.Cipher:                                 NOT OK, tampering not detected");
		}
		catch (BadPaddingException e) {		
			System.out.println("Test A: javac.crypto.Cipher:                                 OK, tampering detected");
		}
	}	
	
	public static void testB_JavaxCipherInputStreamWithAesGcm() throws InvalidKeyException, InvalidAlgorithmParameterException, IOException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {
		// Encrypt (not interesting in this example)
		byte[] randomKey = createRandomArray(16);
		byte[] randomIv = createRandomArray(16);		
		byte[] originalPlaintext = "Confirm 100$ pay".getBytes("ASCII"); 		
		byte[] originalCiphertext = encryptWithAesGcm(originalPlaintext, randomKey, randomIv);
		
		// Attack / alter ciphertext (an attacker would do this!) 
		byte[] alteredCiphertext = Arrays.copyOf(originalCiphertext, originalCiphertext.length);		
		alteredCiphertext[8] = (byte) (alteredCiphertext[8] ^ 0x08); // <<< Change 100$ to 900$			
		
		// Decrypt with regular CipherInputStream (from JDK6/7)
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(randomKey, "AES"), new GCMParameterSpec(96, randomIv));
		
		try {
			byte[] decryptedPlaintext = readFromStream(new javax.crypto.CipherInputStream(new ByteArrayInputStream(alteredCiphertext), cipher));
			//                                         ^^^^^^^^ INTERESTING PART ^^^^^^^^	
			//
			//  The regular CipherInputStream in the javax.crypto package simply ignores BadPaddingExceptions
			//  and doesn't pass them to the application. Tampering with the ciphertext does thereby not throw  
			//  a MAC verification error. The code below is actually executed. The two plaintexts do not match!
			//  The decrypted payload is "Confirm 900$ pay" (not: "Confirm 100$ pay")
			//

			System.out.println("Test B: javac.crypto.CipherInputStream:                      NOT OK, tampering not detected");
			System.out.println("        - Original plaintext:                                - " + new String(originalPlaintext, "ASCII"));
			System.out.println("        - Decrypted plaintext:                               - " + new String(decryptedPlaintext, "ASCII"));
		}
		catch (Exception e) { // What is thrown is IOException that wraps a "AEADBadTagException: Tag mismatch!"
			System.out.println("Test B: javac.crypto.CipherInputStream:                      OK, tampering detected");
                        e.printStackTrace();
		}
	}	

	private static byte[] readFromStream(InputStream inputStream) throws IOException {
		ByteArrayOutputStream decryptedPlaintextOutputStream = new ByteArrayOutputStream(); 
		
		int read = -1;
		byte[] buffer = new byte[16];
		
		while (-1 != (read = inputStream.read(buffer))) {
			decryptedPlaintextOutputStream.write(buffer, 0, read);
		}
		
		inputStream.close();
		decryptedPlaintextOutputStream.close();
		
		return decryptedPlaintextOutputStream.toByteArray();  		
	}
	
	private static byte[] encryptWithAesGcm(byte[] plaintext, byte[] randomKeyBytes, byte[] randomIvBytes) throws IOException, InvalidKeyException,
			InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {

                System.out.println("KEY LENGTH: " + randomKeyBytes.length * 8);
                System.out.println("IV LENGTH: " + randomIvBytes.length * 8);
                
		SecretKey randomKey = new SecretKeySpec(randomKeyBytes, "AES");
		
		Cipher cipher = Cipher.getInstance("AES_128/GCM/NoPadding");
                

                GCMParameterSpec spec = new GCMParameterSpec(96, randomIvBytes);
                
                /*
                 * Next statement throws java.security.InvalidKeyException if
                 * size of key bytes != 128 bits. IV bytes can apparently be
                 * something else.
                 */
		cipher.init(Cipher.ENCRYPT_MODE, randomKey, spec);
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		CipherOutputStream cipherOutputStream = new CipherOutputStream(byteArrayOutputStream, cipher);
		
		cipherOutputStream.write(plaintext);
		cipherOutputStream.close();
		
		return byteArrayOutputStream.toByteArray();
	}
	
	private static byte[] createRandomArray(int size) {
		byte[] randomByteArray = new byte[size];
		secureRandom.nextBytes(randomByteArray);

		return randomByteArray;
	}
}