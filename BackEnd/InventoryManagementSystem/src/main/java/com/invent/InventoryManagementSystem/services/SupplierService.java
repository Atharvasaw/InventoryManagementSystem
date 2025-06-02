package com.invent.InventoryManagementSystem.services;

import org.springframework.stereotype.Service;

import com.invent.InventoryManagementSystem.dtos.Response;
import com.invent.InventoryManagementSystem.dtos.SupplierDTO;

@Service
public interface SupplierService {
	
	Response addSupplier(SupplierDTO supplierDTO);

    Response updateSupplier(Long id, SupplierDTO supplierDTO);

    Response getAllSupplier();

    Response getSupplierById(Long id);

    Response deleteSupplier(Long id);


}
