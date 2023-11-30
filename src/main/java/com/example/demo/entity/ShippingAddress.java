package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "shippingaddress")
public class ShippingAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uid")
	private User user;
	private String fname;
	private String lname;
	private String country;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String phone;
	private String email;
	
	public ShippingAddress() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ShippingAddress(int id, User user, String fname, String lname, String country, String address1,
			String address2, String city, String state, String phone, String email) {
		super();
		this.id = id;
		this.user = user;
		this.fname = fname;
		this.lname = lname;
		this.country = country;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.phone = phone;
		this.email = email;
	}
	@Override
	public String toString() {
		return "ShippingAddress [id=" + id + ", user=" + user + ", fname=" + fname + ", lname=" + lname + ", country="
				+ country + ", address1=" + address1 + ", address2=" + address2 + ", city=" + city + ", state=" + state
				+ ", phone=" + phone + ", email=" + email + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
