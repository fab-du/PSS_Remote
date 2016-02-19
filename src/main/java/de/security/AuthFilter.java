package de.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.servlet.FilterChain;
import java.io.IOException;

import de.hsmannheim.exceptions.JwtException;

public class AuthFilter extends GenericFilterBean {

    AuthenticationManager authManager;

    public AuthFilter( AuthenticationManager authManager ){
        this.authManager = authManager;
    }

    @Override
    public void doFilter(ServletRequest request,ServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("FooFilter");
        HttpServletRequest httpRequest = asHttp( request );
        HttpServletResponse httpResponse = asHttp( response );

        String authorizationToken = (String)httpRequest.getHeader("Authorization");

        if (authorizationToken == null || !authorizationToken.startsWith("Bearer ")) {
            throw new JwtException("No JWT token found in request headers");
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
            System.out.println( resultOfAuthentication.isAuthenticated());
            SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
    }


    private Authentication tryToAuthenticateWithToken( String token ){
        PreAuthenticatedAuthenticationToken requestAuthentication = new PreAuthenticatedAuthenticationToken(token, null);
        return tryToAuthenticate(requestAuthentication);
    }

    private Authentication tryToAuthenticate( Authentication requestAuthentication ){
        Authentication responseAuthentication  = authManager.authenticate( requestAuthentication );
        if (responseAuthentication == null || !responseAuthentication.isAuthenticated()) {
            throw new InternalAuthenticationServiceException("Unable to authenticate Domain User for provided credentials");
        }

        return responseAuthentication;
    }

}
