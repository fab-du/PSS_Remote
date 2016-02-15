package de.hsmannheim.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.AuthenticationEntryPoint;
import javax.servlet.http.HttpServletResponse;


@EnableWebSecurity
@Configuration
@Order(1)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	AuthenticationManager authManager;
	
	public SecurityConfig() {
		super(true);
	}
	
	@Override 
	protected void configure(HttpSecurity http) throws Exception {
	    http
           .csrf()
           .disable();

        http
            .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint());


        http
            .sessionManagement().disable();

	    http.anonymous();

        http
            .requestCache().disable();
	    
	    http
	        .authorizeRequests()
	            .antMatchers("/session/login/challenge").permitAll()
	            .antMatchers("/session/login/authenticate").permitAll()
	            .antMatchers("/session/register").permitAll()
	            .antMatchers("/session/logout").permitAll()
	            .anyRequest().authenticated();

        http.addFilterBefore(new AuthFilterR(authenticationManager()), BasicAuthenticationFilter.class);

	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.authenticationProvider(tokenAuthenticator());
    }

    @Bean
    public AuthenticationProvider tokenAuthenticator() {
        return new TokenAuthenticator();
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
	
}
