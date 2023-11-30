package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name="category")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String cname;
	private String cimg;
	
	
	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Category(int id, String cname, String cimg) {
		super();
		this.id = id;
		this.cname = cname;
		this.cimg = cimg;
	}
	@Override
	public String toString() {
		return "Category [id=" + id + ", cname=" + cname + ", cimg=" + cimg + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCimg() {
		return cimg;
	}
	public void setCimg(String cimg) {
		this.cimg = cimg;
	}
	
}
