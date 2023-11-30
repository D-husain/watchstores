package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Integer> {

	@Query("SELECT c FROM Country c WHERE c.countryname = ?1")
    Optional<Country> findByCountryName(String countryname);
	
	
	 
}
