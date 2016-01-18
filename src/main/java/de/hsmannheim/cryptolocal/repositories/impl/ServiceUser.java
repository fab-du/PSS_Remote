package de.hsmannheim.cryptolocal.repositories.impl;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

//import de.cryptone.crypto.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import de.hsmannheim.cryptolocal.models.User;
import de.hsmannheim.cryptolocal.models.SrpCredential;
import de.hsmannheim.cryptolocal.repositories.RepositoryCredential;
import de.hsmannheim.cryptolocal.repositories.RepositoryUsers;
import de.hsmannheim.cryptolocal.repositories.RepositorySession;
import de.hsmannheim.cryptolocal.repositories.RepositorySrpCredential;
//import de.cryptone.crypto.CryptFactor;

import com.nimbusds.srp6.SRP6CryptoParams;
import com.nimbusds.srp6.SRP6ServerSession;
import com.nimbusds.srp6.SRP6VerifierGenerator;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.RsaSigner;
import java.security.Key;

/*
 * crypto
 */

@Service
@Transactional
public class ServiceUser  {

	 SRP6ServerSession  srpSession;


		@Autowired
		private RepositoryUsers repositoryuser;


	@Autowired
	private RepositoryCredential repositorycredential;

	@Autowired
	private RepositorySession repositorysession;
	
	@Autowired
	RepositorySrpCredential repositorysrpcredential;




	@Autowired
	ServiceGroup servicegroup;
	


	public boolean userExists( Long userId ){
		return repositoryuser.exists( userId );
	}

	public boolean usereExists( String email ){

		if (email.trim().equals( repositoryuser.findOneByEmail(email.trim()).getEmail()) ){
			return true;
		}
		return false;
	}

	public Iterable<User> getAll(){
		if ( repositoryuser.count() <= 0 ) return null;
		return repositoryuser.findAll();
		
	}

	public Map<String, String> step1(String email) throws Exception {
		SRP6CryptoParams config = SRP6CryptoParams.getInstance();

		SrpCredential srp = repositorysrpcredential.findOneByEmail(email);

		if( srp == null ) return null;

		srpSession = new SRP6ServerSession(config);
			

			BigInteger B = srpSession.step1(srp.getEmail(), new BigInteger( srp.getSalt()), 
						new BigInteger( srp.getVerifier() ) );

			Map<String, String > result = new HashMap<String, String>();

			result.put("B", B.toString());
			result.put("salt", srp.getSalt());
			return result;
	}

	public ResponseEntity<Map<String, String>> 
		step2(Map<String, String> authdata, HttpServletResponse response) throws Exception {
	
		  BigInteger evidence = null;
		  Map<String, String > result = 
				  new HashMap<String, String>();

          String A = authdata.get("A");
          String M1 =  authdata.get("M1");

          try {
				  evidence = srpSession.step2( new BigInteger(A) , new BigInteger(M1) );
				  result.put("evidence",  evidence.toString());
				  HttpHeaders responseHeaders = this.buildHeader(authdata, response);
				  boolean ret = responseHeaders.containsKey("Client_pubkey");
				  System.out.println(ret);
				  System.out.println("\n this is ist");
				  return new ResponseEntity<Map<String,String>>(result, responseHeaders, HttpStatus.OK);
				  
          	  } catch (Exception e) {
          		  result.put("error", e.getMessage());
          		  return 
          			new ResponseEntity<Map<String,String>>(result, HttpStatus.UNAUTHORIZED);
		      }

	}



	private HttpHeaders buildHeader(  Map<String, String> sessionData, HttpServletResponse response ){
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Client_pubkey", sessionData.get("client_pubkey") );
		responseHeaders.set("Expires", "3600" );
		responseHeaders.set("Access-Control-Expose-Headers", "Client_pubkey, Expires");
		return responseHeaders;
	}
	
	private String jwt( String claims, Key key ){
		String token = 
		Jwts.builder().claim("claims", claims).signWith(SignatureAlgorithm.RS512, key ).compact();
		System.out.println( token );
		return token;
	}


}
