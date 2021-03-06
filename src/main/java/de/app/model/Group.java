package de.app.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="groups")
public class Group extends AbstEntity{

	@NotNull
	@NotEmpty
	String name;
	
	Long gvid;

	/*
	 * MitgliedSchaft
	 */
	@OneToMany(mappedBy="groups", cascade=CascadeType.ALL)
	@JsonIgnore
	Set<UserGroup> users = new HashSet<UserGroup>(); 

	@OneToMany(mappedBy="groups" ,cascade=CascadeType.ALL)
	@JsonIgnore
	Set<Document> documents = new HashSet<Document>();
	
	@OneToOne( cascade=CascadeType.ALL )
	KeySym groupkey;
	

	public void addUserToGroup( User user, KeySym keysym, boolean groupLead ){
		UserGroup usergroup = new UserGroup();
		usergroup.setUsers(user);
		usergroup.setGroups(this);
		usergroup.setUseringroupId(user.getId());
		usergroup.setGroupId(this.getId());
		usergroup.setGroupLead(groupLead);
		usergroup.setKeysym(keysym);
		System.out.println(this.users);
		this.users.add(usergroup);
		user.getUsergroup().add(usergroup);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<UserGroup> getUsers() {
		return users;
	}

	public void setUsers(Set<UserGroup> users) {
		this.users = users;
	}

	public Set<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}
	
	public Long getGvid() {
		return gvid;
	}

	public void setGvid(Long gvid) {
		this.gvid = gvid;
	}

	public KeySym getGroupkey() {
		return groupkey;
	}

	public void setGroupkey(KeySym groupkey) {
		this.groupkey = groupkey;
	}
}
