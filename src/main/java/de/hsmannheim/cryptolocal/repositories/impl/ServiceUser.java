package de.hsmannheim.cryptolocal.repositories.impl;
import java.math.BigInteger;
import java.util.Base64;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import de.hsmannheim.cryptolocal.models.User;
import de.hsmannheim.cryptolocal.models.forms.FormAuthentication;
import de.hsmannheim.cryptolocal.models.forms.FormChallengeResponse;
import de.hsmannheim.cryptolocal.models.forms.FormLoginAuthenticateResponse;
import de.hsmannheim.cryptolocal.models.Group;
import de.hsmannheim.cryptolocal.models.Session;
import de.hsmannheim.cryptolocal.models.SrpCredential;
import de.hsmannheim.cryptolocal.repositories.RepositoryUsers;
import de.security.TokenUtils;
import de.hsmannheim.cryptolocal.repositories.RepositoryGroup;
import de.hsmannheim.cryptolocal.repositories.RepositorySession;
import de.hsmannheim.cryptolocal.repositories.RepositorySrpCredential;
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
	RepositoryUsers repositoryuser;
	@Autowired
	RepositorySrpCredential repositorysrpcredential;
	@Autowired
	ServiceGroup servicegroup;
	@Autowired
	RepositoryGroup repositoryGroup;
	
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
		User user = repositoryuser.findOneByEmail(email.trim());
		if ((user == null) || (user.getEmail() == null)) return false;
		if (email.trim().equals( user.getEmail())) return true;
		return false;
	}

	public Iterable<User> getAll(){
		if ( repositoryuser.count() <= 0 ) return null;
		return repositoryuser.findAll();
		
	}

	public FormChallengeResponse step1(String email) throws Exception{
		
		if( !this.usereExists(email)) return null; 
		
		SRP6CryptoParams config = SRP6CryptoParams.getInstance();
		SrpCredential srp = repositorysrpcredential.findOneByEmail(email);
		if( srp == null ) return null;

		srpSession = new SRP6ServerSession(config);
		BigInteger B = srpSession.step1(srp.getEmail(), new BigInteger( srp.getSalt()), 
						new BigInteger( srp.getVerifier() ) );

		if ( B == null )
			throw new java.lang.Exception("B didnt computed properly ");
			
		// save session 
		Session session = new Session();
		session.setEmail(email);
		session.setB( B.toString());
		session.setSalt( srp.getSalt());
		repositorySession.save(session);
		
		return new FormChallengeResponse(B.toString(), srp.getSalt());
	}

	public ResponseEntity<FormLoginAuthenticateResponse> 
		step2(FormAuthentication authdata ) throws Exception {
	
		if( authdata == null || authdata.getA() == null || authdata.getM1() == null
			|| authdata.getEmail() == null )
			throw new java.lang.Exception("No data provided for authentication");
		
		  BigInteger evidence = null;
		  FormLoginAuthenticateResponse result = new FormLoginAuthenticateResponse();
          try {
        	  	  evidence = srpSession.step2( new BigInteger(authdata.getA()) , new BigInteger(authdata.getM1()) );
				  System.out.println( evidence.toString() );
				  User user = this.findByEmail( authdata.getEmail());
				  
				  result.setEmail(user.getEmail());
				  result.setCurrentUserId(user.getId().toString());
				  result.setEvidence(evidence.toString());
				  result.setCurrentUserPublicKey( user.getKeypair().getPubkey() );
				  
				  Group group = repositoryGroup.findOneByName( authdata.getEmail().trim() + "_privateGroup" );
				  result.setCurrentUserGroupId(group.getId());
				  
				  HttpHeaders responseHeaders = new HttpHeaders();
				  //session stuff
				  Session session = repositorySession.findOneByEmail( user.getEmail() );
				  TokenUtils tokenUtils = new TokenUtils();
				  String token =  tokenUtils.generateToken(session);
				  session.setToken(token);
				  session.setPubkey( authdata.getSpubkey() );
				  session.setExpires( new java.util.Date().getTime() + ServiceSession.SESSION_TIME );
				  repositorySession.save(session);
				  responseHeaders.set("Authorization", "Bearer " + token);
				 return new ResponseEntity<FormLoginAuthenticateResponse>(result, responseHeaders, HttpStatus.OK);
				 
          	  } catch (Exception e) {
          		  System.out.println( e.getCause());
          		  throw new java.lang.Exception("No data provided for authentication");
          	  }
	}
}
