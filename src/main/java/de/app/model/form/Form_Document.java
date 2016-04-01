package de.app.model.form;


import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class Form_Document {

	@NotNull @NotEmpty
	Long currentUserId;
	@NotNull @NotEmpty
	Long currentGroupId;

	@NotNull @NotEmpty
	String documentName;

	@NotNull @NotEmpty
	String currentGroupName;

	public Long getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(Long currentUserId) {
		this.currentUserId = currentUserId;
	}

	public Long getCurrentGroupId() {
		return currentGroupId;
	}

	public void setCurrentGroupId(Long currentGroupId) {
		this.currentGroupId = currentGroupId;
	}

	public String getDocumentName() {
		return documentName; 
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getCurrentGroupName() {
		return currentGroupName;
	}

	public void setCurrentGroupName(String currentGroupName) {
		this.currentGroupName = currentGroupName;
	}

	boolean validate( Form_Document doc ){
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Form_Document>> constraintViolations = validator.validate( doc );
		return constraintViolations.isEmpty();
	}


}
