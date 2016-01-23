package de.hsmannheim.cryptolocal;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Component
public class AppConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors( InterceptorRegistry registry ){
		registry.addInterceptor( new AuthInterceptor());	
	}
}
