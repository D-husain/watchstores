package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dao.AdminDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.SubscribeDTO;
import com.example.demo.entity.Admin;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Category;
import com.example.demo.entity.Contact;
import com.example.demo.entity.Coupons;
import com.example.demo.entity.Order;
import com.example.demo.entity.Product;
import com.example.demo.entity.Slider;
import com.example.demo.entity.Subscribe;
import com.example.demo.entity.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

	@Autowired
	private AdminDao adao;
	@Autowired
	private UserDao udao;
	
	@GetMapping("/admin")
	public String admin(HttpSession session, Model model) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin == null) {
			return "redirect:/adminlogins";
		} else {
			List<Category> cat = adao.ShowCategory();
			model.addAttribute("category", cat);
			List<Product> productList = adao.ShowProduct();
			model.addAttribute("products", productList);
		}
		return "admin/index";
	}

	@GetMapping("/adminlogins")
	public String adminlogin() {
		return "admin/login";
	}

	@PostMapping("/login_admin")
	public String fechAdmimnData(@RequestParam("email") String admin_email, @RequestParam("password") String password,
			RedirectAttributes redirAttrs,HttpSession session) {
		List<Admin> list = adao.fechAllUser();

		String page_move = "redirect:/adminlogins";

		for (Admin admin : list) {
			if (admin_email.equals(admin.getEmail()) && password.equals(admin.getPassword())) {
				session.setAttribute("admin", admin);
				page_move = "redirect:/admin";
			} else if (!admin_email.equals(admin.getEmail()) && !password.equals(admin.getPassword())) {
				redirAttrs.addFlashAttribute("error", "Login failed. Invalid username or password.");
				return page_move;
			}
		}
		return page_move;
	}
	
	@PostMapping("/admin/login")
	@ResponseBody
	public ResponseEntity<String> userlogins(@RequestParam("email") String admin_email, @RequestParam("password") String password,
	        HttpSession session) {
	    List<Admin> adminList = adao.fechAllUser();

	    boolean loggedIn = false;

	    for (Admin admin : adminList) {
	        if (admin_email.equals(admin.getEmail()) && password.equals(admin.getPassword())) {
	            session.setAttribute("admin", admin);
	            loggedIn = true;
	            break;
	        }
	    }

	    if (loggedIn) {
	    	  return ResponseEntity.ok().build();
	    } else {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed. Invalid username or password.");
	    }
	}

	@GetMapping("/admin-logout")
	public String adminlogout(HttpSession s) {
		s.invalidate();
		return "redirect:/adminlogins";
	}
	
	@GetMapping("/editprofile")
	public String admineditprofile(HttpSession session) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin == null) {
			return "redirect:/adminlogins";
		}
		return "admin/editprofile";

	}

	@GetMapping("subscribers")
	public String adminsubscribers() {
		return "admin/subscribers";
	}
	
	//------------------------------------- Subscribe user Data Api -----------------------------------------------------
	
	@GetMapping("/user/subscribe")
	public ResponseEntity<List<SubscribeDTO>> getSubscribeList() {
		  List<Subscribe> SubscribesList = udao.ShowSubscribe();
		  List<SubscribeDTO> Subscribes = new ArrayList<>();
		  
		  for (Subscribe subscribe : SubscribesList) {
			  SubscribeDTO subscribeDTO = new SubscribeDTO();
			  subscribeDTO.setId(subscribe.getId());
		      subscribeDTO.setFname(subscribe.getUser().getFname());
		      subscribeDTO.setLname(subscribe.getUser().getLname());
		      subscribeDTO.setEmail(subscribe.getEmail());
			  Subscribes.add(subscribeDTO);
		    }
		  return new ResponseEntity<>(Subscribes, HttpStatus.OK);
	}
	
	@DeleteMapping("/subscribe/delete/{id}")
	public ResponseEntity<Void> deletesubscribe(@PathVariable Integer id) {
		udao.deleteSubscribe(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	
	
	
	
	
	
	
	@ModelAttribute("Usersize")
	public int Usersize(HttpSession session) {

		List<User> user = udao.fechAllUser();
		if (user != null) {
			return user.size();
		} else {
			return 0;
		}
	}
	
	@ModelAttribute("Contactsize")
	public int Contactsize(HttpSession session) {

		List<Contact> contact = udao.ShowContact();
		if (contact != null) {
			return contact.size();
		} else {
			return 0;
		}
	}
	
	@ModelAttribute("Subscribesize")
	public int Subscribesize(HttpSession session) {

		List<Subscribe> subscribes = udao.ShowSubscribe();
		if (subscribes != null) {
			return subscribes.size();
		} else {
			return 0;
		}
	}
	
	@ModelAttribute("Categorysize")
	public int Categorysize(HttpSession session) {

		List<Category> categories= adao.ShowCategory();
		if (categories != null) {
			return categories.size();
		} else {
			return 0;
		}
	}
	
	@ModelAttribute("Productsize")
	public int Productsize(HttpSession session) {

		List<Product> products= adao.ShowProduct();
		if (products != null) {
			return products.size();
		} else {
			return 0;
		}
	}
	
	@ModelAttribute("Couponsize")
	public int Couponsize(HttpSession session) {

		List<Coupons> coupons= adao.ShowCoupon();
		if (coupons != null) {
			return coupons.size();
		} else {
			return 0;
		}
	}
	
	@ModelAttribute("Slidersize")
	public int Slidersize(HttpSession session) {

		List<Slider> sliders= adao.ShowSlider();
		if (sliders != null) {
			return sliders.size();
		} else {
			return 0;
		}
	}
	
	@ModelAttribute("Ordersize")
	public int Ordersize(HttpSession session) {

		List<Order> orders= udao.ShowUserOrders();
		if (orders != null) {
			return orders.size();
		} else {
			return 0;
		}
	}
	
	@ModelAttribute("Pendingsize")
	public int Pendingsize(HttpSession session) {

		List<Order> orders= udao.getPendingOrders();
		if (orders != null) {
			return orders.size();
		} else {
			return 0;
		}
	}

	@ModelAttribute("Activesize")
	public int Activesize(HttpSession session) {

		List<Order> orders= udao.getActiveOrders();
		if (orders != null) {
			return orders.size();
		} else {
			return 0;
		}
	}
	
	@ModelAttribute("Shippingsize")
	public int Shippingsize(HttpSession session) {

		List<Order> orders= udao.getShippingOrders();
		if (orders != null) {
			return orders.size();
		} else {
			return 0;
		}
	}
	
	@ModelAttribute("Deliveredsize")
	public int Deliveredsize(HttpSession session) {

		List<Order> orders= udao.getDeliveredOrders();
		if (orders != null) {
			return orders.size();
		} else {
			return 0;
		}
	}
	
	@ModelAttribute("Cancelledsize")
	public int Cancelledsize(HttpSession session) {

		List<Order> orders= udao.getCancelledOrders();
		if (orders != null) {
			return orders.size();
		} else {
			return 0;
		}
	}
}
