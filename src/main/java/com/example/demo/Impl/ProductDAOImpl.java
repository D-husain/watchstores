package com.example.demo.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.dao.ProductDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ProductDAOImpl implements ProductDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<String> getProductColors() {
		return entityManager.createQuery("SELECT DISTINCT p.colore FROM Product p", String.class).getResultList();
	}

}
