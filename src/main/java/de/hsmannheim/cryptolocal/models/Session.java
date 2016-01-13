package de.hsmannheim.cryptolocal.models;

import javax.persistence.Entity;

import org.hibernate.annotations.Type;

@Entity
public class Session extends  AbstEntity {

	String email;
	@Type(type="text")
	String B;
	@Type(type="text")
	String xsrfToken;
	@Type(type="text")
	String clientPubkey;
	@Type(type="text")
	String serverPubkey;
	String headerAuthorization; 
	@Type(type="text")
	String wwwAuthenticate; 
	@Type(type="text")
	String salt;
	String hash_algorithm;  /* sha256 */
	String tokenType; /* Bearer */
	@Type(type="text")
	String accessToken;
	int expiresIn;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getB() {
		return B;
	}
	public void setB(String b) {
		B = b;
	}
	public String getXsrfToken() {
		return xsrfToken;
	}
	public void setXsrfToken(String xsrfToken) {
		this.xsrfToken = xsrfToken;
	}
	public String getClientPubkey() {
		return clientPubkey;
	}
	public void setClientPubkey(String clientPubkey) {
		this.clientPubkey = clientPubkey;
	}
	public String getServerPubkey() {
		return serverPubkey;
	}
	public void setServerPubkey(String serverPubkey) {
		this.serverPubkey = serverPubkey;
	}
	public String getHeaderAuthorization() {
		return headerAuthorization;
	}
	public void setHeaderAuthorization(String headerAuthorization) {
		this.headerAuthorization = headerAuthorization;
	}
	public String getWwwAuthenticate() {
		return wwwAuthenticate;
	}
	public void setWwwAuthenticate(String wwwAuthenticate) {
		this.wwwAuthenticate = wwwAuthenticate;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getHash_algorithm() {
		return hash_algorithm;
	}
	public void setHash_algorithm(String hash_algorithm) {
		this.hash_algorithm = hash_algorithm;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	
}
