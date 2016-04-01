package de.app.model.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FormAuthentication {

	@NotNull @NotBlank
	String email;
	@NotNull @NotBlank
	String A;
	@NotNull @NotBlank
	String M1;
	@NotNull @NotBlank
	String spubkey;
	
	
	@JsonCreator
	public FormAuthentication(@JsonProperty("email") String email,
			@JsonProperty("A") String a, 
			@JsonProperty("M1") String m1,
			@JsonProperty("spubkey") String spubkey) {
		this.email = email;
		A = a;
		M1 = m1;
		this.spubkey = spubkey;
	}
	
	public String getA() {
		return A;
	}
	public void setA(String a) {
		A = a;
	}
	public String getM1() {
		return M1;
	}
	public void setM1(String m1) {
		M1 = m1;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSpubkey() {
		return spubkey;
	}
	public void setSpubkey(String spubkey) {
		this.spubkey = spubkey;
	}

	@Override
	public String toString() {
		return "FormAuthentication [email=" + email + ", A=" + A + ", M1=" + M1 + "]";
	}
}
