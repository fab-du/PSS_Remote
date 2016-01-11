package de.hsmannheim.cryptolocal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter{

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {

    	String header = request.getHeader("X-Access-Token");
    
    	System.out.println("PrePOST HAndler");
    	return true;
    }


}
