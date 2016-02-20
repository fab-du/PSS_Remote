package de.hsmannheim;

import com.google.common.base.Preconditions;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.ValidatableResponse;
import com.nimbusds.srp6.SRP6CryptoParams;
import com.nimbusds.srp6.SRP6VerifierGenerator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import de.hsmannheim.cryptolocal.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class AuthTest {

    @Value("${local.server.port}")
    int port;
    
    String email = "dodo@dodo.com"; 
    String password = "dodo";

    @Before
    public void setup() {
        System.out.println( port );
        RestAssured.baseURI = "http://localhost";
       // RestAssured.keystore(keystoreFile, keystorePass);
        RestAssured.port = port;
        //Mockito.reset(mockedExternalServiceAuthenticator, mockedServiceGateway);
        
    }

    @Test
    public void  sessionLoginChallenge_availableForEveryOne() {	
    Map<String, String> user = new HashMap<>();
    user.put("email", "dodo@dodo.com");
    
    given().body( user ).
	when().post("/session/login/challenge").
                then().statusCode(HttpStatus.OK.value() );
    }

    @Test
    public void  sessionLoginAuthenticate_availableForEveryOne() {
        when().post("/session/login/authenticate").
                then().statusCode(HttpStatus.OK.value() );
    }

    @Test
    public void  sessionRegister_availableForEveryOne() {
        when().post("/session/login/register").
                then().statusCode(HttpStatus.UNAUTHORIZED.value() );
    }

    @Test
    public void apiUsersProtected(){
    	when().get("/api/users").then().statusCode( HttpStatus.UNAUTHORIZED.value() );
    }
    
    @Test
    public void apiDocumentsProtected(){
    	when().get("/api/documents").then().statusCode( HttpStatus.UNAUTHORIZED.value() );
    }
    
    @Test
    public void apiGroupProtected(){
    	when().get("/api/groups").then().statusCode( HttpStatus.UNAUTHORIZED.value() );
    }
    
    
	public void register(  Map<String, String> registration ){

		System.out.println("comme hrer");
		Preconditions.checkNotNull( registration, "No registration daten provided");
		
		Preconditions.checkNotNull( registration.get("email"), "No Email provided"); 
		Preconditions.checkNotNull( registration.get("firstname"), "Email not provided"); 
		Preconditions.checkNotNull( registration.get("secondname"), "Secondname not provided"); 
		Preconditions.checkNotNull( registration.get("company"), "Company not provided"); 
		Preconditions.checkNotNull( registration.get("password"), "Password not provided"); 
		Preconditions.checkNotNull( registration.get("passphrase"), "Passphrase not provided"); 

		System.out.println( registration.get("email"));
		
		SRP6CryptoParams config = SRP6CryptoParams.getInstance(); 
		
		SRP6VerifierGenerator gen = new SRP6VerifierGenerator(config); 
		BigInteger salt = new BigInteger(SRP6VerifierGenerator.generateRandomSalt());

		BigInteger verifier = gen.generateVerifier(salt, registration.get("email"), registration.get("password"));
		
		Map<String, String> data = new LinkedHashMap<>();
		data.put("email", registration.get("email"));
		data.put("firstname", registration.get("firstname"));
		data.put("secondname", registration.get("secondname"));
		data.put("company", registration.get("company"));
		data.put("verifier", verifier.toString());
		data.put("salt", salt.toString());
		
//		RSAKeyGenerator keygen = new RSAKeyGenerator();
//		de.app.model.KeyPair pairkey = keygen.generate();
//		data.put("pubkey", pairkey.getPubkey());
//		data.put("prikey", pairkey.getPrikey());
		
	}
	

	public void login_challenge( Map<String, String> authdata ) throws Exception{
		
//		ResponseEntity<LinkedHashMap<String, String>> result = null;
//		String email = authdata.get("email"); 
//		String password = authdata.get("password"); 
//		System.out.println( email + "//" + password);
//		serviceuser.step1( email, password);
//		Map<String, String> challenge = new HashMap<String, String>();
//		challenge.put("email", email);
//
//		LinkedHashMap<String, String> body = result.getBody();
//		
//		if( body  == null ){
//			LinkedHashMap<String, String> errorMessage = new LinkedHashMap<String, String>();
//			return 
//			new ResponseEntity<LinkedHashMap<String,String>>(errorMessage, HttpStatus.UNAUTHORIZED );
//		}
//		
//		Map<String, String> result1 = serviceuser.step2( body );
//
//		if( result1  == null ){
//			LinkedHashMap<String, String> errorMessage = new LinkedHashMap<String, String>();
//			return 
//			new ResponseEntity<LinkedHashMap<String,String>>(errorMessage, HttpStatus.UNAUTHORIZED );
//		}
//		
//		KeyPair keyGenerator = new KeyPair();
//		String keypair = keyGenerator.algorithm("RSA").generate();
//		System.out.println(keypair);
//		Gson gson = new Gson();
//		Map<String, String> _keypair = new HashMap<>();
//		_keypair = gson.fromJson(keypair, Map.class);
//		
//		if( _keypair  == null ){
//			LinkedHashMap<String, String> errorMessage = new LinkedHashMap<String, String>();
//			return 
//			new ResponseEntity<LinkedHashMap<String,String>>(errorMessage, HttpStatus.UNAUTHORIZED );
//		}
//
//		result1.put("client_pubkey", _keypair.get("pubKey"));
//		result1.put("email", email);
//		result1.put("B", body.get("B"));
//		
//		ResponseEntity<LinkedHashMap<String, String>> endResponse = 
//		POST.simplePost("/session/login/authenticate", result1);
//
//		HttpHeaders headers = new HttpHeaders(); 
//		headers = endResponse.getHeaders();
//		LinkedHashMap<String, String> zwischenErg = endResponse.getBody();
//		
//		if ( zwischenErg == null ){
//			LinkedHashMap<String, String> errorMessage = new LinkedHashMap<String, String>();
//			return new ResponseEntity<LinkedHashMap<String,String>>(errorMessage, HttpStatus.UNAUTHORIZED );
//		}
//		zwischenErg.put("prikey", _keypair.get("priKey"));
//		return new ResponseEntity<LinkedHashMap<String, String>>( zwischenErg, headers, HttpStatus.OK );
	}

}
