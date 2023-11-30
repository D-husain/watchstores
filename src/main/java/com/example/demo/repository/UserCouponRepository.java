package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.User;
import com.example.demo.entity.UserCoupon;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Integer> {

	UserCoupon findByUserAndCoupon(User user, String coupon);
}
