package de.app.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Document extends AbstEntity {
	
	String name, path;
	public @Version Long version;
	public @LastModifiedDate Date date; 

	@ManyToOne(cascade= CascadeType.ALL)
	@JsonIgnore
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

	@Override
	public String toString() {
		return "Document [name=" + name + ", path=" + path + ", version=" + version + ", date=" + date + ", groups="
				+ groups + "]";
	}

	
	
}
