package com.invent.InventoryManagementSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.invent.InventoryManagementSystem.models.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long>{
	

}
