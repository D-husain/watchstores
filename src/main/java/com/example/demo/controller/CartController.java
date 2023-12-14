package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dao.UserDao;
import com.example.demo.dto.CartDTO;
import com.example.demo.entity.Cart;
import com.example.demo.entity.User;
import com.example.demo.repository.CartRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {
	
	@Autowired private UserDao udao;
	@Autowired private CartRepository cartrepo;
	
	
	@PostMapping("user/addToCart")
	public ResponseEntity<String> addToCart(@RequestParam int pid, @RequestParam int qty, HttpSession session) {

		User user = (User) session.getAttribute("user");

		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please log in to add items to your cart.");
		} else {
			udao.addToCart(pid, user.getId(), qty);
			return ResponseEntity.status(HttpStatus.OK).body("Product added successfully.");
		}
	}

	@GetMapping("/cart/data")
	public ResponseEntity<List<CartDTO>> getCartList(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return ResponseEntity.badRequest().build();
		}

		Integer userId = user.getId();
		List<Cart> cart = udao.ShowUserCarts(userId);

		if (cart == null || cart.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		List<CartDTO> cartDTO = cart.stream().map(carts -> {
			CartDTO cdto = new CartDTO();
			cdto.setId(carts.getId());
			cdto.setPrice(carts.getPrice());
			cdto.setQty(carts.getQty());
			cdto.setTotal(carts.getTotal());

			if (carts.getProduct() != null) {
				cdto.setProductid(carts.getProduct().getId());
				cdto.setImg(carts.getProduct().getImg1());
				cdto.setPname(carts.getProduct().getPname());
			}

			if (carts.getUser() != null) {
				cdto.setUserid(carts.getUser().getId());
			}

			return cdto;
		}).collect(Collectors.toList());

		return ResponseEntity.ok(cartDTO); // Return cart data with OK status
	}

	@GetMapping("user/cart/total")
	public ResponseEntity<Double> getuserCarttotal(HttpSession session) {
		User user = (User) session.getAttribute("user");

		if (user == null) {
			return ResponseEntity.badRequest().build();
		}

		List<Cart> cartItems = udao.ShowUserCart(user);
		double subtotal = udao.calculateCartSubtotal(cartItems);

		if (subtotal == 0.0) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(subtotal);
	}
	
	@GetMapping("user/cart/count")
	public ResponseEntity<Integer> getuserCartsize(HttpSession session) {
		User user = (User) session.getAttribute("user");

		if (user == null) {
			return ResponseEntity.badRequest().build();
		}

		List<Cart> cartItems = udao.ShowUserCart(user);

		return ResponseEntity.ok(cartItems.size());
	}

	@GetMapping("/user/cart/subtotal")
	public ResponseEntity<Double> getUserCartSubtotal(HttpSession session) {
		User user = (User) session.getAttribute("user");

		if (user == null) {
			return ResponseEntity.badRequest().build();
		}

		List<Cart> cartItems = udao.ShowUserCart(user);
		double charge = 100.0;

		if (cartItems == null || cartItems.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		double subtotal = udao.calculateCartTotalWithShipping(cartItems, charge);

		return ResponseEntity.ok(subtotal); // Return the subtotal
	}

	@PostMapping("/user/updateCart")
	public ResponseEntity<String> updateCarts(@RequestParam(value = "id", required = true) Integer id, @RequestParam(value = "qty", required = true) int qty) {
		
		if (id == null) {
			return ResponseEntity.badRequest().body("'id' parameter is required.");
		}

		Cart cartItem = cartrepo.getById(id);

		if (cartItem != null) {
			udao.updateCart(id, qty);
			return ResponseEntity.ok("Product updated successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart item not found.");
		}
	}
	
	@DeleteMapping("/cart/delete/{id}")
	public ResponseEntity<String> deleteCart(@PathVariable Integer id) {
		udao.DeleteCart(id);
		return ResponseEntity.status(HttpStatus.OK).body("Product delete successfully.");
	}
	
	
}
