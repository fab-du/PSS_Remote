package de.app.model;

import java.util.Date;

import javax.persistence.Entity;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

@Entity
public class KeySym extends AbstEntity{

	String symkey;


	public @Version Long version;
	public @LastModifiedDate Date date; 

	public String getSymkey() {
		return symkey;
	}

	public void setSymkey(String symkey) {
		this.symkey = symkey;
	}

	@Override
	public String toString() {
		return "KeySym [symkey=" + symkey + "]";
	}
}
