package de.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;

import de.app.model.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.servlet.FilterChain;
import java.io.IOException;
import java.util.Enumeration;


public class AuthFilter extends GenericFilterBean {
   	TokenUtils tokenUtils;
	HttpServletResponse httpResponse;
    AuthenticationManager authManager;

    public AuthFilter( AuthenticationManager authManager, TokenUtils tokenUtils ){
        this.authManager = authManager;
        this.tokenUtils = tokenUtils;
    }

    @Override
    public void doFilter(ServletRequest request,ServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

    	
        System.out.println("FooFilter");
        HttpServletRequest httpRequest = asHttp( request );
        httpResponse = asHttp( response );
        this.printAllheader(httpRequest);
        String authorizationToken = (String)httpRequest.getHeader("Authorization");
        
        this.printAllheader(httpRequest);
        if ( authorizationToken == null )
             authorizationToken = (String)httpRequest.getHeader("authorization");

        if (authorizationToken == null || !authorizationToken.startsWith("Bearer ")) {
        	httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        	return;
        }

        String token = authorizationToken.substring(7);
        System.out.println( "=========================================");
        System.out.println( token );
        
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
    	Session session;
		try {
			session = tokenUtils.parseToken(token);
		} catch (Exception e) {
			return null;
		}
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

    void printAllheader( HttpServletRequest req ){
    	Enumeration<String> headers = req.getHeaderNames();
    	String headername;
        System.out.println( "=========================================");
        System.out.println( "=========DEBUG===========================");
    	while( (headername = headers.nextElement()) != null ){
    		System.out.println(headername + ":" + req.getHeader(headername));
    	}
    }
}


