package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Order;
import com.example.demo.entity.User;

public interface OrderRepository extends JpaRepository<Order, Integer> {

	/*
	 * @Query("SELECT o FROM Order o WHERE o.user.id = :uid") List<Order>
	 * findByUid(@Param("uid") User user);
	 */

	List<Order> findByUser(User user);

	List<Order> findByStatus(int status);

	@Query("SELECT o FROM Order o WHERE o.user.id = :id")
	List<Order> findByUserId(@Param("id") Integer userId);

	@Query("SELECT o FROM Order o WHERE o.orderdate BETWEEN :startDate AND :endDate ORDER BY o.orderdate ASC")
	List<Order> findOrdersByDateRangeOrderByDateAsc(@Param("startDate") String startDate, @Param("endDate") String endDate);
	
	@Query("SELECT o FROM Order o WHERE o.orderdate BETWEEN :startDate AND :endDate ORDER BY o.orderdate ASC")
	List<Order> findOrdersByDateRangeOrderByDateAscs(@Param("startDate") LocalDate startDatel, @Param("endDate") LocalDate endDatel);
	
	 }
