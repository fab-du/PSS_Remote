package de.hsmannheim.cryptolocal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import de.hsmannheim.cryptolocal.repositories.impl.ServiceSession;
import de.hsmannheim.cryptolocal.repositories.impl.ServiceUser;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.RsaSigner;
import java.security.Key;

@RestController
@RequestMapping(value="/session")
public class ControllerSession {

	@Autowired
	ServiceUser serviceuser;
	
	@Autowired
	ServiceSession serviceSession;


	@RequestMapping( value="/login/challenge", method = RequestMethod.POST, consumes="application/json" )
	public ResponseEntity<Map<String, String>> login_challenge( @RequestBody Map<String, String> email  ){
		
		boolean userExist = serviceuser.usereExists(email.get("email"));
			if( userExist == true ){
			 Map<String, String> result = new HashMap<String, String>();

			 try {
				result = serviceuser.step1( email.get("email") );
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Map<String,String>>( HttpStatus.OK );
			}

			 return new ResponseEntity<Map<String,String>>(result, HttpStatus.ACCEPTED);
		}

		return null;
	}

	@RequestMapping( value="/login/authenticate", method = RequestMethod.POST )
	public ResponseEntity<Map<String, String>> login( @RequestBody Map<String, String> authdata,
			HttpServletResponse response) throws Exception{
		System.out.println("authentication");

		Map<String, String > result = null;

		try {
			ResponseEntity<Map<String, String>> ret =  serviceuser.step2( authdata, response );
			return ret;
		} catch (Exception e) {
			System.out.println("Ehe hier");
			Map<String, String> errorMessage = new HashMap<String, String>();
			errorMessage.put("AUTH_ERROR", "Authentication failure");
			return new ResponseEntity<Map<String,String>>( errorMessage, HttpStatus.FORBIDDEN );
		}

	}

	@RequestMapping( value="/register", method = RequestMethod.POST )
	public ResponseEntity<LinkedHashMap<String, String>> 
	register( @RequestBody Map<String, String> newregister ) throws RestClientException, Exception{
			boolean ret = serviceSession.register(newregister);
			if ( ret ) return new ResponseEntity<LinkedHashMap<String,String>>( HttpStatus.CREATED );
			return new ResponseEntity<LinkedHashMap<String,String>>( HttpStatus.OK );
	}

	@RequestMapping( value="/logout", method = RequestMethod.POST )
	public ResponseEntity<LinkedHashMap<String, String>> 
	logout(){
		return new ResponseEntity<>(HttpStatus.OK); 
	}
}
