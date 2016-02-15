package de.helper;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;

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
	
//	public static String toJson( Map<String, String> map){
//		GsonBuilder gsonBuilder = new GsonBuilder();
//		Gson gson = gsonBuilder.create();
//		String jsonResult = gson.toJson(map);
//		return jsonResult;
//	}
	
//	public static Map<String, String> fromJSON( String jsonString ){
//		GsonBuilder gsonBuilder = new GsonBuilder();
//		Gson gson = gsonBuilder.create();
//		return gson.fromJson(jsonString, Map.class);
//	}
	
	
	public static PublicKey pubKeyFromString( String pubkey ){
		byte[] pubkeyBytes = Base64.getDecoder().decode( pubkey.getBytes());
		X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(pubkeyBytes);
		KeyFactory kf = null;
		PublicKey publickey = null; 
		try {
			kf = KeyFactory.getInstance("RSA");
			publickey = kf.generatePublic(X509publicKey);
		} catch (Exception e) {
			return null;
		} 

		return publickey;
	}
	
	
	public static PrivateKey priKeyFromString( String prikey ){
		byte prikeybytes[] = Base64.getDecoder().decode( prikey.getBytes() );
		KeyFactory keyFactory = null;
		PrivateKey privatekey = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
			KeySpec privateKeySpec = new PKCS8EncodedKeySpec(prikeybytes);
			privatekey = keyFactory.generatePrivate(privateKeySpec);
		} catch (Exception e) {
			return null;
		} 

		return privatekey;
	}
	
}

