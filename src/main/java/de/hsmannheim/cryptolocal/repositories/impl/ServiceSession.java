package de.hsmannheim.cryptolocal.repositories.impl;

import java.math.BigInteger;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nimbusds.srp6.SRP6VerifierGenerator;

import de.hsmannheim.cryptolocal.models.Group;
import de.hsmannheim.cryptolocal.models.KeyPair;
import de.hsmannheim.cryptolocal.models.Session;
import de.hsmannheim.cryptolocal.models.SrpCredential;
import de.hsmannheim.cryptolocal.models.User;
import de.hsmannheim.cryptolocal.repositories.RepositoryKeyPair;
import de.hsmannheim.cryptolocal.repositories.RepositorySession;
import de.hsmannheim.cryptolocal.repositories.RepositorySrpCredential;
import de.hsmannheim.cryptolocal.repositories.RepositoryUsers;

@Service
@Transactional
public class ServiceSession {
	@Autowired
	private RepositoryUsers repositoryuser;
	@Autowired
	private RepositoryKeyPair repositorykeypair;

	@Autowired
	RepositorySrpCredential repositorysrpcredential;

	@Autowired
	private RepositorySession repositorysession;


	public boolean userExists( Long userId ){
		return repositoryuser.exists( userId );
	}
	
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
	
	private void saveSession( Map<String, String> sessionData ){
		Session session = new Session();
		session.setEmail(sessionData.get("email"));
		session.setB(sessionData.get("B"));
		session.setExpiresIn(3600);
		session.setAccessToken(new BigInteger(SRP6VerifierGenerator.generateRandomSalt()).toString() );
		session.setClientPubkey(sessionData.get("client_pubkey"));
		repositorysession.save(session);
	}
	
	public boolean isAuthenticate( String token ){
		boolean ret = false;
		
		return ret; 
	}
	
	public void logout( String token ){
		Session session = repositorysession.findOneByAccessToken(token);
		if( token != null ) 
			repositorysession.delete(session);
	}
}
