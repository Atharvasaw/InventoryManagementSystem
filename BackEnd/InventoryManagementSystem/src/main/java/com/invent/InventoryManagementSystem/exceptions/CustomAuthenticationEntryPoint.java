package com.invent.InventoryManagementSystem.exceptions;

import java.io.IOException;


import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invent.InventoryManagementSystem.dtos.Response;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Component
@AllArgsConstructor
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint{
	
	private ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		Response errorResponse = Response.builder()
              .status(HttpStatus.UNAUTHORIZED.value())
              .message(authException.getMessage())
              .build();

      response.setContentType("application/json");
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
		
	}
	

}
