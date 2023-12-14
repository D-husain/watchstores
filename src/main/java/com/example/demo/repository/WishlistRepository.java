package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.dto.WishlistDTO;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.entity.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

	 @Query("SELECT w FROM Wishlist w WHERE w.product = :product AND w.user.id = :uid")
	    Wishlist findByProductAndUser_id(@Param("product") Product product, @Param("uid") int uid);

	 @Modifying
	 @Query("DELETE FROM Wishlist w WHERE w.product = :product AND w.user = :user")
	    void deleteByProductAndUser(@Param("product") Product product, @Param("user") User user);
	 
	    List<Wishlist> findByUser(User user);
	    
}
