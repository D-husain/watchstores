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
@Table(name="wishlist")
public class Wishlist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uid")
	private User user;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pid")
	private Product product;
	private double price;
	
	public Wishlist() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Wishlist(int id, User user, Product product, double price) {
		super();
		this.id = id;
		this.user = user;
		this.product = product;
		this.price = price;
	}
	@Override
	public String toString() {
		return "Wishlist [id=" + id + ", user=" + user + ", product=" + product + ", price=" + price + "]";
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
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
}
