package com.uba.hbd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="User")
public class User {
	
	@Id
	@Column(name="LOGIN")
	private String login;
	
	@Column(name="STAFF_ID")
	private String staffId;
	
	@Column(name="PASSWORD")
	private String password;
	
	public User() {}
	
	public User(String login, String staffId, String password) {
		this.login = login;
		this.staffId = staffId;
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
