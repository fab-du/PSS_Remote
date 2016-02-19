package de.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.Authentication;

public class AuthProvider implements AuthenticationProvider{
   @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
       // String principal = authentication.getPrincipal().toString();
        System.out.println(email);
        //System.out.println(principal);
        return authentication;
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}

