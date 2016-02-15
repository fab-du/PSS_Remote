package de.hsmannheim.cryptolocal;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Enumeration;

import javax.annotation.Priority;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.activemq.artemis.utils.ByteUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import de.crypto.Hash;
import de.helper.Helper;
import javassist.bytecode.ByteArray;

@Component
@Priority(value=1)
public class FilterR implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
//		HttpServletRequest req = (HttpServletRequest) request;
//		HttpServletResponse res = (HttpServletResponse) response;
//		Enumeration<String> headernames = req.getHeaderNames();
//
//		while( headernames.hasMoreElements() ){
//			String headername = headernames.nextElement();
//			String headervalue = req.getHeader( headername );
//			System.out.println( headername + "-----" + headervalue);
//		}
//		
//		Hash hash = new Hash();
//		ServletInputStream body = req.getInputStream();
//		BufferedInputStream bis = new BufferedInputStream(body);
//		
//		byte[] b = new byte[ body.available()];
//		
//		bis.read(b);
//		
//		System.out.println( hash.checkHash( b, req.getHeader("hash")) );
//		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
