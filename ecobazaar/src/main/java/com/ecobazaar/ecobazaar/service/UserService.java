package com.ecobazaar.ecobazaar.service;
//Not for project!!
//Basic Understanding purpose
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ecobazaar.ecobazaar.model.User;
import com.ecobazaar.ecobazaar.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepo;

	public UserService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	public User createUsers(User user) {
		return userRepo.save(user);
	}

	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	public User getUserById(Long id) {
		return userRepo.findById(id).orElse(null);
	}

	public User updateUser(Long id, User userDetails) {
		return userRepo.findById(id).map(user -> {
			user.setName(userDetails.getName());
			user.setEmail(userDetails.getEmail());
			if (userDetails.getPassword() != null && !userDetails.getPassword().isBlank()) {
				user.setPassword(userDetails.getPassword());
			}
			user.setRole(userDetails.getRole());
			user.setEcoScore(userDetails.getEcoScore());
			return userRepo.save(user);
		}).orElse(null);
	}

	public boolean deleteUser(Long id) {
		if (userRepo.existsById(id)) {
			userRepo.deleteById(id);
			return true;
		}
		return false;
	}

	public User patchUser(Long id, Map<String, Object> updates) {
		return userRepo.findById(id).map(user -> {
			updates.forEach((key, value) -> {
				switch (key) {
				case "name":
					user.setName((String) value);
					break;
				case "email":
					user.setEmail((String) value);
					break;
				case "role":
					user.setRole((String) value);
					break;
				case "password":
					user.setPassword((String) value);
					break;
				case "ecoscore":
					user.setEcoScore((Integer) value);
					break;
				}
			});
			return userRepo.save(user);
		}).orElse(null);
	}
}
