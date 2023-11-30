package com.example.demo.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dao.AdminDao;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.Category;
import com.example.demo.entity.Coupons;
import com.example.demo.entity.User;
import com.example.demo.entity.UserCoupon;
import com.example.demo.repository.UserCouponRepository;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class CouponController {

	@Autowired
	private AdminDao adao;
	@Autowired
	private UserDao udao;
	@Autowired
	private UserCouponRepository usercouponrepo;
	@Autowired
	private UserRepository userrepo;
	
	@GetMapping("/coupons")
	public String coupons(Model model) {
		model.addAttribute("coupon", adao.ShowCoupon());
		return "admin/coupons";
	}

	@PostMapping("addcoupon")
	public String AddCoupon(@ModelAttribute("coupon") Coupons coupons,RedirectAttributes redirAttrs) {
		adao.AddCoupon(coupons);
		return "redirect:/coupons";
	}

	@GetMapping("/deletecoupon/{id}")
	public String deletecoupon(@PathVariable int id, HttpSession session) {
		adao.DeleteCoupon(id);
		session.setAttribute("coupon", "Services Delete Sucessfully..");
		return "redirect:/coupons";
	}
	
	//-------------------------------------------------------------------------------------------------------------------
	
	@PostMapping("/applycoupon")
	public String applycoupon(@RequestParam("uid") Integer userId, @RequestParam("code") String code, Model model,
			HttpSession session,RedirectAttributes redirAttrs) {
		User user = userrepo.getById(userId);
		UserCoupon existingCoupon = usercouponrepo.findByUserAndCoupon(user, code);
		if (existingCoupon != null) {
			redirAttrs.addFlashAttribute("error", "Coupon already applied");
			return "redirect:/checkout";
		} else {
			UserCoupon uc = new UserCoupon();
			uc.setUser(user);
			uc.setCoupon(code);
			udao.ApplyCoupon(uc);
			session.setAttribute("coupon", uc);
			redirAttrs.addFlashAttribute("success", "Coupon Apply successfully");
		}
		return "redirect:/checkout";
	}
	
	//--------------------------------------- API --------------------------------------------------------------------
	
	@GetMapping("/coupon/data")
	public ResponseEntity<List<Coupons>> getcouponList() {
		return new ResponseEntity<List<Coupons>>(adao.ShowCoupon(), HttpStatus.OK);
	}
	
	@PostMapping("/coupon/upadte/save")
	public ResponseEntity<Void> saveOrUpdatecoupon(@RequestBody Coupons coupons) {
		adao.AddCoupon(coupons);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@DeleteMapping("/coupon/delete/{id}")
	public ResponseEntity<Void> deleteCoupon(@PathVariable Integer id) {
		adao.DeleteCoupon(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
