package de.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value=HttpStatus.NOT_FOUND, reason="something wrong with jwt-token" )
public class JwtException extends io.jsonwebtoken.JwtException{

	private static final long serialVersionUID = 7228642793192518153L;

	public JwtException( String message ){
		super( message );
	}
}

