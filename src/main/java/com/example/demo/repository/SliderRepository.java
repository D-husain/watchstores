package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Slider;

public interface SliderRepository extends JpaRepository<Slider, Integer> {

}
