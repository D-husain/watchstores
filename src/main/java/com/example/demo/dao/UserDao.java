package com.example.demo.dao;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Cart;
import com.example.demo.entity.City;
import com.example.demo.entity.Contact;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetails;
import com.example.demo.entity.Product;
import com.example.demo.entity.Reviews;
import com.example.demo.entity.ShippingAddress;
import com.example.demo.entity.States;
import com.example.demo.entity.Subscribe;
import com.example.demo.entity.User;
import com.example.demo.entity.UserCoupon;
import com.example.demo.entity.Wishlist;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.CityRepository;
import com.example.demo.repository.ContactRepository;
import com.example.demo.repository.OrderProductRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ReviewsRepository;
import com.example.demo.repository.ShippingAddressRepository;
import com.example.demo.repository.StateRepository;
import com.example.demo.repository.SubscribeRepository;
import com.example.demo.repository.UserCouponRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WishlistRepository;


@Service
public class UserDao {
	
	@Autowired private UserRepository userrepo;
	@Autowired private WishlistRepository wishlistrepo;
	@Autowired private ContactRepository contactrepo;
	@Autowired private CartRepository cartrepo;
	@Autowired private ProductRepository productrepo;
	@Autowired private ShippingAddressRepository shippingrepo;
	@Autowired private OrderRepository orderrepo;
	@Autowired private OrderProductRepository orderproductrepo;
	@Autowired private UserCouponRepository UserCouponRepo;
	@Autowired private SubscribeRepository subscriberepo;
	@Autowired private StateRepository staterepo;
	@Autowired private CityRepository cityrepo;
	@Autowired private ReviewsRepository reviewrepo;
	
	/*
	 * public void UserReqister(User user) { u.save(user); }
	 */
	
	public boolean UserRegister(User user) {
		boolean f = false;
		try {
			this.userrepo.save(user);
			return f = true;
		} catch (Exception e) {
			System.out.println("error : " + e);
		}
		return f;
	}
	
	public List<User> fechAllUser() {
		return this.userrepo.findAll();
	}
	
	public User getUserById(int id) {
		Optional<User> user = userrepo.findById(id);
		if (user.isPresent()) {
			return user.get();
		}
		return null;
	}
	
	public void updateUser(User user) {
		userrepo.save(user);
	}
	
	public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
         
