package com.uba.hbd.dto;

import org.springframework.web.multipart.MultipartFile;

import com.uba.hbd.model.Staff;

public class StaffDTO {
	
	private String id;
	private String username;
	private String name;
	private String lastname;
	private String email;
	private String photoUrl;
	private MultipartFile photo;
	
	public StaffDTO() { }
	
	public StaffDTO( Staff staff ) {
		this.id = staff.getId();
		this.username = staff.getUsername();
		this.name = staff.getName();
		this.lastname = staff.getLastname();
		this.email = staff.getEmail();
		this.photoUrl = staff.getPhotoUrl();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public MultipartFile getPhoto() {
		return photo;
	}
	
	public void setPhoto(MultipartFile photo) {
		this.photo = photo;
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

	public String toString() {
//		return "ID: "+this.id+" Name: "+this.name+" Email: "+this.email+" File: "+photo.getName();
		return "ID: "+this.id+" Name: "+this.name+" Email: "+this.email;
		
	}
}
