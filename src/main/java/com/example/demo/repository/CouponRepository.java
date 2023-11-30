package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Coupons;

public interface CouponRepository extends JpaRepository<Coupons, Integer>{

	Optional<Coupons> findByCode(String enteredCoupon);

}
