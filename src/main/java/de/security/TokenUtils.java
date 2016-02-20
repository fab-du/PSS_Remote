package de.security;

import de.hsmannheim.cryptolocal.models.Session;
import de.hsmannheim.cryptolocal.repositories.RepositorySession;
import io.jsonwebtoken.Jwts; 
import io.jsonwebtoken.SignatureAlgorithm; 
import io.jsonwebtoken.Claims;

import org.springframework.beans.factory.annotation.Autowired;

public class TokenUtils {

	@Autowired
	RepositorySession repositorySession;

    public Session parseToken( String token ){
    	System.out.println( token );
    	Session session = repositorySession.findOneByToken(token);
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
        session.setEmail( email );
        return session;
    }

}
