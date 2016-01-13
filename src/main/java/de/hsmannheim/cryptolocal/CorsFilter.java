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

import org.hibernate.service.spi.SessionFactoryServiceInitiator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import de.hsmannheim.cryptolocal.repositories.impl.ServiceSession;
import io.netty.handler.codec.http.HttpResponse;


/*
 * @Order(Ordered.HIGHEST_PRECEDENCE)
 * So the filter will be executed before the 
 * Authentication check;
 * 
 */

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class CorsFilter implements Filter {
	
	
	
	@Autowired 
	ServiceSession serviceSession;
	
	private HttpServletRequest req; 
	private HttpServletResponse res; 

	  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
		    HttpServletResponse response = (HttpServletResponse) res;
		    HttpServletRequest request = (HttpServletRequest) req;
		    this.req = request; 
		    this.res = response;
		    
		    if ( this.isUri("/session/login/challenge")){
		    	// nothing to do 
		    	// registration will be handle by controller
		    }
		    else if( this.isUri("/session/login/authenticate")){
		    	
		    }
		    else if( this.isUri("/session/register")){
		    		//nothing todo 
		    		// will also be handle by controller
		    	
		    }
		    else if( this.isUri("/session/logout")){
		    	String access_token = this.req.getHeader("Access-Token");
		    	
		    	if( access_token == null || access_token == ""){
		    		// not authorized 
		    	}
		    	else if ( access_token != null && access_token != "" ){
			    	serviceSession.logout( access_token  );
		    	}
		    	else{}
		    }
		    else if( this.matchProtectedUri(this.req.getRequestURI()) && !this.req.getRequestURI().trim().equals("/auth_error") ){
		    	System.out.println(	this.req.getRequestURI());
		    	System.out.println("comme herer");
		    	 try {
					req.getServletContext().getRequestDispatcher("/auth_error").forward(req, res);
					return;
				} catch ( ServletException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			  String sessionUri = uri.split("/")[0];
			  System.out.println( sessionUri );
			  
			  if ( !sessionUri.trim().equals("session") )
				  ret = true;
			  
			  return ret;
		  }
		  
		  
		  void setHeaders( ){
			    res.setHeader("Access-Control-Allow-Origin", "*");
			    res.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
			    res.setHeader("Access-Control-Allow-Headers", "x-requested-with");
			    res.setHeader("Access-Control-Max-Age", "3600");
			    res.setHeader("access_token", "TODO");
			    res.setHeader("token_type", "Bearer");
			    res.setHeader("refresh_token", "TODO");
			    
			    // JWT 
			    res.setHeader("typ", "JWT");
			    res.setHeader("alg", "HS256");
			    res.setHeader("jti", "TODO : jwt headers");
		  }
}
