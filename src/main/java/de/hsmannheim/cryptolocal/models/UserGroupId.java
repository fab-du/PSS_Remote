package de.hsmannheim.cryptolocal.models;

import java.io.Serializable;

public class UserGroupId implements Serializable{

	private long useringroupId;
	private long groupId;

	

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

	@Override
	public int hashCode() {
		return (int) (useringroupId + groupId);
	}
	@Override
	public boolean equals(Object object) {
	    if (object instanceof UserGroupId) {
	        UserGroupId otherId = ( UserGroupId) object;
	        return (otherId.useringroupId == this.useringroupId ) && (otherId.groupId == this.groupId);
	      }
	      return false;
	}

	
}
