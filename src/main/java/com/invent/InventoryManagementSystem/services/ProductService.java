package com.invent.InventoryManagementSystem.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.invent.InventoryManagementSystem.dtos.ProductDTO;
import com.invent.InventoryManagementSystem.dtos.Response;

@Service
public interface ProductService {

		Response saveProduct(ProductDTO productDTO, MultipartFile imageFile);

	    Response updateProduct(ProductDTO productDTO, MultipartFile imageFile);

	    Response getAllProducts();

	    Response getProductById(Long id);

	    Response deleteProduct(Long id);

	    Response searchProduct(String input);
	
}
