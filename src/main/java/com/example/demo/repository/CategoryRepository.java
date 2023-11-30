package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query("SELECT c FROM Category c WHERE c.cname = ?1")
    Optional<Category> findByCname(String cname);

}