        userrepo.save(user);
    }
	
	public User findByUsername(String username) {
        return userrepo.findByUsername(username);
    }

	//------------------------------------------------ subscribe --------------------------------------------------------
	
	public void Subscribes(Subscribe subscribe) {
		subscriberepo.save(subscribe);
	}
	
	public List<Subscribe> ShowSubscribe(){
		return subscriberepo.findAll();
	}
	
	public void deleteSubscribe(Integer id) {
		subscriberepo.deleteById(id);
	}
	
	//------------------------------------------------ contact ----------------------------------------------------------
	
	public void AddContact(Contact contact) {
		contactrepo.save(contact);
	}
	
	public List<Contact> ShowContact(){
		return contactrepo.findAll();
	}
	
	public Contact getContactByIds(Integer id) {
		return contactrepo.findById(id).get();
	}
	
	public void ReplayMessage(int contactId, String email, String replay) {
		Optional<Contact> contact = contactrepo.findById(contactId);
        contact.ifPresent(contacts -> {
            contacts.setEmail(email);
            contacts.setReplay(replay);
            contactrepo.save(contacts);
        });
	}
	
	//------------------------------------------------ state city ----------------------------------------------------------
	
	public List<City> showCity() {
		return cityrepo.findAll();
	}
	
	public City getcityById(Integer id) {
		return cityrepo.findById(id).get();
	}
	
	public List<States> ShowState() {
		return staterepo.findAll();
	}
	
	public States getStateById(Integer id) {
		return staterepo.findById(id).get();
	}
	
	//------------------------------------------------ cart ----------------------------------------------------------
	
	public void addToCart(int pid, int uid, int qty) {
        Product product = productrepo.findById(pid).orElse(null);
        if (product != null) {
            // Check if the cart item already exists
            Cart existingCartItem = cartrepo.findByProductAndUser_Uid(product, uid);
            if (existingCartItem != null) {
                int existingQty = existingCartItem.getQty();
                int updatedQty = existingQty + qty;
                existingCartItem.setQty(updatedQty);
                existingCartItem.setTotal(existingCartItem.getPrice() * updatedQty);
                cartrepo.save(existingCartItem);
            } else {
                Cart newCartItem = new Cart();
                newCartItem.setUser(userrepo.getById(uid));
                newCartItem.setProduct(product);
                newCartItem.setPrice(product.getPrice());
                newCartItem.setQty(qty);
                newCartItem.setTotal(product.getPrice() * qty);
                cartrepo.save(newCartItem);
                
                Wishlist wishlistItem = wishlistrepo.findByProductAndUser_id(product, uid);
                if (wishlistItem != null) {
                    wishlistrepo.delete(wishlistItem);
                }
                
            }
        }
    }
	
	public double calculateCartSubtotal(List<Cart> cartItems) {
	    double subtotal = cartItems.stream()
	        .mapToDouble(Cart::getTotal)
	        .sum();
	    return subtotal;
	}
	
	public double calculateCartTotalWithShipping(List<Cart> cartItems, double shippingCharge) {
	    double subtotal = cartItems.stream()
	        .mapToDouble(Cart::getTotal)
	        .sum();

	    double totalWithShipping = subtotal + shippingCharge;

	    return totalWithShipping;
	}

	public void updateCart(int id, int qty, Double total) {
        Cart cartItem = cartrepo.findById(id).orElse(null);
        if (cartItem != null) {
            cartItem.setQty(qty);
            cartItem.setTotal(total);
            cartrepo.save(cartItem);
        }
    }
	
	public List<Cart> ShowCart() {
		return cartrepo.findAll();
	}
	
	public List<Cart> ShowUserCart(User user) {
        return cartrepo.findByUser(user);
    }
	
	public List<Cart> ShowUserCarts(Integer userId) {
        return cartrepo.findByUserUid(userId);
    }
	
	
	public void deleteCartItemsByUid(int uid) {
        cartrepo.deleteByUid(uid);
    }
	
	public void DeleteCart(int id) {
		cartrepo.deleteById(id);
	}
	
	
	
	public boolean updateCart(Integer id, int qty) {
        Cart cartItem = cartrepo.getById(id);
        
        if (cartItem != null) {
            double total = cartItem.getPrice() * qty;
            updateCart(id, qty, total);
            return true;
        }
        
        return false;
    }
	
	//------------------------------------------------ Product ----------------------------------------------------------
	
	/*
	 * public List<Product> SearchProduct(String keyword) { return
	 * productrepo.searchProductByName(keyword); }
	 */
	
	public List<Product> ProductSearch(String keyword) {
        if (keyword != null) {
            return productrepo.search(keyword);
        }
        return productrepo.findAll();
    }
	
	public Page<Product> listAll(int pageNum) {
	    int pageSize = 5;
	     
	    Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
	     
	    return productrepo.findAll(pageable);
	}
	
	public Reviews findByProductAndUser(Product product, User user) {
        return reviewrepo.findByProductAndUser(product, user);
    }
	
	public Product findById(Integer productId) {
        Optional<Product> productOptional = productrepo.findById(productId);
        return productOptional.orElse(null);
    }
	
	//------------------------------------------------ Review ----------------------------------------------------------
	
	
	public void AddRevirew(Reviews reviews) {
		reviewrepo.save(reviews);
	}
	
	public List<Reviews> viewUserreview(User user) {
		return reviewrepo.findByUser(user);
	}
	
	public List<Reviews> getReviewsByProduct(Product product) {
        return reviewrepo.findAllByProduct(product);
    }
	
	//------------------------------------------------ shipping Address------------------------------------------------------
	
	public List<ShippingAddress> ShowShippingAddress() {
		return shippingrepo.findAll();
	}
	
	public List<ShippingAddress> showShippingAddressesByUserId(int userId) {
	    return shippingrepo.findByUserId(userId);
	}


	public ShippingAddress getShippingById(int id) {
		Optional<ShippingAddress> shipping = shippingrepo.findById(id);
		if (shipping.isPresent()) {
			return shipping.get();
		}
		return null;
	}

	private static final AtomicLong NEXT_ID = new AtomicLong(1);

	public static Integer generateNextId() {
		return (int) NEXT_ID.getAndIncrement();
	}
	
	//------------------------------------------------ Wishlist ------------------------------------------------------
	
	public void addTowishlist(int pid, int uid) {
		Product product = productrepo.findById(pid).orElse(null);
		Wishlist wishlist = new Wishlist();
		wishlist.setUser(userrepo.getById(uid));
		wishlist.setProduct(product);
		wishlist.setPrice(product.getPrice());
		wishlistrepo.save(wishlist);
	}
	
	public List<Wishlist> ShowWishlist() {
		return wishlistrepo.findAll();
	}
	
	public List<Wishlist> viewUserwishlist(User user) {
        return wishlistrepo.findByUser(user);
    }
	
	public void DeleteWishlist(int id) {
		wishlistrepo.deleteById(id);
	}
	
	 
	//------------------------------------------------ userOrder ------------------------------------------------------
	
	
	
	//------------------------------------------------ userOrder ------------------------------------------------------
	
	/*
	 * public void SaveUserOrderss(Order order, ShippingAddress shippingAddress) {
	 * 
	 * shippingrepo.save(shippingAddress);
	 * order.setShippingAddress(shippingAddress); orderrepo.save(order);
	 * 
	 * }
	 */
	
	
	
	public void SaveUserOrder(Order order, ShippingAddress shippingAddress) {
		if (shippingAddress.getUser().getId() != null && shippingAddress.getUser().getId().equals(shippingAddress.getUser().getId())) {

			List<ShippingAddress> existingShippingAddresses = shippingrepo.findByUserId(shippingAddress.getUser().getId());

			if (!existingShippingAddresses.isEmpty()) {
				if (existingShippingAddresses.size() == 1) {

					ShippingAddress existingShippingAddress = existingShippingAddresses.get(0);
					existingShippingAddress.setAddress1(shippingAddress.getAddress1());
					existingShippingAddress.setAddress2(shippingAddress.getAddress2());
					existingShippingAddress.setCity(shippingAddress.getCity());
					existingShippingAddress.setCountry(shippingAddress.getCountry());
					existingShippingAddress.setEmail(shippingAddress.getEmail());
					existingShippingAddress.setFname(shippingAddress.getFname());
					existingShippingAddress.setLname(shippingAddress.getLname());
					existingShippingAddress.setPhone(shippingAddress.getPhone());
					existingShippingAddress.setState(shippingAddress.getState());

					shippingrepo.save(existingShippingAddress);

					order.setShippingAddress(existingShippingAddress);
				}
			} else {
				shippingrepo.save(shippingAddress);

				order.setShippingAddress(shippingAddress);
			}

			orderrepo.save(order);
		}
	}
	
	public List<Order> viewUserOrders(User user) {
        return orderrepo.findByUser(user);
    }
	
	public List<Order> viewOrders(Integer userId) {
        return orderrepo.findByUserId(userId);
    }
	
	public List<Order> ShowUserOrders() {
        return orderrepo.findAll(sortByIdAsc());
    }
	
	private Sort sortByIdAsc() {
        return Sort.by(Sort.Direction.DESC, "id");
    }

	public List<Order> getPendingOrders() {
        return orderrepo.findByStatus(1);
    }
	
	public List<Order> getActiveOrders() {
        return orderrepo.findByStatus(2);
    }
	
	public void updateActiveStatus(int orderId, int newStatus) {
        Optional<Order> orderOptional = orderrepo.findById(orderId);
        orderOptional.ifPresent(order -> {
            order.setStatus(newStatus);
            orderrepo.save(order);
        });
    }
	
	public List<Order> getShippingOrders() {
        return orderrepo.findByStatus(3);
    }
	
	public void updateShippingStatus(int orderId, int newStatus) {
        Optional<Order> orderOptional = orderrepo.findById(orderId);
        orderOptional.ifPresent(order -> {
            order.setStatus(newStatus);
            orderrepo.save(order);
        });
    }
	
	public List<Order> getDeliveredOrders() {
        return orderrepo.findByStatus(4);
    }
	
	public void updateDeliveredStatus(int orderId, int newStatus) {
        Optional<Order> orderOptional = orderrepo.findById(orderId);
        orderOptional.ifPresent(order -> {
            order.setStatus(newStatus);
            orderrepo.save(order);
        });
    }
	
	public List<Order> getCancelledOrders() {
        return orderrepo.findByStatus(5);
    }
	
	public void updateCancelledStatus(int orderId, int newStatus, String cancelOrder) {
		Optional<Order> orderOptional = orderrepo.findById(orderId);
        orderOptional.ifPresent(order -> {
            order.setStatus(newStatus);
            order.setCancelorder(cancelOrder);
            orderrepo.save(order);
        });
	}
	
	public Order getOrderById(int id) {
		Optional<Order> order = orderrepo.findById(id);
		if (order.isPresent()) {
			return order.get();
		}
		return null;
	}
	
	public List<Order> findByDateRangeAndOrderByDate(String startDate, String endDate) {
        return orderrepo.findOrdersByDateRangeOrderByDateAsc(startDate, endDate);
    }
	
	public List<Order> findByDateRangeAndOrderByDates(LocalDate startDatel, LocalDate endDatel) {
        return orderrepo.findOrdersByDateRangeOrderByDateAscs(startDatel, endDatel);
    }
	
	//------------------------------------------------ OrderDetails ------------------------------------------------------
	
	public void SaveOrderProduct(OrderDetails orderProducts, Integer userId) {
		orderproductrepo.save(orderProducts);
		cartrepo.deleteByUid(userId);
	}
	
    public List<OrderDetails> getOrderDetailsByOrderId(int orderId) {
    	 List<OrderDetails> orderDetailsList = orderproductrepo.findByOrderid(orderId);
		return orderDetailsList;
    }
    
    public Map<Integer, List<OrderDetails>> getOrderDetailsMap(List<Order> orders) {
        Map<Integer, List<OrderDetails>> orderDetailsMap = new HashMap<>();
        for (Order order : orders) {
            List<OrderDetails> orderDetails = getOrderDetailsByOrderId(order.getId());
            orderDetailsMap.put(order.getId(), orderDetails);
        }
        return orderDetailsMap;
    }

  //------------------------------------------------ UserCouon ------------------------------------------------------
    
    public void ApplyCoupon(UserCoupon userCoupon) {
    	UserCouponRepo.save(userCoupon);
	}

	

    
    
}
