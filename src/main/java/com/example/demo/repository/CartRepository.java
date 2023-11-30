package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;

import jakarta.transaction.Transactional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

	 	Optional<Cart> findByProduct(Product product);
	 
	 	@Query("SELECT c FROM Cart c WHERE c.product = :product AND c.user.id = :uid")
	    Cart findByProductAndUser_Uid(@Param("product") Product product, @Param("uid") int uid);
	 
	 	@Query("SELECT c FROM Cart c WHERE c.user.id = :uid")
	    List<Cart> findByUserUid(@Param("uid") int uid);

	    List<Cart> findByUser(User user);
	    
	    @Modifying
	    @Transactional
	    @Query("DELETE FROM Cart c WHERE c.user.id = :id")
	    void deleteByUid(@Param("id") Integer id);
}