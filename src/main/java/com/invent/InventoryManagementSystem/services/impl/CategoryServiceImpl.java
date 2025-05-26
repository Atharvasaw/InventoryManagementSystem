package com.invent.InventoryManagementSystem.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.invent.InventoryManagementSystem.dtos.CategoryDTO;
import com.invent.InventoryManagementSystem.dtos.Response;
import com.invent.InventoryManagementSystem.exceptions.NotFoundException;
import com.invent.InventoryManagementSystem.models.Category;
import com.invent.InventoryManagementSystem.repositories.CategoryRepository;
import com.invent.InventoryManagementSystem.repositories.ProductRepository;
import com.invent.InventoryManagementSystem.services.CategoryService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
    private ModelMapper modelMapper;
//	@Autowired
//	private ProductRepository productRepository;

	@Override
	public Response createCategory(CategoryDTO categoryDTO) {
		// TODO Auto-generated method stub
		 Category categoryToSave = modelMapper.map(categoryDTO, Category.class);

	        categoryRepository.save(categoryToSave);

	        return Response.builder()
	                .status(200)
	                .message("Category Saved Successfully")
	                .build();

	}

	@Override
	public Response getAllCategories() {
		// TODO Auto-generated method stub
		List<Category> categories = categoryRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        categories.forEach(category -> category.setProducts(null));

        List<CategoryDTO> categoryDTOList = modelMapper.map(categories, new TypeToken<List<CategoryDTO>>() {
        }.getType());

        return Response.builder()
                .status(200)
                .message("success")
                .categories(categoryDTOList)
                .build();
	}

	@Override
	public Response getCategoryById(Long id) {
		// TODO Auto-generated method stub
		 Category category = categoryRepository.findById(id)
	                .orElseThrow(() -> new NotFoundException("Category Not Found"));

	        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);

	        return Response.builder()
	                .status(200)
	                .message("success")
	                .category(categoryDTO)
	                .build();
	}

	@Override
	public Response updateCategory(Long id, CategoryDTO categoryDTO) {
		// TODO Auto-generated method stub
		Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category Not Found"));

        existingCategory.setName(categoryDTO.getName());

        categoryRepository.save(existingCategory);

        return Response.builder()
                .status(200)
                .message("Category Was Successfully Updated")
                .build();
	}

	@Override
	@Transactional
	public Response deleteCategory(Long id) {
		// TODO Auto-generated method stub
		
		categoryRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Category Not Found"));


		categoryRepository.deleteById(id);

		return Response.builder()
        .status(200)
        .message("Category Was Successfully Deleted")
        .build();
	}

}
