package de.hsmannheim.cryptolocal.repositories.impl;
import java.math.BigInteger;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import de.hsmannheim.cryptolocal.models.User;
import de.hsmannheim.cryptolocal.models.Session;
import de.hsmannheim.cryptolocal.models.SrpCredential;
import de.hsmannheim.cryptolocal.repositories.RepositoryUsers;
import de.security.TokenUtils;
import de.hsmannheim.cryptolocal.repositories.RepositorySession;
import de.hsmannheim.cryptolocal.repositories.RepositorySrpCredential;
//import de.cryptone.crypto.CryptFactor;

import com.nimbusds.srp6.SRP6CryptoParams;
import com.nimbusds.srp6.SRP6ServerSession;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;

/*
 * crypto
 */

@Service
@Transactional
public class ServiceUser  {

	SRP6ServerSession  srpSession;
	
	@Autowired
	RepositorySession repositorySession;
	
	@Autowired
	private RepositoryUsers repositoryuser;
	
	@Autowired
	RepositorySrpCredential repositorysrpcredential;


	@Autowired
	ServiceGroup servicegroup;
	

	public User findByEmail( String email ){
		return repositoryuser.findOneByEmail(email);
	}
	
	public ResponseEntity<Iterable<User>> find(){
		List<User> users = repositoryuser.findAll();
		
		if(users != null && users.iterator().hasNext() ) 
		  return new ResponseEntity<Iterable<User>>(users, HttpStatus.OK);
		  return new ResponseEntity<Iterable<User>>(HttpStatus.NO_CONTENT);
	}
	
	public ResponseEntity<User> findOne( Long id){
		User user = repositoryuser.findOne(id);
		if( user != null )
			return new ResponseEntity<User>( user , HttpStatus.OK);
			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
	
	
	/*
	 * Friends related
	 */
	public ResponseEntity<Iterable<User>> find_friends( long id){
		User user = repositoryuser.findOne(id); 	
		if ( user == null )
			  return new ResponseEntity<Iterable<User>>(HttpStatus.NO_CONTENT);
				return null;
	}
 	
	public static PrivateKey priKeyFromString( String prikey ){
		byte prikeybytes[] = Base64.getDecoder().decode( prikey.getBytes() );
		KeyFactory keyFactory = null;
		PrivateKey privatekey = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
			KeySpec privateKeySpec = new PKCS8EncodedKeySpec(prikeybytes);
			privatekey = keyFactory.generatePrivate(privateKeySpec);
		} catch (Exception e) {
			return null;
		} 

		return privatekey;
	}
	
	

	public boolean userExists( Long userId ){
		return repositoryuser.exists( userId );
	}

	public boolean usereExists( String email ){
		
		if( email == null ) return false;
		
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
		
		if( !this.usereExists(email)) return null; 
		
		SRP6CryptoParams config = SRP6CryptoParams.getInstance();

		SrpCredential srp = repositorysrpcredential.findOneByEmail(email);

		if( srp == null ) return null;

		srpSession = new SRP6ServerSession(config);
			

			BigInteger B = srpSession.step1(srp.getEmail(), new BigInteger( srp.getSalt()), 
						new BigInteger( srp.getVerifier() ) );

			Map<String, String > result = new HashMap<String, String>();

			result.put("B", B.toString());
			result.put("salt", srp.getSalt());
			
			// save session 
			Session session = new Session();
			session.setEmail(email);
			session.setB( B.toString());
			session.setSalt( srp.getSalt());
			repositorySession.save(session);
			
			return result;
	}

	public ResponseEntity<Map<String, String>> 
		step2(Map<String, String> authdata ) throws Exception {
	
		  BigInteger evidence = null;
		  Map<String, String > result = 
				  new HashMap<String, String>();

          String A = authdata.get("A");
          String M1 =  authdata.get("M1");

          try {
				  evidence = srpSession.step2( new BigInteger(A) , new BigInteger(M1) );
				  System.out.println("======" + evidence.toString());
				  result.put("evidence",  evidence.toString());
				  User user = this.findByEmail( authdata.get("email"));
				  result.put("currentUserId", user.getId().toString());
				  result.put("email", user.getEmail());
				  
				  HttpHeaders responseHeaders = new HttpHeaders();
				  
				  //session stuff
				  Session session = repositorySession.findOneByEmail( user.getEmail() );
				  TokenUtils tokenUtils = new TokenUtils();
				  String token =  tokenUtils.generateToken(session);
				  session.setToken(token);
				  repositorySession.save(session);
				  responseHeaders.set("Authorization", "Bearer " + token);
				 
				 return new ResponseEntity<Map<String,String>>(result, responseHeaders, HttpStatus.OK);
				  
          	  } catch (Exception e) {
          		  System.out.println( e.getCause());
          		System.out.println("===================================");
          		System.out.println("===================================");
          		result.put("error", e.getMessage());
          		  return 
          			new ResponseEntity<Map<String,String>>(result, HttpStatus.UNAUTHORIZED);
		      }

	}
	
}
