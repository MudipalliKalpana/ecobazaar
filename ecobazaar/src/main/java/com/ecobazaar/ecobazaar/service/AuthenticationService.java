package com.ecobazaar.ecobazaar.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecobazaar.ecobazaar.dto.LoginRequest;
import com.ecobazaar.ecobazaar.dto.RegisterRequest;
import com.ecobazaar.ecobazaar.dto.UserResponse;
import com.ecobazaar.ecobazaar.model.User;
import com.ecobazaar.ecobazaar.repository.UserRepository;

@Service
public class AuthenticationService {
	private final UserRepository userRepo;
	
	public AuthenticationService(UserRepository userRepo) {
		this.userRepo=userRepo;
	}
	
	public UserResponse register(RegisterRequest request) {
		Optional<User> existing= userRepo.findByEmail(request.getEmail());
		if(existing.isPresent()) {
			throw new RuntimeException("Email is already taken!");
		}
		User user=new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		user.setRole("Customer");
		user.setEcoScore(0);
		
		userRepo.save(user);
		
		return new UserResponse(user.getId(),user.getName(),user.getEmail(),user.getRole(),user.getEcoScore());
	}
	
	public UserResponse login(LoginRequest request) {
		User user=userRepo.findByEmail(request.getEmail())
				.orElseThrow(()->new RuntimeException("User not found"));
		if(!user.getPassword().equals(request.getPassword())) {
			throw new RuntimeException("Invalid password");
		}
		
		return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getEcoScore());
		
	}
	
}
