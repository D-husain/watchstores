package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name="country")
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String countryname;
	private String sortname;
	private String img;
	
	public Country() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Country(int id, String countryname, String sortname, String img) {
		super();
		this.id = id;
		this.countryname = countryname;
		this.sortname = sortname;
		this.img = img;
	}
	@Override
	public String toString() {
		return "Country [id=" + id + ", countryname=" + countryname + ", sortname=" + sortname + ", img=" + img + "]";
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
	public String getSortname() {
		return sortname;
	}
	public void setSortname(String sortname) {
		this.sortname = sortname;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
}
