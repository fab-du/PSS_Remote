package de.app.model;

import java.util.Map;
import java.util.HashMap;

import javax.persistence.Table;
import javax.persistence.Entity;


@Entity
@Table(name="roles")
public class Role extends AbstEntity{
	public static final Map<String, String>ROLES = new HashMap<String, String>();
	{
		ROLES.put("admin", "admin");
		ROLES.put("user", "user");
		ROLES.put("validated", "ja");
		ROLES.put("unvalidated", "ja");
	}

	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



}
