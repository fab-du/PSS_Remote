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

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/*
 * @Order(Ordered.HIGHEST_PRECEDENCE)
 * So the filter will be executed before the 
 * Authentication check;
 * 
 */

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class CorsFilter implements Filter {

	  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
		    HttpServletResponse response = (HttpServletResponse) res;
		    HttpServletRequest request = (HttpServletRequest) req;
		    response.setHeader("Access-Control-Allow-Origin", "*");
		    response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
		    response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		    response.setHeader("Access-Control-Max-Age", "3600");

		      try {
				chain.doFilter(req, res);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }

		  public void init(FilterConfig filterConfig) {}

		  public void destroy() {}
}
