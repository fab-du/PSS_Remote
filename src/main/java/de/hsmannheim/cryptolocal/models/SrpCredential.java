package de.hsmannheim.cryptolocal.models;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Type;

@Entity
public class SrpCredential extends AbstEntity {

	
	public SrpCredential(String email, String salt, String verifier, String role) {
		super();
		this.email = email;
		this.salt = salt;
		this.verifier = verifier;
		this.role = role;
	}

	
	public SrpCredential() {
		super();
	}


	private String email;
	
	@Column(unique = true)
	@Type(type="text")
	private String salt;

	/**
	 * Note that we encrypt the verifier in the database to protect against
	 * leaked database backups being used to perform an offline dictionary
	 * attack
	 */
	@Type(type="text")
	private String verifier;

	@Column
	private String role = "ROLE_USER";

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getVerifier() {
		return verifier;
	}

	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}



	@Override
	public String toString() {
		return "SrpCredential [email=" + email + ", salt=" + salt + ", verifier=" + verifier + ", role=" + role + "]";
	}

	

}
