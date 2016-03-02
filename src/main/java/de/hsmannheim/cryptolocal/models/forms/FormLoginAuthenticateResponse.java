package de.hsmannheim.cryptolocal.models.forms;

public class FormLoginAuthenticateResponse {
	
	String currentUserId;
	String email;
	String evidence;
	String currentUserPublicKey;
	
    Long currenUserGroupId;
	
	public FormLoginAuthenticateResponse() {
		super();
	}
	
	public Long getCurrentUserGroupId(){
		return currenUserGroupId;
	}
	public void setCurrentUserGroupId( Long currentUserGroupId ){
		this.currenUserGroupId = currentUserGroupId;
	}
	public String getCurrentUserId() {
		return currentUserId;
	}
	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEvidence() {
		return evidence;
	}
	public void setEvidence(String evidence) {
		this.evidence = evidence;
	}
	
	public String getCurrentUserPublicKey() {
		return currentUserPublicKey;
	}
	public void setCurrentUserPublicKey(String currentUserPublicKey) {
		this.currentUserPublicKey = currentUserPublicKey;
	}
}
