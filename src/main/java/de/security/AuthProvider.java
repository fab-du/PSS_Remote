package de.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.AuthenticationException;

import de.hsmannheim.cryptolocal.models.Session;
import de.hsmannheim.cryptolocal.repositories.impl.ServiceSession;

import org.springframework.security.core.Authentication;


public class AuthProvider implements AuthenticationProvider{

	@Autowired
	ServiceSession serviceSession;
	
	@Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Session principal = (Session)authentication.getPrincipal();
        
        Session issuer = serviceSession.userExists( principal.getEmail() );
        
        if( this.isAuthenticate(principal, issuer))
        	return (Authentication)issuer;
        		return null;
    }
   
   
   protected boolean isAuthenticate( Session principal, Session issuer ){
	   if(
			principal.getB().trim().equals(issuer.getB().trim()) &&
			principal.getEmail().trim().equals( issuer.getEmail().trim()) 
	     )
		  return true;
	   		return false;
   }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}

