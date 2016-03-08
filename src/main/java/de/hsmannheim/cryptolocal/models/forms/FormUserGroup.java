package de.hsmannheim.cryptolocal.models.forms;

import de.hsmannheim.cryptolocal.models.KeySym;

public class FormUserGroup {
	
	Long userId;
	KeySym keysym;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public KeySym getKeysym() {
		return keysym;
	}
	public void setKeysym(KeySym keysym) {
		this.keysym = keysym;
	}
}
