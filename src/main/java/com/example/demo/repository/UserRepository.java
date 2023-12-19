package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query("SELECT u FROM User u WHERE u.email = ?1")
    public User findByEmail(String email); 
	
	@Query("select u from User u where u.email = :email")
    public User getUserByUserName(@Param("email") String email);

	@Query("SELECT u FROM User u WHERE u.fname = ?1")
	public User findByUsername(String username);

}
