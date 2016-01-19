package de.hsmannheim.cryptolocal;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Base64;
import java.util.Enumeration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.RsaProvider;
import io.jsonwebtoken.impl.crypto.RsaSigner;
import scala.annotation.meta.getter;

import java.security.Key;
import java.security.KeyPair;

import de.hsmannheim.cryptolocal.repositories.impl.ServiceSession;


/*
 * @Order(Ordered.HIGHEST_PRECEDENCE)
 * So the filter will be executed before the 
 * Authentication check;
 * 
 */

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class CorsFilter implements Filter {
	
	public static final String PROTECTED_URI = "api";
	
	public static final String URL = "http://localhost:8080";
	
	public static final String CONTENT_SECURITY_POLICY = "content-security-header", 
							   CONTENT_SECURITY_POLICY_VALUE = "script-src 'self'";
	
	public static final String X_XSRF_TOKEN = "X-XSRF-TOKEN";
	
	public static final String REALM = "realm", 
							   REALM_VALUE="realm";
	
	public static final String WWW_AUTHENTICATION = "WWW-Authentication";
	
	public static final String HASH_ALGORITHM = "hash-algorithm"; 
	
	public static final String EXPIRES_IN = "EXPIRES-IN";
	
	public static final String CLIENT_PUBLIC_KEY = "CLIENT-PUBLIC-KEY";
	
	public static final String SERVER_PUBLIC_KEY = "SERVER-PUBLIC-KEY";
	
	public static final String SERVER_PRIVATE_KEY = "SERVER-PRIVATE-KEY";

	
	public static final String TYP = "typ",
							   TYP_VALUE = "JWT";
	
	public static final String ALG = "alg",
							   ALG_VALUE = "RS512";
	
	public static final String AUTHORIZATION = "Authorization",
			   AUTHORIZATION_VALUE = "Bearer ";
	
	
	
	@Autowired 
	ServiceSession serviceSession;
	
	private HttpServletRequest req; 
	private HttpServletResponse res; 

	  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
		    HttpServletResponse response = (HttpServletResponse) res;
		    HttpServletRequest request = (HttpServletRequest) req;
		    this.req = request; 
		    this.res = response;
		    
		    
		    /*
		     * check authorization header
		     */
		    
		    String authHeader = this.req.getHeader(AUTHORIZATION);
		    
		    if(  authHeader == null && !this.matchProtectedUri( request.getRequestURI())){
		    	this.redirect(request, response, "/auth_error");
		    	return;
		    }
		    
		    if( this.matchProtectedUri( request.getRequestURI())){
		    	//authenticate
		    }
		    

		    if ( this.isUri("/session/login/challenge")){
		      	KeyPair serverKey = RsaProvider.generateKeyPair(512);
		      	String privateKey = Base64.getEncoder().encodeToString( serverKey.getPrivate().getEncoded());
		      	String publicKey = Base64.getEncoder().encodeToString( serverKey.getPublic().getEncoded());
		      	response.setHeader(SERVER_PUBLIC_KEY, publicKey);
		    	response.setHeader(SERVER_PRIVATE_KEY,  privateKey);
		    }
		    
		    else if( this.isUri("/session/login/authenticate")){
		    	if( request.getHeader(SERVER_PUBLIC_KEY) == null ){
		    		this.redirect(request, response, "/auth_error");
		    		return;
		    	}    	
		    }
		    else if( this.isUri("/session/logout")){
		    	String access_token = this.req.getHeader(AUTHORIZATION);   	
		    	if( access_token == null || access_token == ""){
		    		this.redirect(this.req, this.res, "/auth_error");
		    	}
		    	else if ( access_token != null && access_token != "" ){
			    	serviceSession.logout( access_token  );
		    	}
		    	else{}
		    }
		    else{}

		    try {
				chain.doFilter(req, res);
				return;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ServletException e) {
				e.printStackTrace();
			}
		    
		  }

		  public void init(FilterConfig filterConfig) {}
		  public void destroy() {}
		  
		  
		  public boolean isUri( String uri ){
			  boolean ret = false;
			  if( req.getRequestURI().trim().equals(uri) ) 
				  ret = true;
			  return ret;
		  }
		  
		  public boolean matchProtectedUri( String uri ){
			  boolean ret = false;
			  String sessionUri = null; 
			  
			  try {
				  sessionUri= uri.split("/")[0];
				  if ( !sessionUri.trim().equals("session") )
					  ret = true;
			} catch (Exception e) {
				
			}			  
	 
			  System.out.println( sessionUri );
			  return ret;
		  }

			public void redirect( HttpServletRequest request, HttpServletResponse response, String uri ){
				try {
					request.getServletContext().getRequestDispatcher(uri).forward(request, response);
				} catch (ServletException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}  
		  
}
