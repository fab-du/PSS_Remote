package de.hsmannheim.security;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.AuthenticationEntryPoint;
import javax.servlet.http.HttpServletResponse;


@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	ApplicationContext applicationContext;
	
	
	
	public SecurityConfig() {
		super(true);
	}
	
	@Override 
	protected void configure(HttpSecurity http) throws Exception {

		http.httpBasic().disable();
		http.rememberMe().disable();
		
		http.csrf().disable();
		
		http.x509().disable();
		
		http.formLogin().disable();
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	    
		http.exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint());

            
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}
	
	@Bean
	public AuthFilterR authFilter( AuthenticationManager authmanager ){
		return new AuthFilterR( authmanager );
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
        return new UnauthorizedEntryPoint();
    }
	
}
