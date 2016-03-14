package de.app.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import de.app.services.ServiceSession;
import de.app.services.ServiceUser;
import de.app.model.form.FormAuthentication;
import de.app.model.form.FormChallengeResponse;
import de.app.model.form.FormLoginAuthenticateResponse;
import de.app.model.form.FormRegister;

@RestController
@RequestMapping(value="/session")
public class ControllerSession {

	@Autowired
	ServiceUser serviceuser;
	
	@Autowired
	ServiceSession serviceSession;

	@RequestMapping( value="/login/challenge", method = RequestMethod.POST, consumes="application/json" )
	public ResponseEntity<FormChallengeResponse> login_challenge( @RequestBody Map<String, String> challenge  ) throws Exception{
	
	//if user not in system
	String email = challenge.get("email");
	if( email == null || !serviceuser.usereExists(email) )
		throw new AuthenticationCredentialsNotFoundException("No data provided");

	//if user already authenticated 
	if ( serviceSession.userExists(email) != null )
		throw new Exception("User already authenticated");
	
		return new ResponseEntity<FormChallengeResponse>( 
					serviceuser.step1(email), HttpStatus.OK);
	}
	
	@RequestMapping( value="/login/authenticate", method = RequestMethod.POST )
	public ResponseEntity<FormLoginAuthenticateResponse> login( @RequestBody FormAuthentication authdata) throws Exception{
		return  serviceuser.step2( authdata );
	}

	@RequestMapping( value="/register", method = RequestMethod.POST )
	public ResponseEntity<LinkedHashMap<String, String>> 
	register( @RequestBody FormRegister newregister ) throws RestClientException, Exception{
			boolean ret = serviceSession.register(newregister);
			if ( ret ) return new ResponseEntity<LinkedHashMap<String,String>>( HttpStatus.CREATED );
			return new ResponseEntity<LinkedHashMap<String,String>>( HttpStatus.OK );
	}

	@RequestMapping( value="/logout", method = RequestMethod.POST )
	public ResponseEntity<?> 
	logout( @RequestHeader(value="Authorization") String
	Auth, @RequestHeader(value="authorization") String auth) throws Exception{
		
		String authToken = null;
		
		if( auth != null  ){
			authToken = auth;
		}
		else if ( Auth != null ){
			authToken = Auth;
		}else{}
		
		if ( authToken != null )
			serviceSession.logout( authToken.split(" ")[1].trim() );
		return new ResponseEntity<>(HttpStatus.OK); 
	}
	
	@ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
	public ResponseEntity<Map<String, String>>
	exceptionHandler(){
		Map<String, String> errorMessage = new HashMap<String, String>();
		errorMessage.put("message", "Error while trying loggin in");
		return new ResponseEntity<Map<String,String>>(errorMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>>
	exceptionHandler( Exception ex ){
		System.out.println( ex.getMessage());
		Map<String, String> errorMessage = new HashMap<String, String>();
		errorMessage.put("message", ex.getMessage());
		return new ResponseEntity<Map<String,String>>(errorMessage, HttpStatus.BAD_REQUEST);
	}
}
