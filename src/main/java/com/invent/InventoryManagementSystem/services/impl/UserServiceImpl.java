package com.invent.InventoryManagementSystem.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.invent.InventoryManagementSystem.dtos.LoginRequest;
import com.invent.InventoryManagementSystem.dtos.RegisterRequest;
import com.invent.InventoryManagementSystem.dtos.Response;
import com.invent.InventoryManagementSystem.dtos.UserDTO;
import com.invent.InventoryManagementSystem.enums.UserRole;
import com.invent.InventoryManagementSystem.exceptions.InvalidCredentialsException;
import com.invent.InventoryManagementSystem.exceptions.NotFoundException;
import com.invent.InventoryManagementSystem.models.User;
import com.invent.InventoryManagementSystem.repositories.UserRepository;
import com.invent.InventoryManagementSystem.security.JwtUtils;
import com.invent.InventoryManagementSystem.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service(value="userService")

@Slf4j

public class UserServiceImpl implements UserService {

		@Autowired
	    private UserRepository userRepository;
		@Autowired
	    private PasswordEncoder passwordEncoder;
		@Autowired
	    private ModelMapper modelMapper;
		@Autowired
	    private JwtUtils jwtUtils;
	    
//	    public UserServiceImpl(UserRepository userRepository) {
//	        this.userRepository = userRepository;
//	    }
//	    
//	    public UserServiceImpl(PasswordEncoder passwordEncoder) {
//	        this.passwordEncoder = passwordEncoder;
//	    }
//	
//	    public UserServiceImpl(ModelMapper modelMapper) {
//	        this.modelMapper = modelMapper;
//	    }
//	    
//	    public UserServiceImpl(JwtUtils jwtUtils) {
//	        this.jwtUtils = jwtUtils;
//	    }

    
    
	@Override
	public Response registerUser(RegisterRequest registerRequest) {
		// TODO Auto-generated method stub
		 UserRole role = UserRole.MANAGER;

	        if (registerRequest.getRole() != null) {
	            role = registerRequest.getRole();
	        }

	        User userToSave = User.builder()
	                .name(registerRequest.getName())
	                .email(registerRequest.getEmail())
	                .password(passwordEncoder.encode(registerRequest.getPassword()))
	                .phoneNumber(registerRequest.getPhoneNumber())
	                .role(role)
	                .build();

	        userToSave.setId(null);
	        userRepository.save(userToSave);
		return Response.builder()
                .status(200)
                .message("User was successfully registered")
                .build();
	}
	@Override
	public Response loginUser(LoginRequest loginRequest) {
		// TODO Auto-generated method stub
		 User user = userRepository.findByEmail(loginRequest.getEmail())
	                .orElseThrow(() -> new NotFoundException("Email Not Found"));

	        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
	            throw new InvalidCredentialsException("Password Does Not Match");
	        }
	        String token = jwtUtils.generateToken(user.getEmail());

	        return Response.builder()
	                .status(200)
	                .message("User Logged in Successfully")
	                .role(user.getRole())
	                .token(token)
	                .expirationTime("6 months")
	                .build();
	}
	@Override
	public Response getAllUsers() {
		// TODO Auto-generated method stub
		 List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

	        users.forEach(user -> user.setTransactions(null));

	        List<UserDTO> userDTOS = modelMapper.map(users, new TypeToken<List<UserDTO>>() {
	        }.getType());

	        return Response.builder()
	                .status(200)
	                .message("success")
	                .users(userDTOS)
	                .build();
	}
	@Override
	public User getCurrentLoggedInUser() {
		// TODO Auto-generated method stub
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User Not Found"));

        user.setTransactions(null);

        return user;
	}
	@Override
	public Response getUserById(Long id) {
		// TODO Auto-generated method stub
		User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User Not Found"));

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        userDTO.setTransactions(null);

        return Response.builder()
                .status(200)
                .message("success")
                .user(userDTO)
                .build();
	}
	@Override
	public Response updateUser(Long id, UserDTO userDTO) {
		// TODO Auto-generated method stub
		User existingUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User Not Found"));

        if (userDTO.getEmail() != null) existingUser.setEmail(userDTO.getEmail());
        if (userDTO.getPhoneNumber() != null) existingUser.setPhoneNumber(userDTO.getPhoneNumber());
        if (userDTO.getName() != null) existingUser.setName(userDTO.getName());
        if (userDTO.getRole() != null) existingUser.setRole(userDTO.getRole());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        userRepository.save(existingUser);

        return Response.builder()
                .status(200)
                .message("User successfully updated")
                .build();
	}
	@Override
	public Response deleteUser(Long id) {
		// TODO Auto-generated method stub
		 userRepository.findById(id).orElseThrow(() -> new NotFoundException("User Not Found"));

	        userRepository.deleteById(id);

	        return Response.builder()
	                .status(200)
	                .message("User successfully Deleted")
	                .build();
	}
	@Override
	public Response getUserTransactions(Long id) {
		// TODO Auto-generated method stub
		 User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User Not Found"));

	        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

	        userDTO.getTransactions().forEach(transactionDTO -> {
	            transactionDTO.setUser(null);
	            transactionDTO.setSupplier(null);
	        });

	        return Response.builder()
	                .status(200)
	                .message("success")
	                .user(userDTO)
	                .build();
	}

}
