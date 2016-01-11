package de.hsmannheim.cryptolocal.repositories.impl;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

//import de.cryptone.crypto.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Service;


import de.hsmannheim.cryptolocal.models.User;
import de.hsmannheim.cryptolocal.models.forms.FormRegister;
import de.hsmannheim.cryptolocal.models.Group;
import de.hsmannheim.cryptolocal.models.KeyPair;
import de.hsmannheim.cryptolocal.models.Session;
import de.hsmannheim.cryptolocal.models.SrpCredential;
import de.hsmannheim.cryptolocal.repositories.RepositoryCredential;
import de.hsmannheim.cryptolocal.repositories.RepositoryGroup;
import de.hsmannheim.cryptolocal.repositories.RepositoryKeyPair;
import de.hsmannheim.cryptolocal.repositories.RepositoryUsers;
import de.hsmannheim.cryptolocal.repositories.RepositoryKeysym;
import de.hsmannheim.cryptolocal.repositories.RepositorySession;
import de.hsmannheim.cryptolocal.repositories.RepositorySrpCredential;
//import de.cryptone.crypto.CryptFactor;

import com.nimbusds.srp6.SRP6CryptoParams;
import com.nimbusds.srp6.SRP6ServerSession;
import com.nimbusds.srp6.SRP6VerifierGenerator;

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
	private RepositoryKeyPair repositorykeypair;

	@Autowired
	private RepositoryCredential repositorycredential;

	@Autowired
	private RepositorySession repositorysession;

	@Autowired
	private RepositoryGroup repositorygroup;
	@Autowired
	private RepositoryKeysym repositorykeysym;

	@Autowired
	RepositorySrpCredential repositorysrpcredential;

	@Autowired
	ServiceGroup servicegroup;
	
	public boolean register( Map<String, String> user){

		User existinguser = repositoryuser.findOneByEmail(user.get("email"));

		if( existinguser != null  ){
			return false;
		}

		User newuser = new User();
		newuser.setCompany( user.get("company"));
		newuser.setFirstname(user.get("firstname"));
		newuser.setSecondname(user.get("secondname"));
		newuser.setEmail( user.get("email"));

		repositoryuser.save(newuser);


		KeyPair keytosave = new KeyPair();
		keytosave.setPrikey(user.get("prikey"));
		keytosave.setPubkey(user.get("pubkey"));
		keytosave.setSalt(user.get("pairkeySalt"));
		
 		repositorykeypair.save( keytosave );

 		newuser = repositoryuser.findOneByEmail(user.get("email"));
 		/* set key pair */
 		newuser.setKeypair(keytosave);


 		if( user.get("salt") != null && user.get("verifier") != null ){
 			 SrpCredential srpcredentials = new SrpCredential( user.get("email"),  user.get("salt"), user.get("verifier"), "user" );
			 repositorysrpcredential.save(srpcredentials);
			 newuser.setSrp(srpcredentials);
			 repositoryuser.save(newuser);
 		}

		Group group = new Group();
		group.setName( user.get("firstname") + "_private_group");
 		//servicegroup.save(group, newuser.getId(), true);

		return true;
	}

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
				  this.saveSession(authdata, response );
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

	private void saveSession( Map<String, String> sessionData, HttpServletResponse response ){
		Session session = new Session();
		session.setEmail(sessionData.get("email"));
		session.setB(sessionData.get("B"));
		session.setExpires_in(3600);
		session.setAccess_token(new BigInteger(SRP6VerifierGenerator.generateRandomSalt()).toString() );
		session.setClient_pubkey(sessionData.get("client_pubkey"));


		//set session 
		repositorysession.save(session);
	}

	private HttpHeaders buildHeader(  Map<String, String> sessionData, HttpServletResponse response ){
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Client_pubkey", sessionData.get("client_pubkey") );
		responseHeaders.set("Expires", "3600" );
		responseHeaders.set("Access-Control-Expose-Headers", "Client_pubkey, Expires");
		return responseHeaders;
	}


}
