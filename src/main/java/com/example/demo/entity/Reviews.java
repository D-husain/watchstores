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
@Table(name = "reviews")
public class Reviews {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String reviewtital;
	private String review;
	private String reviewdate;
	private int rating;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ProductId")
	private Product product;
	
	public Reviews() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Reviews(int id, String reviewtital, String review, String reviewdate, int rating, User user,
			Product product) {
		super();
		this.id = id;
		this.reviewtital = reviewtital;
		this.review = review;
		this.reviewdate = reviewdate;
		this.rating = rating;
		this.user = user;
		this.product = product;
	}
	@Override
	public String toString() {
		return "Reviews [id=" + id + ", reviewtital=" + reviewtital + ", review=" + review + ", reviewdate="
				+ reviewdate + ", rating=" + rating + ", user=" + user + ", product=" + product + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReviewtital() {
		return reviewtital;
	}
	public void setReviewtital(String reviewtital) {
		this.reviewtital = reviewtital;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public String getReviewdate() {
		return reviewdate;
	}
	public void setReviewdate(String reviewdate) {
		this.reviewdate = reviewdate;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
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
}
