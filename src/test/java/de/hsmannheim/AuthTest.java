package de.hsmannheim;

//import com.google.common.base.Preconditions;
//import com.jayway.restassured.RestAssured;
//import com.jayway.restassured.response.ValidatableResponse;
//import com.nimbusds.srp6.SRP6CryptoParams;
//import com.nimbusds.srp6.SRP6VerifierGenerator;
//
//import org.junit.Before;
//import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.mockito.BDDMockito;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.IntegrationTest;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import static com.jayway.restassured.RestAssured.given;
//import static com.jayway.restassured.RestAssured.when;
//import static org.hamcrest.core.IsEqual.equalTo;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Matchers.eq;
//import static org.mockito.Mockito.mock;
//
//import java.math.BigInteger;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import de.app.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class AuthTest {

//    @Value("${local.server.port}")
//    int port;
//    
//    String email = "dodo@dodo.com"; 
//    String password = "dodo";
//
//    @Before
//    public void setup() {
//        System.out.println( port );
//        RestAssured.baseURI = "http://localhost";
//       // RestAssured.keystore(keystoreFile, keystorePass);
//        RestAssured.port = port;
//        //Mockito.reset(mockedExternalServiceAuthenticator, mockedServiceGateway);
//        
//    }
//
//    @Test
//    public void  sessionLoginChallenge_availableForEveryOne() {	
//    Map<String, String> user = new HashMap<>();
//    user.put("email", "dodo@dodo.com");
//    
//    //given().body( user ).
//	//when().post("/session/login/challenge").
//      //          then().statusCode(HttpStatus.OK.value() );
//    }
//
//    @Test
//    public void  sessionLoginAuthenticate_availableForEveryOne() {
//       // when().post("/session/login/authenticate").
//         //       then().statusCode(HttpStatus.OK.value() );
//    }
//
//    @Test
//    public void  sessionRegister_availableForEveryOne() {
//      //  when().post("/session/login/register").
//       //         then().statusCode(HttpStatus.UNAUTHORIZED.value() );
//    }
//
//    @Test
//    public void apiUsersProtected(){
//    	//when().get("/api/users").then().statusCode( HttpStatus.UNAUTHORIZED.value() );
//    }
//    
//    @Test
//    public void apiDocumentsProtected(){
//      //  when().get("/api/documents").then().statusCode( HttpStatus.UNAUTHORIZED.value() );
//    }
//    
//    @Test
//    public void apiGroupProtected(){
//       // when().get("/api/groups").then().statusCode( HttpStatus.UNAUTHORIZED.value() );
//    }

}
