package de.hsmannheim.cryptolocal.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.OneToOne;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name="users")
public class User extends AbstEntity{

	/*
	 * user : 
	 * 		 - email
	 * 		 - company
	 */
	@NotBlank
	String firstname, secondname,  company; 

	@NotBlank
	@Email
	String email;


	public User(){}
	public User(String firstname, String secondname, String email, String company, boolean validated) {
		super();
		this.firstname = firstname;
		this.secondname = secondname;
		this.email = email;
		this.company = company;
		this.validated = validated;
	}


	/*
	 * user pairkeys
	 */
	@OneToOne
	@JsonIgnore
	KeyPair keypair;

	@OneToOne(cascade=CascadeType.ALL)
	@JsonIgnore
	 SrpCredential srp;

	/*
	 * user status
	 */
	boolean validated=false;

	@OneToMany(mappedBy="users", cascade = CascadeType.ALL)
	@JsonIgnore
	Set<UserGroup> usergroup = new HashSet<UserGroup>();
	
	@OneToMany(mappedBy="users", cascade = CascadeType.ALL)
	@JsonIgnore
	Set<Friendship> friends = new HashSet<Friendship>();


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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}


	public KeyPair getKeypair() {
		return keypair;
	}

	public void setKeypair(KeyPair keypair) {
		this.keypair = keypair;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	public Set<UserGroup> getUsergroup() {
		return usergroup;
	}

	public void setUsergroup(Set<UserGroup> usergroup) {
		this.usergroup = usergroup;
	}

	

	public SrpCredential getSrp() {
		return srp;
	}
	public void setSrp(SrpCredential  srp) {
		this.srp = srp;
	}
	
	

	public Set<Friendship> getFriends() {
		return friends;
	}
	public void setFriends(Set<Friendship> friends) {
		this.friends = friends;
	}
	
	@Override
	public String toString() {
		return "User [firstname=" + firstname + ", secondname=" + secondname + ", company=" + company + ", email="
				+ email + ", keypair=" + keypair + ", validated=" + validated + "]";
	}

}
