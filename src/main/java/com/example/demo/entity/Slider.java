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
@Table(name ="slider")
public class Slider {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String tital;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoryid")
	private Category category;
	private String image;
	
	public Slider() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Slider [id=" + id + ", name=" + name + ", tital=" + tital + ", category=" + category + ", image="
				+ image + "]";
	}
	public Slider(int id, String name, String tital, Category category, String image) {
		super();
		this.id = id;
		this.name = name;
		this.tital = tital;
		this.category = category;
		this.image = image;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTital() {
		return tital;
	}
	public void setTital(String tital) {
		this.tital = tital;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
}
