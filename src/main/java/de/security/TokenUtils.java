package de.security;

import de.hsmannheim.cryptolocal.models.Session;
import de.hsmannheim.cryptolocal.repositories.RepositorySession;
import de.hsmannheim.cryptolocal.repositories.impl.ServiceSession;
import io.jsonwebtoken.Jwts; 
import io.jsonwebtoken.SignatureAlgorithm; 
import io.jsonwebtoken.Claims;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TokenUtils {

	@Autowired
	ServiceSession repositorySession;

    public Session parseToken( String token ){
    	System.out.println(repositorySession);
    	Session session = repositorySession.userExists(token.trim());
    	String B = session.getB();
        Claims claims = Jwts.parser().setSigningKey( B ).parseClaimsJws(token).getBody();
        return this.claimsToSessionObj( claims );
    }


    public String generateToken( Session session ){

        Claims claims = Jwts.claims().setSubject( session.getEmail( ));
        claims.setId( session.getId().toString() );
        claims.put( "hash", session.getHashValue() );

        return Jwts.builder()
                   .setClaims( claims )
                   .signWith( SignatureAlgorithm.HS512, session.getB() ) 
                   .compact();
    }

    
    private Session claimsToSessionObj( Claims claim ){
        Session session = new Session();
        
        String email = claim.getSubject();
        Long id = new Long(claim.getId());
        
        session.setId(id);
        session.setEmail( email );
        return session;
    }

}
