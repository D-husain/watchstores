package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Reviews;
import com.example.demo.entity.User;

public interface ReviewsRepository extends JpaRepository<Reviews, Integer> {

	List<Reviews> findByUser(User user);

}
