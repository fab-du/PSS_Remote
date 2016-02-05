package de.hsmannheim.cryptolocal.models;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="friend")
public class Friendship extends AbstEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	Long friendId;
	
	@ManyToOne
	User users;
	
	String signature;

	public Long getFriendId() {
		return friendId;
	}

	public void setFriendId(Long friendId) {
		this.friendId = friendId;
	}

	public User getUsers() {
		return users;
	}

	public void setUsers(User users) {
		this.users = users;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
	
}
