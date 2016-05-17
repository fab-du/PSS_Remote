package de.app.exceptions;

public class AuthenticationError extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static String MESSAGE = "Authentication Error.";

	public AuthenticationError(String message) {
		super(MESSAGE + message);
	}
	
}
