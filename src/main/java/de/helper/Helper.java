package de.helper;

import java.util.Base64;

public class Helper {
	
	public static String encode( byte[] bytes){
		return Base64.getEncoder().encodeToString(bytes);
	}
	
	public static byte[] decode( String str ){
		return Base64.getDecoder().decode(str);
	}
	
	public static byte[] decode( byte[] str ){
		return Base64.getDecoder().decode(str);
	}
	
}

