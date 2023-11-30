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
@Table(name="cities")
public class City {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String city;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "state_id")
	private States states;
	
	public City() {
		super();
		// TODO Auto-generated constructor stub
	}
	public City(int id, String city, States states) {
		super();
		this.id = id;
		this.city = city;
		this.states = states;
	}
	@Override
	public String toString() {
		return "City [id=" + id + ", city=" + city + ", states=" + states + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public States getStates() {
		return states;
	}
	public void setStates(States states) {
		this.states = states;
	}
}
