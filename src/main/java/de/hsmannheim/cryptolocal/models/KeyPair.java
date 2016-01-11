package de.hsmannheim.cryptolocal.models;

import javax.persistence.Entity;

import org.hibernate.annotations.Type;

@Entity
public class KeyPair extends AbstEntity{


	@Type(type="text")
	String pubkey;
	@Type(type="text")
	String prikey;

	@Type(type="text")
	String salt;

	public String getPubkey() {
		return pubkey;
	}
	public void setPubkey(String pubkey) {
		this.pubkey = pubkey;
	}
	public String getPrikey() {
		return prikey;
	}
	public void setPrikey(String prikey) {
		this.prikey = prikey;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Override
	public String toString() {
		return "KeyPair [pubkey=" + pubkey + ", prikey=" + prikey + ", salt=" + salt + "]";
	}
}
