package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.UserDao;
import com.example.demo.dto.WishlistDTO;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.entity.Wishlist;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.WishlistRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class WishlistController {

	@Autowired
	private UserDao udao;
	@Autowired
	private WishlistRepository wishlistrepo;
	@Autowired
	private ProductRepository productrepo;

	@PostMapping("/user/addToWishlist")
	public ResponseEntity<String> addToWishlist(@RequestParam int pid, HttpSession session) {
		User user = (User) session.getAttribute("user");

		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please log in to add items to the wishlist.");
		} else {
			try {
				Product product = productrepo.findById(pid).orElse(null);

				if (product == null) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
				}

				Wishlist existingWishlistItem = wishlistrepo.findByProductAndUser_id(product, user.getId());

				if (existingWishlistItem != null) {
					return ResponseEntity.status(HttpStatus.CONFLICT).body("Product is already in your wishlist.");
				} else {
					Wishlist newWishlistItem = new Wishlist();
					newWishlistItem.setUser(user);
					newWishlistItem.setProduct(product);
					wishlistrepo.save(newWishlistItem);
					return ResponseEntity.status(HttpStatus.OK).body("Product added successfully to the wishlist.");
				}
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Error adding product to wishlist.");
			}
		}
	}

	@GetMapping("/wishlist/data")
	public ResponseEntity<List<WishlistDTO>> getWishlistList(HttpSession session) {
		User user = (User) session.getAttribute("user");

		if (user == null) {
			return ResponseEntity.badRequest().build();
		}

		List<Wishlist> wishlist = udao.viewUserwishlist(user);

		if (wishlist == null || wishlist.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		List<WishlistDTO> wishlistDTO = wishlist.stream().map(wishlistItem -> {
			WishlistDTO wdto = new WishlistDTO();
			wdto.setId(wishlistItem.getId());

			Product product = wishlistItem.getProduct();
			if (product != null) {
				wdto.setPid(product.getId());
				wdto.setImg(product.getImg1());
				wdto.setPname(product.getPname());
				wdto.setPrice(product.getPrice());
				wdto.setStock(product.getAvailability());
			}

			wdto.setUser(user.getId()); // Set user ID for each wishlist item

			return wdto;
		}).collect(Collectors.toList());

		return ResponseEntity.ok(wishlistDTO); // Return wishlist data with OK status
	}

	@GetMapping("user/wishlist/count")
	public ResponseEntity<Integer> getuserWishlistsize(HttpSession session) {
		User user = (User) session.getAttribute("user");

		if (user == null) {
			return ResponseEntity.badRequest().build();
		}

		List<Wishlist> wishlistItems = udao.viewUserwishlist(user);

		return ResponseEntity.ok(wishlistItems.size());
	}

	@DeleteMapping("/wishlist/delete/{id}")
	public ResponseEntity<String> deleteWishlist(@PathVariable Integer id) {
		udao.DeleteWishlist(id);
		return ResponseEntity.status(HttpStatus.OK).body("Product delete successfully.");
	}
}
