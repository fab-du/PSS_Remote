package de.hsmannheim.cryptolocal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter{

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
		

    	String header = request.getHeader("X-Access-Token");
    	
    	int status = response.getStatus();
    	
    	/*
    	 * !Important 
    	 * If internal server error. 
    	 * reset the response object to avoid an attacker
    	 * to identify server error. 
    	 * furthermore the developper team should be mail , 
    	 * and the error have to be log. 
    	 */
    	if ( status >= 500 && status < 600 ){
    		response.reset();
    		response.setStatus(200);
    	}
    	
    	System.out.println("POST HAndler");
	}
	
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {

    	return true;
    }


}
