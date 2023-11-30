package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.ProductBrand;

public interface ProductBrandRepository extends JpaRepository<ProductBrand, Integer> {

	@Query("SELECT b FROM ProductBrand b WHERE b.brand = ?1")
    Optional<ProductBrand> findBybrandname(String brand);

}
