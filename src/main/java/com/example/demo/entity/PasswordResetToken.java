package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "PasswordResetToken")
public class PasswordResetToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;

	private String token;
	private LocalDateTime expiryDate;
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}
	@Override
	public String toString() {
		return "PasswordResetToken [id=" + id + ", user=" + user + ", token=" + token + ", expiryDate=" + expiryDate
				+ "]";
	}
	public PasswordResetToken(int id, User user, String token, LocalDateTime expiryDate) {
		super();
		this.id = id;
		this.user = user;
		this.token = token;
		this.expiryDate = expiryDate;
	}
	public PasswordResetToken() {
		super();
		// TODO Auto-generated constructor stub
	}
	public boolean isExpired() {
		// TODO Auto-generated method stub
		return false;
	}
}
