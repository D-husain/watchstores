package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.Order;
import com.example.demo.entity.User;

@Controller
public class UserControllers {
	
	@Autowired
	private UserDao udao;
	
	
	@PostMapping("/user/register")
	public ResponseEntity<Void> UserRegisters(@RequestBody User user) {
		udao.UserRegister(user);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("/user/data")
	public ResponseEntity<List<User>> getUserList() {
		return new ResponseEntity<List<User>>(udao.fechAllUser(), HttpStatus.OK);
	}
	
	
	
	@GetMapping("/user/orders/{userId}")
	public ResponseEntity<List<Order>> getUserOrders(@RequestBody User user) {
	    List<Order> userOrders = udao.viewOrders(user.getId());

	    if (userOrders.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } else {
	        return new ResponseEntity<>(userOrders, HttpStatus.OK);
	    }
	}
}
