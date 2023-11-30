package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.ProductCountryOrigin;

public interface OriginRepository extends JpaRepository<ProductCountryOrigin, Integer> {

	@Query("SELECT c FROM ProductCountryOrigin c WHERE c.countryname = ?1")
	Optional<ProductCountryOrigin> findByCountryOriginName(String countryOriginByName);

}
