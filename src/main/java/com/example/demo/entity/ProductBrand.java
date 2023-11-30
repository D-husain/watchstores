package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="productbrand")
public class ProductBrand {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String brand;
	
	public ProductBrand() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductBrand(int id, String brand) {
		super();
		this.id = id;
		this.brand = brand;
	}
	@Override
	public String toString() {
		return "ProductBrand [id=" + id + ", brand=" + brand + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
}
