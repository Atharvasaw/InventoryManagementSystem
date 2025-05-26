package com.invent.InventoryManagementSystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.invent.InventoryManagementSystem.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	
	List<Product> findByNameContainingOrDescriptionContaining(String name, String description);

	@Modifying
	@Query("DELETE FROM Product p WHERE p.category.id = :id")
	void deleteAllByCategoryId(@Param("id") Long id);
}
