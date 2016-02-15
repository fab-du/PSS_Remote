package de.hsmannheim.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	public SecurityConfig() {
		super(true);
	}
	
	@Override 
	protected void configure(HttpSecurity http) throws Exception {
	    http
	        .csrf()
	            .disable()
	        .authorizeRequests()
	            .antMatchers("/session/**").permitAll()
	            .anyRequest().authenticated();
	}
	
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}
	
}
