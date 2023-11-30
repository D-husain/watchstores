package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "coupon")
public class Coupons {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String code;
	private String type;
	private Integer value;
	private Integer mvalue;
	
	public Coupons() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Coupons(Integer id, String code, String type, Integer value, Integer mvalue) {
		super();
		this.id = id;
		this.code = code;
		this.type = type;
		this.value = value;
		this.mvalue = mvalue;
	}
	@Override
	public String toString() {
		return "Coupons [id=" + id + ", code=" + code + ", type=" + type + ", value=" + value + ", mvalue=" + mvalue
				+ "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public Integer getMvalue() {
		return mvalue;
	}
	public void setMvalue(Integer mvalue) {
		this.mvalue = mvalue;
	}
}