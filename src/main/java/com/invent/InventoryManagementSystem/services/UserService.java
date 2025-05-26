package com.invent.InventoryManagementSystem.services;

import org.springframework.stereotype.Service;

import com.invent.InventoryManagementSystem.dtos.LoginRequest;
import com.invent.InventoryManagementSystem.dtos.RegisterRequest;
import com.invent.InventoryManagementSystem.dtos.Response;
import com.invent.InventoryManagementSystem.dtos.UserDTO;
import com.invent.InventoryManagementSystem.models.User;

@Service
public interface UserService {
	
	Response registerUser(RegisterRequest registerRequest);

    Response loginUser(LoginRequest loginRequest);

    Response getAllUsers();

    User getCurrentLoggedInUser();

    Response getUserById(Long id);

    Response updateUser(Long id, UserDTO userDTO);

    Response deleteUser(Long id);

    Response getUserTransactions(Long id);
	       

}
