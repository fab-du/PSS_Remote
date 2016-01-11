package de.hsmannheim.cryptolocal;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.bouncycastle.util.encoders.Hex;

import com.nimbusds.srp6.SRP6ClientCredentials;
import com.nimbusds.srp6.SRP6ClientSession;
import com.nimbusds.srp6.SRP6CryptoParams;
import com.nimbusds.srp6.SRP6Exception;
import com.nimbusds.srp6.SRP6ServerSession;
import com.nimbusds.srp6.SRP6VerifierGenerator;

//import de.cryptone.crypto.CryptFactor;

public class Main {

	public static void main(String[] args) {
//	Returns an SRP-6a crypto parameters instance with precomputed 
//	512-bit prime 'N', matching 'g' value and "SHA-1" hash algorithm.
//	Returns: SRP-6a crypto parameters instance with 512-bit prime 'N', 
//	matching 'g' value and "SHA-1" hash algorithm.
	SRP6CryptoParams config = SRP6CryptoParams.getInstance();

	// Create verifier generator
	SRP6VerifierGenerator gen = new SRP6VerifierGenerator(config);

	// Random 16 byte salt 's'
	BigInteger salt = new BigInteger(SRP6VerifierGenerator.generateRandomSalt());
	

	// Username and password
	String username = "alice";
	String password = "secret";



	// Compute verifier 'v'
	BigInteger verifier = gen.generateVerifier(salt, username, password);

	SRP6ClientSession client = new SRP6ClientSession();
	client.step1(username, password+"ds");


	//SRP6CryptoParams config = SRP6CryptoParams.getInstance();

	SRP6ServerSession server = new SRP6ServerSession(config);

	// Retrieve user verifier 'v' + salt 's' from database
	BigInteger v = verifier; 
	BigInteger s = salt; 

	// Compute the public server value 'B'
	BigInteger B = server.step1(username, s, v);
	System.out.println( B.toString() );



	SRP6ClientCredentials cred1 = null;

	try {
	        cred1 = client.step2(config, s, B);

	} catch (SRP6Exception e) {
	        // Invalid server 'B'
	}

	BigInteger M2 = null;

	try {
	        M2 = server.step2(cred1.A, cred1.M1);

	} catch (SRP6Exception e) {
	        // User authentication failed
	}

	try {
        client.step3(M2);
        System.out.println( "well authenticated ");

		} catch (SRP6Exception e) {
				// Server not authenticated
		}
	

		
	}

}
