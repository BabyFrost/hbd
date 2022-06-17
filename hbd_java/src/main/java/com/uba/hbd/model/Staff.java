package com.uba.hbd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="STAFF")
public class Staff {
	
	@Id
	@Column(name="ID")
	private String id;
	
	@Column(name="USERNAME")
	private String username;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="LAST_NAME")
	private String lastname;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="PHOTO")
	private String photoUrl;
	
	public Staff () {
		
	}
	
	public Staff ( String id, String name, String email, String photoUrl ) {
		
		this.id = id;
		this.name = name;
		this.email = email;
		this.photoUrl = photoUrl;
		
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
}
