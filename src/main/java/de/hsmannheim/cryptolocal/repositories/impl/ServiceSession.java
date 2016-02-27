package de.hsmannheim.cryptolocal.repositories.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import de.hsmannheim.cryptolocal.models.KeyPair;
import de.hsmannheim.cryptolocal.models.Session;
import de.hsmannheim.cryptolocal.models.SrpCredential;
import de.hsmannheim.cryptolocal.models.User;
import de.hsmannheim.cryptolocal.models.forms.FormRegister;
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
	
	public static final int SCHEDULE_TIME = 5 * 60 * 1000; // 5 min 
	public static final int SESSION_TIME  = SCHEDULE_TIME * 2; // 10 min

	/*
	 * searchStrin := email or token
	 */
	public Session userExists( String searchString ){
		Session session1 = repositorysession.findOneByEmail(searchString);
		Session session2 = repositorysession.findOneByToken(searchString);
		
		if ( session1 != null ) return session1; else{}
		if ( session2 != null ) return session2; else{}
		return null;
	}
	
	public boolean register( FormRegister user) throws Exception{

	if (repositoryuser.findOneByEmail(user.getEmail()) != null )
		throw new Exception("User already exists");

		User newuser = new User();
		newuser.setCompany(user.getCompany());
		newuser.setFirstname(user.getFirstname());
		newuser.setSecondname(user.getSecondname());
		newuser.setEmail(user.getEmail());

		repositoryuser.save(newuser);

		KeyPair keytosave = new KeyPair();
		keytosave.setPrikey(user.getPrikey());
		keytosave.setPubkey(user.getPubkey());
		keytosave.setSalt(user.getSalt());
		
 		repositorykeypair.save( keytosave );

 		newuser = repositoryuser.findOneByEmail(user.getEmail());
 		/* set key pair */
 		newuser.setKeypair(keytosave);

 	if( user.getSalt() != null && user.getVerifier() != null ){
 	  SrpCredential srpcredentials = 
 	    new SrpCredential( user.getEmail(),  
 	    	user.getSalt(), user.getVerifier(), "user" );
			 repositorysrpcredential.save(srpcredentials);
			 newuser.setSrp(srpcredentials);
			 repositoryuser.save(newuser);
 		}

		return true;
	}
	
	protected boolean isExpired( Long expireIn  ){
		Long currentTimeMilli = System.currentTimeMillis();
		return ( expireIn <= currentTimeMilli );
	}
	
	public void deleteEpiredSession(){
		Long currentDateTime = new Date().getTime();
		Iterable<Session> expiredSessions = 
		repositorysession.findByExpiresLessThan( currentDateTime );

		for (Session session : expiredSessions) {
			repositorysession.delete(session.getId());
		}
	}
	
	@Scheduled(fixedRate= SCHEDULE_TIME)
	public void removeExpiredSession(){
		log.info("remove expired token from database");
		this.deleteEpiredSession();
	}
	
	public void logout( String token ) throws Exception{
		repositorysession.deleteByToken(token);
	}
}
