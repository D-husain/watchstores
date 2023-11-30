package com.example.demo.controller;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.example.demo.dao.AdminDao;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Category;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetails;
import com.example.demo.entity.Product;
import com.example.demo.entity.ShippingAddress;
import com.example.demo.entity.User;
import com.example.demo.entity.Wishlist;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WishlistRepository;
import com.example.demo.service.EmailService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

	@Autowired
	private UserDao udao;
	@Autowired
	private AdminDao adao;
	@Autowired
	private UserRepository userrepo;
	@Autowired
	private ProductRepository productrepo;
	@Autowired
	private CartRepository cartrepo;
	@Autowired
	private WishlistRepository wishlistrepo;
	@Autowired
	private EmailService emailService;

	@GetMapping("/")
	public String index(Model model, HttpSession session) {
		model.addAttribute("category", adao.ShowCategory());
		model.addAttribute("product", adao.ShowProduct());
		model.addAttribute("slider", adao.ShowSlider());
		return "index";
	}

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("country", adao.ShowCountry());
		model.addAttribute("city", udao.showCity());
		model.addAttribute("state", udao.ShowState());

		return "register";
	}

	@PostMapping("/register_user")
	public String register_user(@ModelAttribute("user") User user, @RequestParam("email") String email,
			HttpSession session,RedirectAttributes redirAttrs) {

		User findemail = userrepo.findByEmail(email);

		if (findemail != null) {
			redirAttrs.addFlashAttribute("info", "Email id already exists");
			return "redirect:/register";
		} else {
			boolean t = udao.UserRegister(user);

			if (t == true) {
				redirAttrs.addFlashAttribute("success", "Register successfully");
			} else {
				redirAttrs.addFlashAttribute("error", "Register not successfully");
			}
		}
		return "redirect:/logins";
	}

	@GetMapping("/users")
	public String Users(Model model) {
		List<User> users=udao.fechAllUser();
		model.addAttribute("users", users);
		
		 Map<Integer, List<Order>> orderMap = new HashMap<>();
		    for (User user : users) {
		        List<Order> orderDetails = udao.viewOrders(user.getId());
		        orderMap.put(user.getId(), orderDetails);
		        System.out.println("order size:-"+orderMap.size());
		    }
		    model.addAttribute("orderMap", orderMap);
		    
		return "admin/users";
	}
	
	
	@GetMapping("/logins")
	public String login() {
		return "login";
	}

	@PostMapping("/login_user")
	public String userlogin(@RequestParam("name") String user_name, @RequestParam("password") String password,
			HttpServletRequest request, Model model, HttpSession s, RedirectAttributes redirAttrs) {
		List<User> list = udao.fechAllUser();

		String page_move = "redirect:/logins";
		boolean loggedIn = false;

		for (User user : list) {
			if (user_name.equals(user.getFname()) && password.equals(user.getPassword())) {
				s.setAttribute("user", user);
				page_move = "redirect:/account";
				s.setAttribute("user", user);
				page_move = "redirect:/account";
				loggedIn = true;
				break;
			}
		}

		if (!loggedIn) {
			redirAttrs.addFlashAttribute("error", "Login failed. Invalid username or password.");
		}
		return page_move;
	}
	
	
	@PostMapping("/api/login_users")
	@ResponseBody
	public ResponseEntity<String> userlogins(@RequestParam("name") String user_name, @RequestParam("password") String password,
	        HttpSession session) {
	    List<User> userList = udao.fechAllUser();

	    boolean loggedIn = false;

	    for (User user : userList) {
	        if (user_name.equals(user.getFname()) && password.equals(user.getPassword())) {
	            session.setAttribute("user", user);
	            loggedIn = true;
	            break;
	        }
	    }

	    if (loggedIn) {
	        return ResponseEntity.ok("redirect:/account");
	    } else {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed. Invalid username or password.");
	    }
	}


	@GetMapping("/user-logout")
	public String login_animation(HttpSession s) {
		s.invalidate();
		return "redirect:/logins";
	}

	@GetMapping("/forgot")
	public String forgot() {
		return "forgot";
	}

	@PostMapping("/send-otp")
	public String sendotp(@RequestParam("email") String email, HttpSession session,RedirectAttributes redirAttrs) {

		Random random = new Random(1000);
		int otp = random.nextInt(99999);

		String subject = "Forgot Password";
		String message = "" + "<table border='0' cellpadding='0' cellspacing='0' width='100%'>" + "<tr>"
				+ "<td bgcolor='#F9FAFC'>" + "<div align='center' style='padding: 45px 0;'>"
				+ "<table border='0' cellpadding='0' cellspacing='0' style='font-family:Arial,Helvetica,sans-serif;font-size:16px;line-height:1.5em;max-width: 500px;'>"
				+ "<thead>" + "<tr>" + "<td style='text-align: center;'>"
				+ "<img src='https://i0.wp.com/www.writefromscratch.com/wp-content/uploads/2018/12/demo-logo.png?ssl=1' style='margin-bottom: 1rem; width: 110px;' alt=''>"
				+ "</td>" + "</tr>" + "<tr>"
				+ "<td style='background-color: #1f74ca; color: white; padding: 0 20px; border-radius: 15px 15px 0 0;'>"
				+ "<h2 align='center'>Reset Password</h2>" + "</td>" + "</tr>" + "</thead>"
				+ "<tbody style='background-color: white;padding: 40px 20px;border-radius: 0 0 15px 15px;display: block;box-shadow: 0 10px 30px -30px black;'>"
				+ "<tr>" + "<td>" + "<p align='center' style='margin: 0; color: #475467;'>Hi,<strong>User</strong></p>"
				+ "</td>" + "</tr>" + "<tr>" + "<td>"
				+ "<p align='center' style='color: #7a899f;margin-bottom: 0;font-size: 14px;'>We're sending you this email because you requested a password reset.</p>"
				+ "</td>" + "</tr>" + "<tr>" + "<td align='center'>"
				+ "<a href='javascript:void(0)' style='text-decoration: none;display: inline-block;min-width: 100px;text-align: center;padding: 10px 30px;margin: 30px auto;background-color: #1f74ca; color: white; border-radius: 10px; transition: all 0.3s ease;'>"
				+ otp + "</a>" + "</td>" + "</tr>" + "<tr>" + "<td>"
				+ "<p align='center' style='color: #7a899f;margin-bottom: 0;font-size: 14px;'>If you didn't request a password reset, you can ignore this email. Your password will not be changed.</p>"
				+ "</td>" + "</tr>" + "</tbody>" + "<tfoot>" + "<tr>" + "<td>" + "<p align='center'>"
				+ "<small style='color:#7a899f;'>&copy;2023 Copyright <a href='#' target='_blank' style='text-decoration: none; color: #1f74ca;'>Watch Store</a>. All Rights Reserved.</small>"
				+ "</p>" + "</td>" + "</tr>" + "</tfoot>" + "</table>" + "</div>" + "</td>" + "</tr>" + "</table>";
		String to = email;

		User findemail = userrepo.findByEmail(email);

		if (findemail != null) {
			boolean flag = this.emailService.sendEmail(subject, message, to);

			if (flag) {
				session.setAttribute("otp", otp);
				session.setAttribute("email", email);
				redirAttrs.addFlashAttribute("success", "OTP is sent to your email id");
				return "varify-otp";
			} else {

				return "redirect:/forgot";
			}
		} else {
			redirAttrs.addFlashAttribute("error", "Please enter registered email");
			return "redirect:/forgot";
		}
	}

	@PostMapping("/varify-otp")
	public String varifyotp(@RequestParam("otp") int otp, HttpSession session,RedirectAttributes redirAttrs) {

		int myotp = (int) session.getAttribute("otp");
		if (myotp == otp) {
			redirAttrs.addFlashAttribute("success", "Email varify successfully");
			return "reset-password";
		} else {
			redirAttrs.addFlashAttribute("error", "Please enter valid Otp");
			return "varify-otp";
		}
	}

	@PostMapping("/reset_password")
	public String reset_password(@RequestParam("newpassword") String newpassword,
			@RequestParam("confirmPassword") String confirmPassword,HttpSession session,RedirectAttributes redirAttrs) {
		String email = (String) session.getAttribute("email");
		
		if (!newpassword.equals(confirmPassword)) {
	        redirAttrs.addFlashAttribute("error", "Password and confirmation password do not match.");
	        return "reset-password";
	    }

		User user = this.userrepo.getUserByUserName(email);

		if (user != null) {
			String hashedPassword = hashPassword(newpassword);

			user.setPassword(hashedPassword);
			this.userrepo.save(user);
			
			redirAttrs.addFlashAttribute("success", "Password reset successfully.");
			return "redirect:/logins";
		} else {
			return "redirect:/reset-password";
		}
	}

	private String hashPassword(String password) {
		return password;
	}

	@GetMapping("/about-us")
	public String aboutus() {
		return "about-us";
	}
	
	@GetMapping("/contact-us")
	public String contact() {
		return "contact-us";
	}

	@GetMapping("/account")
	public String account(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/logins";
		} else {
			model.addAttribute("orders", udao.viewUserOrders(user));
		}
		return "my-account";
	}
	
	@PostMapping("/editaddress")
	public String editAddress(@ModelAttribute("user") User user,  RedirectAttributes redirAttrs) {
		udao.UserRegister(user);
		redirAttrs.addFlashAttribute("success", "Address edit successfully");
		return "redirect:/user-logout";
	}

	@GetMapping("order_details")
	public String detailsorder(@RequestParam("orderId") int OrderId, Model model, HttpSession session) {
		Order order = udao.getOrderById(OrderId);
		model.addAttribute("order", order);

		List<OrderDetails> sproductList = udao.getOrderDetailsByOrderId(order.getId());
		model.addAttribute("sproductList", sproductList);

		return "order_details";
	}

	@GetMapping("/shop")
	public String shop(@RequestParam(name = "category", required = false) String category,
			@RequestParam(name = "brand", required = false) String brand, Model model) {
		List<Product> productList = null;

		if (category == null && brand == null) {
			productList = adao.ShowProduct();
		} else if (category != null && brand == null) {
			productList = adao.viewProductsByCategoryName(category);
		} else if (category == null && brand != null) {
			productList = adao.viewProductsByBrandName(brand);
		} else {
			// You can handle this case if necessary.
		}

		model.addAttribute("category", adao.ShowCategory());
		model.addAttribute("brand", adao.ShowBrand());
		model.addAttribute("products", productList);

		return "shop";
	}

	@GetMapping("/product-details")
	public String pdetails(@RequestParam("id") Integer id, Model m) {
		Product product = adao.getProductById(id);
		m.addAttribute("product", product);
		if (product != null) {
			List<Product> product1 = adao.viewProductsByCategoryId(product.getCategory().getId());
			m.addAttribute("related", product1);
		}
		return "product-details";
	}

	@GetMapping("/productsearch")
	public String searchProduct(@Param("keyword") String keyword, Model model, HttpSession session) {
		List<Product> products = udao.ProductSearch(keyword);
		model.addAttribute("product", products);
		model.addAttribute("keyword", keyword);
		model.addAttribute("category", adao.ShowCategory());
		model.addAttribute("brand", adao.ShowBrand());
		return "product-search";
	}

	@GetMapping("/sort")
	public String sortProducts(@RequestParam("sort") String sortType, Model model) {
		List<Product> sortedProducts = null;
		if ("priceLowToHigh".equals(sortType)) {
			sortedProducts = adao.sortByPriceLowToHigh();
		} else if ("priceHighToLow".equals(sortType)) {
			sortedProducts = adao.sortByPriceHighToLow();
		} else if ("productNameZ".equals(sortType)) {
			sortedProducts = adao.sortByProductNameZ();
		} else if ("productNameA".equals(sortType)) {
			sortedProducts = adao.sortByProductNameA();
		}
		model.addAttribute("products", sortedProducts);
		model.addAttribute("category", adao.ShowCategory());
		model.addAttribute("brand", adao.ShowBrand());
		return "shop";
	}
	
	@GetMapping("/wishlist")
	public String wishlist(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		model.addAttribute("wishlist", udao.viewUserwishlist(user));
		return "wishlist";
	}
	
	@PostMapping("/addToWishlist")
	public String addToWishlist(@RequestParam int pid, HttpSession session, RedirectAttributes redirAttrs) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			redirAttrs.addFlashAttribute("info", "Please log in to add items to your wishlist");
			return "redirect:/logins";
		} else {
			Wishlist existingWishlistItem = wishlistrepo.findByProductAndUser_id(productrepo.getById(pid), user.getId());
			if (existingWishlistItem != null) {
				redirAttrs.addFlashAttribute("warning", "Product is already in your wishlist");
				return "redirect:/wishlist";
			} else {
				udao.addTowishlist(pid, user.getId());
				redirAttrs.addFlashAttribute("success", "Product added successfully.");
			}
		}
		return "redirect:/wishlist";
	}
	
	
	@GetMapping("/deletewishlist/{id}")
	public String deletewishlist(@PathVariable int id, HttpSession session,RedirectAttributes redirAttrs) {
		udao.DeleteWishlist(id);
		redirAttrs.addFlashAttribute("error", "Product removed successfully.");
		return "redirect:/wishlist";
	}

	@GetMapping("/cart")
	public String cart(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");

		List<Cart> cartItems = udao.ShowUserCart(user);
		double subtotal = udao.calculateCartSubtotal(cartItems);
		double charge = 100.0;
		double total = udao.calculateCartTotalWithShipping(cartItems, charge);

		model.addAttribute("cart", cartItems);
		model.addAttribute("subtotal", subtotal);
		model.addAttribute("total", total);
		model.addAttribute("charge", charge);

		return "cart";
	}
	
	@PostMapping("/addToCart")
	public String addToCart(@RequestParam int pid, @RequestParam int qty, HttpSession session, RedirectAttributes redirAttrs) {
	    User user = (User) session.getAttribute("user");

	    if (user == null) {
	        redirAttrs.addFlashAttribute("info", "Please log in to add items to your cart");
	        return "redirect:/logins"; // Redirect to the login page
	    } else {
	            udao.addToCart(pid, user.getId(), qty);
	            redirAttrs.addFlashAttribute("success", "Product added successfully.");
	    }
	    return "redirect:/cart"; // Redirect to the cart page
	}

	@PostMapping("/updateCart")
	public String updateCart(@RequestParam("id") Integer id, @RequestParam("qty") int qty, Model model,
			RedirectAttributes redirAttrs) {
		Cart cartItem = cartrepo.getById(id);

		if (cartItem != null) {
			double total = cartItem.getPrice() * qty;
			udao.updateCart(id, qty, total);
			redirAttrs.addFlashAttribute("update", "Product update successfully.");
		}
		return "redirect:/cart";
	}
	
	@PostMapping("/api/updateCart")
    public ResponseEntity<String> updateCarts(@RequestParam("id") Integer id, @RequestParam("qty") int qty) {
        Cart cartItem = cartrepo.getById(id);

        if (cartItem != null) {
            double total = cartItem.getPrice() * qty;
            udao.updateCart(id, qty, total);
            return ResponseEntity.ok("Product updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Cart item not found.");
        }
    }


	@GetMapping("/deletecart/{id}")
	public String deletecart(@PathVariable int id, HttpSession session,RedirectAttributes redirAttrs) {
		udao.DeleteCart(id);
		redirAttrs.addFlashAttribute("error", "Product removed successfully.");
		return "redirect:/cart";
	}

	@GetMapping("/checkout")
	public String checkout(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");

		if (user == null) {
			return "redirect:/logins";
		} else {
			List<Cart> cartItems = udao.ShowUserCart(user);

			double subtotal = udao.calculateCartSubtotal(cartItems);
			double charge = 100.0;
			double total = udao.calculateCartTotalWithShipping(cartItems, charge);

			double discountPercentage = 10;
			double discountAmount = total * (discountPercentage / 100);
			model.addAttribute("discountAmount", discountAmount);
			double discountedSubtotal = total - discountAmount;
			model.addAttribute("discountedSubtotal", discountedSubtotal);

			model.addAttribute("cart", cartItems);
			model.addAttribute("subtotal", subtotal);
			model.addAttribute("total", total);
			model.addAttribute("charge", charge);

			model.addAttribute("country", adao.ShowCountry());
			model.addAttribute("city", udao.showCity());
			model.addAttribute("state", udao.ShowState());
		}
		return "checkout";
	}


	@GetMapping("/payment")
	public String payment(@RequestParam("fname") String fname, @RequestParam("lname") String lname,
			@RequestParam("country") String country, @RequestParam("address1") String address1,
			@RequestParam("address2") String address2, @RequestParam("city") String city,
			@RequestParam("state") String state, @RequestParam("phone") String phone,
			@RequestParam("email") String email, Model model, HttpSession session) {
		
		User user = (User) session.getAttribute("user");

		ShippingAddress shippingAddress = new ShippingAddress();
		shippingAddress.setFname(fname);
		shippingAddress.setLname(lname);
		shippingAddress.setCountry(country);
		shippingAddress.setAddress1(address1);
		shippingAddress.setAddress2(address2);
		shippingAddress.setCity(city);
		shippingAddress.setState(state);
		shippingAddress.setPhone(phone);
		shippingAddress.setEmail(email);
		shippingAddress.setUser(user);
		model.addAttribute("shipping", shippingAddress);
		session.setAttribute("data", shippingAddress);

		List<Cart> cartItems = udao.ShowUserCart(user);
		model.addAttribute("cart", cartItems);

		double subtotal = udao.calculateCartSubtotal(cartItems);
		model.addAttribute("subtotal", subtotal);

		int charge = 100;
		model.addAttribute("charge", charge);

		double total = udao.calculateCartTotalWithShipping(cartItems, charge);
		model.addAttribute("total", total);

		Random randomGenerator = new Random();
		int RendomOrderId = randomGenerator.nextInt(100);
		model.addAttribute("OrderId", RendomOrderId);

		LocalDateTime now = LocalDateTime.now();
		System.out.println("Before Formatting: " + now);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formatDateTime = now.format(format);
		model.addAttribute("orderdate", formatDateTime);

		double discountPercentage = 10;
		double discountAmount = total * (discountPercentage / 100);
		model.addAttribute("discountAmount", discountAmount);
		double discountedSubtotal = total - discountAmount;
		model.addAttribute("discountedSubtotal", discountedSubtotal);

		return "payment";
	}

	@PostMapping("userorder")
	public String UserOrder(@RequestParam("orderid") Integer OrderId, @RequestParam("amount") Double amount,
			@RequestParam("total") Double total, @RequestParam("status") Integer status,
			@RequestParam("charge") Integer charge, @RequestParam("orderdate") String orderdate,
			@RequestParam("discount") Double discount, Model model, HttpSession session) {

		User user = (User) session.getAttribute("user");
		ShippingAddress shipping=(ShippingAddress) session.getAttribute("data");

		Order order = new Order();
		order.setUser(user);
		order.setShippingAddress(shipping);
		order.setOrderId(OrderId);
		order.setAmount(amount);
		order.setTotal(total);
		order.setStatus(status);
		order.setCharge(charge);
		order.setDiscount(discount);
		order.setOrderdate(orderdate);

		udao.SaveUserOrder(order, shipping);

		session.setAttribute("message", "data successfully Inserted...");
		session.setAttribute("order", order);

		return "redirect:/confirmorder";
	}

	@GetMapping("/confirmorder")
	public String confirmorder(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		List<Cart> cartItems = udao.ShowUserCart(user);
		model.addAttribute("carts", cartItems);
		return "confirmorders";
	}

	@PostMapping("/userconfirmorder")
	public String userconfirmorder(@RequestParam("productname") String[] productName, @RequestParam("price") Double[] price,
			@RequestParam("qty") Integer[] qty, @RequestParam("total") Double[] total,
			@RequestParam("pid") Integer[] productId, Model model,
			HttpSession session) {
		Order order=(Order) session.getAttribute("order");
		User user = (User) session.getAttribute("user");

		for (int i = 0; i < productName.length; i++) {
			String pname = productName[i];
			Double totals = total[i];
			Integer qtys = qty[i];
			Double prices = price[i];
			Integer proId = productId[i];

			OrderDetails orderProducts = new OrderDetails();
			orderProducts.setOrder(order);
			orderProducts.setProductname(pname);
			orderProducts.setPrice(prices);
			orderProducts.setQty(qtys);
			orderProducts.setTotal(totals);
			Product product = productrepo.getById(proId);
			orderProducts.setProduct(product);

			udao.SaveOrderProduct(orderProducts, user.getId());
			session.setAttribute("saveorder", orderProducts);

			session.removeAttribute("coupon");
		}
		return "redirect:/account";
	}

	@GetMapping("/invoice/{id}")
	public ModelAndView generatePdf(@PathVariable int id, HttpServletResponse response) {
		List<OrderDetails> order = udao.getOrderDetailsByOrderId(id);

		if (order == null) {
			return new ModelAndView("orderNotFound");
		}
		String htmlContent = generateOrderHtml(order);
		try {
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(htmlContent);
			renderer.layout();
			ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
			renderer.createPDF(pdfStream);

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=order_" + id + ".pdf");

			response.getOutputStream().write(pdfStream.toByteArray());
			response.getOutputStream().flush();
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("pdfGenerationError");
		}

		return null;
	}

	private String generateOrderHtml(List<OrderDetails> order) {
		String jspContent = "<html>\n" + "<head>\n" + "<style>"
				+ "body { font-family: Arial, sans-serif; } table { width: 100%; border-collapse: collapse; } th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }.store{color: #445f84; font-family: cursive; text-align: center; display: flex; justify-content: center; align-items: center;}.invoice{ text-align: center; font-family: serif; } th { background-color: #445f84; }"
				+ "</style>" + "</head>\n" + "<body>\n"
				+ "<div class='store'><img width='100' height='70' src='http://localhost:8080/Shoe_Store/assets/images/demos/demo-10/shoe-logo-footers.png' alt='Shoe Logo'></img><h1 class='store'> Watch Store</h1></div>"
				+ "<h2 class='invoice'>Order Invoice</h2>" + "<p>Order Id: " + order.get(0).getOrder().getId() + "</p>"
				+ "<p>Order Date: " + order.get(0).getOrder().getOrderdate() + "</p>" + "<p>Customer Name: "
				+ order.get(0).getOrder().getUser().getFname() + " " + order.get(0).getOrder().getUser().getLname()
				+ "</p>" + "<p>Email: " + order.get(0).getOrder().getShippingAddress().getEmail() + "</p>"
				+ "<p>Address: " + order.get(0).getOrder().getShippingAddress().getAddress1() + "</p>"
				+ "<p>Phone Number: " + order.get(0).getOrder().getShippingAddress().getPhone() + "</p>" + "<table>"
				+ "<tr>" + "<th>ID</th>" + "<th>Product Name</th>" + "<th>Price</th>" + "<th>Qty</th>"
				+ "<th>Total</th>" + "</tr>";

		int id = 1;
		for (OrderDetails product : order) {
			String productInfo = "<tr>" + "<td>" + id + "</td>" + "<td>" + product.getProductname() + "</td>" + "<td>"
					+ product.getPrice() + "</td>" + "<td>" + product.getQty() + "</td>" + "<td>" + product.getTotal()
					+ "</td>" + "</tr>";
			jspContent += productInfo;
			id++;
		}

		Double totalAmount = null;
		Double totalPrice = null;
		Double discount = null;
		Integer charge = null;
		for (OrderDetails product : order) {
			totalAmount = product.getOrder().getAmount();
			totalPrice = product.getOrder().getTotal();
			discount = product.getOrder().getDiscount();
			charge = product.getOrder().getCharge();
		}

		String totalInfo = "<tr>" + "<td colspan='4' style='text-align: left;'>Total Price</td>" + "<td>&#8377; "
				+ totalAmount + "</td>" + "</tr>" + "<tr>" + "<td colspan='4' style='text-align: left;'>Shipping</td>"
				+ "<td>&#8377; " + charge + "</td>" + "</tr>" + "<tr>"
				+ "<td colspan='4' style='text-align: left;'>Discount Price</td>" + "<td>&#8377; " + discount + "</td>"
				+ "</tr>" + "<tr>" + "<td colspan='4' style='text-align: left;'>Total</td>" + "<td>&#8377; "
				+ totalPrice + "</td>" + "</tr>";
		jspContent += totalInfo;

		String fullHTML = "</table>" + "</body>\n" + "</html>";
		jspContent += fullHTML;

		return jspContent;
	}

	// ---------------------------------------------------------------------------------------------------------------

	@ModelAttribute("Cartsize")
	public int cartSize(HttpSession session) {
		User user = (User) session.getAttribute("user");

		if (user != null) {
			List<Cart> cartItems = udao.ShowUserCart(user);
			return cartItems.size();
		} else {
			return 0;
		}
	}

	@ModelAttribute("cart")
	public List<Cart> getcart(HttpSession session) {
		User user = (User) session.getAttribute("user");

		if (user != null) {
			List<Cart> cartItems = udao.ShowUserCart(user);
			return cartItems;
		} else {
			return new ArrayList<Cart>();
		}
	}

	@ModelAttribute("subtotal")
	public double getCartSubtotal(HttpSession session) {
		User user = (User) session.getAttribute("user");

		if (user != null) {
			List<Cart> cartItems = udao.ShowUserCart(user);
			double subtotal = udao.calculateCartSubtotal(cartItems);
			return subtotal;
		} else {
			return 0.0;
		}
	}

	@ModelAttribute("wishlist")
	public List<Wishlist> getWishlist(HttpSession session) {
		User user = (User) session.getAttribute("user");

		if (user != null) {
			List<Wishlist> wishlistItems = udao.viewUserwishlist(user);
			return wishlistItems;
		} else {
			return new ArrayList<Wishlist>();
		}
	}

	@ModelAttribute("Wishlistsize")
	public int Wishlistsize(HttpSession session) {
		User user = (User) session.getAttribute("user");

		if (user != null) {
			List<Wishlist> wishlistItems = udao.viewUserwishlist(user);
			return wishlistItems.size();
		} else {
			return 0;
		}
	}

	@ModelAttribute("category")
	public List<Category> showcat(HttpSession session) {
		List<Category> category = adao.ShowCategory();
		return category;
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

}
