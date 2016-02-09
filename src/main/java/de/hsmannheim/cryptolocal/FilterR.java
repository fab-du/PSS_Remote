package de.hsmannheim.cryptolocal;

import java.io.IOException;
import java.util.Enumeration;

import javax.annotation.Priority;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
@Priority(value=1)
public class FilterR implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		Enumeration<String> headernames = req.getHeaderNames();

		while( headernames.hasMoreElements() ){
			String headername = headernames.nextElement();
			String headervalue = req.getHeader( headername );
			System.out.println( headername + "-----" + headervalue);
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
