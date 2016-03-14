package de.app.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;


@Entity
@Table(name="friend")
public class Friendship extends AbstEntity{

	Long friendId;
	
	@ManyToOne
	User users;
	
	@Type(type="text")
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

	@Override
	public String toString() {
		return "Friendship [friendId=" + friendId + ", users=" + users + ", signature=" + signature + "]";
	}
}
