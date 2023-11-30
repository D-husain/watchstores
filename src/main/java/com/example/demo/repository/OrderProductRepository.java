package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.OrderDetails;

public interface OrderProductRepository extends JpaRepository<OrderDetails, Integer> {

	 
		  @Query("SELECT o FROM OrderDetails o WHERE o.order.id = :id") 
		  List<OrderDetails> findByOrderid(@Param("id") int orderId);
		 
}
