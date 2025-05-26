package com.invent.InventoryManagementSystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.invent.InventoryManagementSystem.exceptions.NotFoundException;
import com.invent.InventoryManagementSystem.models.User;
import com.invent.InventoryManagementSystem.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
	
	public UserRepository userRepository;
	
	@Autowired
    public CustomUserDetailsService(UserRepository userRepository) { // Constructor injection
        this.userRepository = userRepository;
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		 User user = userRepository.findByEmail(username)
	                .orElseThrow(() -> new NotFoundException("User Email Not Found"));

		return AuthUser.builder().user(user).build();
	}
}
