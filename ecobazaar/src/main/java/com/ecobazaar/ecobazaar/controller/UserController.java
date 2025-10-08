package com.ecobazaar.ecobazaar.controller;
//Not for project!!
//Basic Understanding purpose
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecobazaar.ecobazaar.model.User;
import com.ecobazaar.ecobazaar.service.UserService;

@RestController 
@RequestMapping("/users")
public class UserController {
	private final UserService userService;
	
	public  UserController(UserService userService) {
		this.userService=userService;
	}
	
	@PostMapping
	public User createUsers(@RequestBody User user) {
		return userService.createUsers(user);
	}
	
	@GetMapping
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id){
		User user=userService.getUserById(id);
		return (user!=null)?ResponseEntity.ok(user):ResponseEntity.notFound().build();
	}
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id,@RequestBody User userDetails){
		User updatedUser=userService.updateUser(id,userDetails);
		return (updatedUser!=null)?ResponseEntity.ok(updatedUser):ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id){
		boolean deleted=userService.deleteUser(id);
		return deleted?ResponseEntity.noContent().build():ResponseEntity.notFound().build();
	}
	@PatchMapping("/{id}")
	public ResponseEntity<User> patchUser(@PathVariable Long id,@RequestBody Map<String,Object> updates){
		User updatedUser=userService.patchUser(id,updates);
		return (updatedUser!=null)?ResponseEntity.ok(updatedUser):ResponseEntity.notFound().build();
	}
}





