package com.invent.InventoryManagementSystem.services;

import org.springframework.stereotype.Service;

import com.invent.InventoryManagementSystem.dtos.CategoryDTO;
import com.invent.InventoryManagementSystem.dtos.Response;

@Service
public interface CategoryService {
	
	Response createCategory(CategoryDTO categoryDTO);

    Response getAllCategories();

    Response getCategoryById(Long id);

    Response updateCategory(Long id, CategoryDTO categoryDTO);

    Response deleteCategory(Long id);

}
