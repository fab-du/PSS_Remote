package de.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.helper.Helper;

public class Hash {
	
	public String hash( byte[] obj ){
		MessageDigest md ;
		
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(obj);
			byte[] digest = md.digest();
			return Helper.encode(digest);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}

	}

	public boolean checkHash(byte[] obj , String digesta){
		byte[] _digesta = Helper.decode(digesta);
		byte[] digestb = Helper.decode(this.hash(obj));
		return MessageDigest.isEqual(_digesta, digestb);
	}
	
}
