package com.example.demo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

	
	Product findByName(String name);
	
	Boolean existsByName(String name);

	@Query(value = "SELECT SUM(quantity) FROM Product")
	Integer getProductAmount();

	@Query(value = "SELECT c.name, SUM(p.quantity) as QTY FROM Product p JOIN p.category c ON p.category.category_id = c.category_id GROUP BY p.category.category_id ORDER BY QTY DESC")
	List<Object[]> getProductAmountByCategory();

	List<Product> findByCategory(Category category);
}
