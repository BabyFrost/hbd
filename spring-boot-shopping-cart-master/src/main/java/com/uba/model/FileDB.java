package com.uba.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "files")
public class FileDB {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "file_id")
  private Long id;

  private String name;
  
  private String solid;
  
  public String getSolid() {
	return solid;
}

public void setSolid(String solid) {
	this.solid = solid;
}

@DateTimeFormat(pattern = "dd/MM/yyyy")
  private Date birtdate;
  
  private String firstname;
  private String description;
  private double price;
  private int quantity;
  private String staffid;
  private String lastname;
  private String filename;
  public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public double getPrice() {
	return price;
}

public void setPrice(double price) {
	this.price = price;
}

public int getQuantity() {
	return quantity;
}

public void setQuantity(int quantity) {
	this.quantity = quantity;
}

public String getFilename() {
	return filename;
}

public void setFilename(String filename) {
	this.filename = filename;
}

private String accountnumber;
  public String getAccountnumber() {
	return accountnumber;
}

public void setAccountnumber(String accountnumber) {
	this.accountnumber = accountnumber;
}

private String email;
  



public Date getBirtdate() {
	return birtdate;
}

public void setBirtdate(Date birtdate) {
	this.birtdate = birtdate;
}

public String getFirstname() {
	return firstname;
}

public void setFirstname(String firstname) {
	this.firstname = firstname;
}

public String getStaffid() {
	return staffid;
}

public void setStaffid(String staffid) {
	this.staffid = staffid;
}

public String getLastname() {
	return lastname;
}

public void setLastname(String lastname) {
	this.lastname = lastname;
}



public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

private String type;

  @Lob
  private byte[] data;

  public FileDB() {
  }

  public FileDB(String name, String type, byte[] data) {
    this.name = name;
    this.type = type;
    this.data = data;
  }

  public Long getId() {
    return id;
  }
  
  

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }


}
