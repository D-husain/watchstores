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
@Table (name="product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String pname;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cid")
	private Category category;
	private String img1;
	private String img2;
	private String img3;
	private String img4;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "brandid")
	private ProductBrand brand;
	private String colore;
	private String description;
	private String specification;
	private String genericname;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "countruid")
	private Country country;
	private double price;
	private int qty;
	private String availability;
	
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Product(int id, String pname, Category category, String img1, String img2, String img3, String img4,
			ProductBrand brand, String colore, String description, String specification, String genericname, Country country,
			double price, int qty, String availability) {
		super();
		this.id = id;
		this.pname = pname;
		this.category = category;
		this.img1 = img1;
		this.img2 = img2;
		this.img3 = img3;
		this.img4 = img4;
		this.brand = brand;
		this.colore = colore;
		this.description = description;
		this.specification = specification;
		this.genericname = genericname;
		this.country = country;
		this.price = price;
		this.qty = qty;
		this.availability = availability;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", pname=" + pname + ", category=" + category + ", img1=" + img1 + ", img2=" + img2
				+ ", img3=" + img3 + ", img4=" + img4 + ", brand=" + brand + ", colore=" + colore + ", description="
				+ description + ", specification=" + specification + ", genericname=" + genericname + ", country="
				+ country + ", price=" + price + ", qty=" + qty + ", availability=" + availability + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getImg1() {
		return img1;
	}
	public void setImg1(String img1) {
		this.img1 = img1;
	}
	public String getImg2() {
		return img2;
	}
	public void setImg2(String img2) {
		this.img2 = img2;
	}
	public String getImg3() {
		return img3;
	}
	public void setImg3(String img3) {
		this.img3 = img3;
	}
	public String getImg4() {
		return img4;
	}
	public void setImg4(String img4) {
		this.img4 = img4;
	}
	public ProductBrand getBrand() {
		return brand;
	}
	public void setBrand(ProductBrand brand) {
		this.brand = brand;
	}
	public String getColore() {
		return colore;
	}
	public void setColore(String colore) {
		this.colore = colore;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public String getGenericname() {
		return genericname;
	}
	public void setGenericname(String genericname) {
		this.genericname = genericname;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getAvailability() {
		return availability;
	}
	public void setAvailability(String availability) {
		this.availability = availability;
	}
}