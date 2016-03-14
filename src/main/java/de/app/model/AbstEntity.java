package de.app.model;

import javax.persistence.Id;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

@MappedSuperclass
public class AbstEntity extends  AbstSecureModel{

	@Id 
	@GeneratedValue
	Long id;

	public void setId( Long id ){
		this.id = id;
	}

	public Long getId(){
		return this.id;
	}

	@Column(name="created_at")
	Date createdAt; @PrePersist void createdAt( ){ this.createdAt = new Date(); }

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode(); 
	}

	@Override
	public boolean equals(Object obj) {

		if( this == obj ){
			return true;
		}

		if( this.id == null || obj == null || !(this.getClass().equals(obj.getClass()))){
			return false;
		}

		AbstEntity that = ( AbstEntity ) obj;

		return this.id.equals(that.getId());
	}

	public boolean validate( AbstEntity entity ){
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<AbstEntity>> constraintViolations = validator.validate(entity);

		return ( constraintViolations.size() >= 1);
	}

	
}
