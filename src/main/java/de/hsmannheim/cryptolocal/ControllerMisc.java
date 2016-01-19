package de.hsmannheim.cryptolocal;

import java.util.LinkedHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE )
public class ControllerMisc {

	
	@RequestMapping( value="/auth_error", method = RequestMethod.GET )
	public ResponseEntity<LinkedHashMap<String, String>> 
	unauthorized(){
		LinkedHashMap<String, String> response = new LinkedHashMap<>();
		response.put("error", "not authenticate");
		System.out.println("default error handler");
		return new ResponseEntity<>( response, HttpStatus.UNAUTHORIZED ); 
	}
	
}
