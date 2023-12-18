package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Query("SELECT p FROM Product p where p.category.id = ?1")
	List<Product> findByProductCategoryId(int cid);

	@Query("SELECT p FROM Product p where p.category.cname = ?1")
	List<Product> findByProductCategoryName(String category);

	@Query("SELECT p FROM Product p where p.brand.id = ?1")
	List<Product> findByProductBrandId(int bid);

	@Query("SELECT p FROM Product p where p.brand.brand = ?1")
	List<Product> findByProductBrandName(String brand);

	@Query("SELECT p FROM Product p ORDER BY p.price ASC")
	List<Product> sortByPriceLowToHigh();

	@Query("SELECT p FROM Product p ORDER BY p.price DESC")
	List<Product> sortByPriceHighToLow();

	@Query("SELECT p FROM Product p ORDER BY p.pname DESC")
	List<Product> sortByProductNameZ();

	@Query("SELECT p FROM Product p ORDER BY p.pname ASC")
	List<Product> sortByProductNameA();

	@Query("SELECT p FROM Product p WHERE p.pname LIKE %?1%")
	public List<Product> search(String keyword);

	@Query("SELECT COUNT(p) FROM Product p")
	int countAllProducts();

	@Query("SELECT p FROM Product p where p.colore = ?1")
	List<Product> findProductsByColor(String color);

	@Query("SELECT p FROM Product p WHERE p.category.cname = ?1 AND p.colore = ?2")
	List<Product> findProductsByCategoryAndColor(String category, String color);

	@Query("SELECT p FROM Product p WHERE p.category.cname = ?1 AND p.brand.brand=?2 AND p.colore = ?3")
	List<Product> findProductsByCategoryBrandAndColor(String category, String brand, String color);
	
	@Query("SELECT p FROM Product p WHERE p.brand.brand = ?1 AND p.colore = ?2")
	List<Product> findProductsByBrandAndColor(String brand, String color);

}
