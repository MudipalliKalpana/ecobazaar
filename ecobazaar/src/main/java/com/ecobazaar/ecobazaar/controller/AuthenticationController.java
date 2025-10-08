package com.ecobazaar.ecobazaar.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecobazaar.ecobazaar.dto.LoginRequest;
import com.ecobazaar.ecobazaar.dto.RegisterRequest;
import com.ecobazaar.ecobazaar.dto.UserResponse;
import com.ecobazaar.ecobazaar.service.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
	private final AuthenticationService authService;
	
	public AuthenticationController(AuthenticationService authService) { //Constructor injection
		this.authService=authService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request){
		return ResponseEntity.ok(authService.register(request));
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest request){
		return ResponseEntity.ok(authService.login(request));
	}
}
