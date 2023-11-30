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
@Table(name="Orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int orderId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uid")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sId")
	private ShippingAddress shippingAddress;
	
	private String orderdate;
	private double total;
	private double amount;
	private int status;
	private String cancelorder;
	private int charge;
	private double discount;
	
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Order(int id, int orderId, User user, ShippingAddress shippingAddress, String orderdate, double total,
			double amount, int status, String cancelorder, int charge, double discount) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.user = user;
		this.shippingAddress = shippingAddress;
		this.orderdate = orderdate;
		this.total = total;
		this.amount = amount;
		this.status = status;
		this.cancelorder = cancelorder;
		this.charge = charge;
		this.discount = discount;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", orderId=" + orderId + ", user=" + user + ", shippingAddress=" + shippingAddress
				+ ", orderdate=" + orderdate + ", total=" + total + ", amount=" + amount + ", status=" + status
				+ ", cancelorder=" + cancelorder + ", charge=" + charge + ", discount=" + discount + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public ShippingAddress getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(ShippingAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public String getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCancelorder() {
		return cancelorder;
	}
	public void setCancelorder(String cancelorder) {
		this.cancelorder = cancelorder;
	}
	public int getCharge() {
		return charge;
	}
	public void setCharge(int charge) {
		this.charge = charge;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
	
}
