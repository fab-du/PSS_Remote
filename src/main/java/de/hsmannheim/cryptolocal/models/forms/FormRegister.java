package de.hsmannheim.cryptolocal.models.forms;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

public class FormRegister {
	@NotNull
	String email;
	@NotNull
	String firstname;
	@NotNull
	String secondname;
	@NotNull
	String company;
	
	@Nullable
	String password;
	@Nullable
	String passphrase;
	
	@NotNull
	String verifier;
	@NotNull
	String salt;
	@NotNull
	String pubkey;
	@NotNull
	String prikey; 
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getSecondname() {
		return secondname;
	}
	public void setSecondname(String secondname) {
		this.secondname = secondname;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassphrase() {
		return passphrase;
	}
	public void setPassphrase(String passphrase) {
		this.passphrase = passphrase;
	}
	public String getVerifier() {
		return verifier;
	}
	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getPubkey() {
		return pubkey;
	}
	public void setPubkey(String pubkey) {
		this.pubkey = pubkey;
	}
	public String getPrikey() {
		return prikey;
	}
	public void setPrikey(String prikey) {
		this.prikey = prikey;
	}
	
	@Override
	public String toString() {
		return "FormRegister [email=" + email + ", firstname=" + firstname + ", secondname=" + secondname + ", company="
				+ company + ", password=" + password + ", passphrase=" + passphrase + ", verifier=" + verifier
				+ ", salt=" + salt + ", pubkey=" + pubkey + ", prikey=" + prikey + "]";
	}

}
