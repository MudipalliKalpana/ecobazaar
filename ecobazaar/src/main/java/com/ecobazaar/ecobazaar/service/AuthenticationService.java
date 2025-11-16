package com.ecobazaar.ecobazaar.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecobazaar.ecobazaar.dto.LoginRequest;
import com.ecobazaar.ecobazaar.dto.RegisterRequest;
import com.ecobazaar.ecobazaar.dto.UserResponse;
import com.ecobazaar.ecobazaar.model.User;
import com.ecobazaar.ecobazaar.repository.UserRepository;
import com.ecobazaar.ecobazaar.security.JwtUtil;

@Service
public class AuthenticationService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}

	public UserResponse register(RegisterRequest request) {
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new RuntimeException("Email already exists!");
		}
		// Default role = ROLE_USER
		String role = "ROLE_USER";
		if (role.equals("ROLE_ADMIN")) {
			throw new RuntimeException("Cannot self-register as admin!");
		}
		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(role);
		user.setEcoScore(0);
		User saved = userRepository.save(user);
		return new UserResponse(saved.getId(), saved.getName(), saved.getEmail(), saved.getRole(), 0, null);

	}

	// Login user

	public UserResponse login(LoginRequest login) {
		User user = userRepository.findByEmail(login.getEmail())
				.orElseThrow(() -> new RuntimeException("User not found!"));
		if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid credentials!");
		}
		String token = jwtUtil.generateToken(user.getEmail(), user.getRole(), user.getId());
		return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getEcoScore(),
				token);

	}

}
