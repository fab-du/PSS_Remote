package de.security;

import de.hsmannheim.cryptolocal.models.Session;

import io.jsonwebtoken.Jwts; 
import io.jsonwebtoken.SignatureAlgorithm; 
import io.jsonwebtoken.Claims; 
import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.MissingClaimException;
import org.springframework.beans.factory.annotation.Value;

public class TokenUtils {

    @Value("${jwt.secret}")
    private String secret;

    public Session parseToken( String token ){
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return this.claimsToSessionObj( claims );
    }


    public String generateToken( Session session ){

        Claims claims = Jwts.claims().setSubject( session.getEmail( ));
        claims.setId( session.getId().toString() );
        claims.put( "clientPubKey" ,  session.getClientPubkey()); 
        claims.put( "hash", session.getHashValue() );

        return Jwts.builder()
                   .setClaims( claims )
                   .signWith( SignatureAlgorithm.HS512, secret ) 
                   .compact();
    }

    private Session claimsToSessionObj( Claims claim ){
        Session session = new Session();
        String email = claim.getSubject();
        session.setEmail( email );
        return session;
    }

}
