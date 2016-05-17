package de.app.exceptions;

public class ResourceNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static String MESSAGE = "Resource Not Found.";
	
	
	public ResourceNotFoundException(String message){
		super(MESSAGE + message);
	}
	
}