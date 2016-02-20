package de.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;

import de.hsmannheim.cryptolocal.models.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import java.io.IOException;


public class AuthFilter extends GenericFilterBean {

	HttpServletResponse httpResponse;
    AuthenticationManager authManager;

    public AuthFilter( AuthenticationManager authManager ){
        this.authManager = authManager;
    }

    @Override
    public void doFilter(ServletRequest request,ServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("FooFilter");
        HttpServletRequest httpRequest = asHttp( request );
         httpResponse = asHttp( response );

        String authorizationToken = (String)httpRequest.getHeader("Authorization");
        System.out.println( authorizationToken );
        if (authorizationToken == null || !authorizationToken.startsWith("Bearer ")) {
        	httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        	return;
        }

        String token = authorizationToken.substring(7);


        this.processTokenAuthentication( token );

        filterChain.doFilter(request, response);
    }

    private HttpServletRequest asHttp(ServletRequest request) {
        return (HttpServletRequest) request;
    }

    private HttpServletResponse asHttp(ServletResponse response) {
        return (HttpServletResponse) response;
    }

    private void processTokenAuthentication(String token) {
            Authentication resultOfAuthentication = tryToAuthenticateWithToken(token);
            SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
    }


    private Authentication tryToAuthenticateWithToken( String token ){
    	TokenUtils tokenUtils = new TokenUtils();
    	Session session = tokenUtils.parseToken(token);
        PreAuthenticatedAuthenticationToken requestAuthentication = new PreAuthenticatedAuthenticationToken(session, null);
        return tryToAuthenticate(requestAuthentication);
    }

    private Authentication tryToAuthenticate( Authentication requestAuthentication ){
        Authentication responseAuthentication  = authManager.authenticate( requestAuthentication );
        if (responseAuthentication == null || !responseAuthentication.isAuthenticated()) {
           return responseAuthentication;
        }

        return responseAuthentication;
    }

}
