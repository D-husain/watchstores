package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.City;
import com.example.demo.entity.States;

public interface CityRepository extends JpaRepository<City, Integer> {

	 @Query("SELECT c FROM City c JOIN c.states s WHERE s = :states")
    List<City> findByState(@Param("states") States state);

	
	@Query("SELECT c FROM City c WHERE c.states.id = :id")
    List<City> findByStateId(@Param("id") Integer stateId);

}
