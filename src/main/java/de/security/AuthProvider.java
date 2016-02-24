package de.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import de.hsmannheim.cryptolocal.models.Session;
import de.hsmannheim.cryptolocal.repositories.impl.ServiceSession;

import org.springframework.security.core.Authentication;


public class AuthProvider implements AuthenticationProvider{

	@Autowired
	ServiceSession serviceSession;
	
	@Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Session principal = (Session)authentication.getPrincipal();
        System.out.println( "========================");
        System.out.println( principal);
        Session issuer = serviceSession.userExists( principal.getEmail() );
        
        return this.isAuthenticate(principal, issuer);
    }
   
   
   protected Authentication isAuthenticate( Session principal, Session issuer ){
	   if(
			principal.getId().equals(issuer.getId()) &&
			principal.getEmail().trim().equals( issuer.getEmail().trim()) 
	     )
	   {
		   return new PreAuthenticatedAuthenticationToken(issuer, null);
//		   AuthModel auth = new AuthModel();
//		   auth.setId( issuer.getId() );
//		   auth.setEmail( issuer.getEmail());
//		   return (Authentication) auth;
	   }
	   		return null;
   }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}

