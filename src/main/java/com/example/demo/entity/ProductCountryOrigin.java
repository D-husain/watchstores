package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "productcountryorigin")
public class ProductCountryOrigin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String countryname;
	
	public ProductCountryOrigin() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductCountryOrigin(int id, String countryname) {
		super();
		this.id = id;
		this.countryname = countryname;
	}
	@Override
	public String toString() {
		return "ProductCountryOrigin [id=" + id + ", countryname=" + countryname + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCountryname() {
		return countryname;
	}
	public void setCountryname(String countryname) {
		this.countryname = countryname;
	}
}
