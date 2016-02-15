package de.hsmannheim.security;

import com.google.common.base.Optional;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;


public class TokenAuthenticator implements AuthenticationProvider {

    public TokenAuthenticator(){
    }

   @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }
   
    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }

}
