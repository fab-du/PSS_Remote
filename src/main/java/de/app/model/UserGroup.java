package de.app.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Table(name="usergroup")
@IdClass(UserGroupId.class)
public class UserGroup implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3937334589055454828L;

	@Id
	private long useringroupId;

	@Id
	private long groupId;

	/*
	 * User copy of the group sym. key encrypt with user pub. key
	 */
	@OneToOne
	KeySym keysym;

	boolean isGroupLead;

	@ManyToOne(cascade= CascadeType.ALL)
	@PrimaryKeyJoinColumn(name="USERINGROUPID", referencedColumnName="ID" )
	User users = new User();

	@ManyToOne(cascade= CascadeType.ALL)
	@PrimaryKeyJoinColumn(name="GROUPID", referencedColumnName="ID")
	Group groups = new Group();

	public long getUseringroupId() {
		return useringroupId;
	}

	public void setUseringroupId(long useringroupId) {
		this.useringroupId = useringroupId;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public KeySym getKeysym() {
		return keysym;
	}

	public void setKeysym(KeySym keysym) {
		this.keysym = keysym;
	}

	public boolean isGroupLead() {
		return isGroupLead;
	}

	public void setGroupLead(boolean isGroupLead) {
		this.isGroupLead = isGroupLead;
	}

	public User getUsers() {
		return users;
	}

	public void setUsers(User users) {
		this.users = users;
	}

	public Group getGroups() {
		return groups;
	}

	public void setGroups(Group groups) {
		this.groups = groups;
	}
}
