package de.hsmannheim.cryptolocal.repositories.impl;

import java.math.BigInteger;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

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
	
	private final static Logger log = LoggerFactory.getLogger( ServiceSession.class);
	public static final int SCHEDULE_TIME = 36000; 


	/*
	 * searchStrin := email or token
	 */
	public Session userExists( String searchString ){
		Session session1 = repositorysession.findOneByEmail(searchString);
		Session session2 = repositorysession.findOneByToken(searchString);
	
		if ( session1 != null ) return session1;
		if( session2 != null ) return session2;
		return null;
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
		repositorysession.save(session);
	}
	
	protected boolean isExpired( Long expireIn  ){
		Long currentTimeMilli = System.currentTimeMillis();
		return ( expireIn <= currentTimeMilli );
	}
	
	public void deletEpiredSession(){
		Iterable<Session> activeSessions = repositorysession.findAll();

//		for (Session session : activeSessions) {
//				Long expire_in = session.getExpiresIn();
//
//				if( this.isExpired(expire_in) ){
//					repositorysession.delete(session.getId());
//				}
//		}
	}
	
	@Scheduled(fixedRate= SCHEDULE_TIME)
	public void removeExpiredSession(){
		//log.info("remove expired token from database");
		//this.deletEpiredSession();
	}
	
	public void logout( String token ){
	}
}
