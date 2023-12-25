package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.PasswordResetToken;

public interface PasswordResetTokenRepository extends  JpaRepository<PasswordResetToken, Integer> {
	
	@Query("SELECT p FROM PasswordResetToken p WHERE p.token = ?1")
    public PasswordResetToken findByToken(String token);
}
