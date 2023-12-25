package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "user")
public class User {

	@Id
	@GeneratedValue (strategy =  GenerationType.IDENTITY)
	private Integer id;
	private String fname;
	private String lname;
	private String email;
	private String contact;
	private String address;
	private String city;
	private String state;
	private String country;
	private Integer zip;
	private String password;
	private int loginCount;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(Integer id, String fname, String lname, String email, String contact, String address, String city,
			String state, String country, Integer zip, String password, int loginCount) {
		super();
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.contact = contact;
		this.address = address;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zip = zip;
		this.password = password;
		this.loginCount = loginCount;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", fname=" + fname + ", lname=" + lname + ", email=" + email + ", contact=" + contact
				+ ", address=" + address + ", city=" + city + ", state=" + state + ", country=" + country + ", zip="
				+ zip + ", password=" + password + ", loginCount=" + loginCount + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Integer getZip() {
		return zip;
	}
	public void setZip(Integer zip) {
		this.zip = zip;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}
}
