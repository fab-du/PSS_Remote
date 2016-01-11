package de.hsmannheim.cryptolocal.models.forms;


import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import org.hibernate.tool.hbm2ddl.ForeignKeyMetadata;
import org.hibernate.validator.constraints.NotEmpty;

import de.hsmannheim.cryptolocal.models.Group;

public class FormNewUserToGroup {


	@NotNull @NotEmpty
	String newuseremail;

	@NotNull @NotEmpty
	String passphrase; 


	Long groupid;
	Long gvid;



	public FormNewUserToGroup(){}

	public String getNewuseremail() {
		return newuseremail;
	}

	public void setNewuseremail(String newuseremail) {
		this.newuseremail = newuseremail;
	}

	public String getPassphrase() {
		return passphrase;
	}

	public void setPassphrase(String passphrase) {
		this.passphrase = passphrase;
	}

	public Long getGroupid() {
		return groupid;
	}

	public void setGroupid(Long groupid) {
		this.groupid = groupid;
	}

	public Long getGvid() {
		return gvid;
	}

	public void setGvid(Long gvid) {
		this.gvid = gvid;
	}

	


	public boolean validate(  FormNewUserToGroup obj ){
		Set<ConstraintViolation<FormNewUserToGroup>> constraintViolations = this.validate(obj, true);
		return constraintViolations.isEmpty(); 
	}

	public Set<ConstraintViolation<FormNewUserToGroup>> validate( FormNewUserToGroup obj, boolean verbose ){
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<FormNewUserToGroup>> constraintViolations = validator.validate( obj );
		return constraintViolations;
	}
	


}
