package de.hsmannheim.cryptolocal.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

@Entity
public class Document extends AbstEntity {
	
	String name, path;
	public @Version Long version;
	public @LastModifiedDate Date date; 

	@ManyToOne(cascade= CascadeType.ALL)
	@PrimaryKeyJoinColumn(name="GROUPID", referencedColumnName="ID")
	Group groups = new Group();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Group getGroups() {
		return groups;
	}

	public void setGroups(Group groups) {
		this.groups = groups;
	}

	
	
}
