package de.hsmannheim.cryptolocal.models;

import javax.persistence.Entity;

import org.hibernate.annotations.Type;

@Entity
public class Session extends  AbstEntity {

	String email;
	
	@Type(type="text")
	String B;

	@Type(type="text")
	String xsrf_token;

	@Type(type="text")
	String client_pubkey;

	@Type(type="text")
	String server_pubkey;

	String header_authorization; 

	@Type(type="text")
	String www_authenticate; 

	@Type(type="text")
	String salt;

	String hash_algorithm;  /* sha256 */
	String token_type; /* Bearer */

	@Type(type="text")
	String access_token;
	int expires_in;

	public String getB() {
		return B;
	}
	public void setB(String b) {
		B = b;
	}
	public String getXsrf_token() {
		return xsrf_token;
	}
	public void setXsrf_token(String xsrf_token) {
		this.xsrf_token = xsrf_token;
	}
	public String getClient_pubkey() {
		return client_pubkey;
	}
	public void setClient_pubkey(String client_pubkey) {
		this.client_pubkey = client_pubkey;
	}
	public String getServer_pubkey() {
		return server_pubkey;
	}
	public void setServer_pubkey(String server_pubkey) {
		this.server_pubkey = server_pubkey;
	}

	public String getHeader_authorization() {
		return header_authorization;
	}
	public void setHeader_authorization(String header_authorization) {
		this.header_authorization = header_authorization;
	}
	public String getWww_authenticate() {
		return www_authenticate;
	}
	public void setWww_authenticate(String www_authenticate) {
		this.www_authenticate = www_authenticate;
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
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "Session [email=" + email + ", B=" + B + ", xsrf_token=" + xsrf_token + ", client_pubkey="
				+ client_pubkey + ", server_pubkey=" + server_pubkey + ", header_authorization=" + header_authorization
				+ ", www_authenticate=" + www_authenticate + ", salt=" + salt + ", hash_algorithm=" + hash_algorithm
				+ ", token_type=" + token_type + ", access_token=" + access_token + ", expires_in=" + expires_in + "]";
	}



	
}
