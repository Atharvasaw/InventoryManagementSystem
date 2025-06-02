package com.invent.InventoryManagementSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.invent.InventoryManagementSystem.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	

}
