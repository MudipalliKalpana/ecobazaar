package com.ecobazaar.ecobazaar.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecobazaar.ecobazaar.dto.CartSummaryDTO;
import com.ecobazaar.ecobazaar.model.CartItem;
import com.ecobazaar.ecobazaar.model.User;
import com.ecobazaar.ecobazaar.repository.UserRepository;
import com.ecobazaar.ecobazaar.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	private final CartService cartService;
	private final UserRepository userRepository;
	
	public CartController(CartService cartService, UserRepository userRepository ) {
		this.cartService=cartService;
		this.userRepository=userRepository;
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping
	public CartItem addItem(@RequestBody CartItem cartItem) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User currentUser = userRepository.findByEmail(email)
		.orElseThrow(() -> new RuntimeException("User not found"));
		cartItem.setUserId(currentUser.getId());
		return cartService.addItems(cartItem);
	}
	
//	@GetMapping
//	public List<CartItem> getAllCarts(){
//		return cartService.getAllCartItems();
//	}
	
//	@GetMapping("/{userId}")
//	public List<CartItem> getCartByUserId(@PathVariable Long userId) {
//		return cartService.getCartByUserId(userId);
//	}
//	
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping
	public CartSummaryDTO getCartSummary() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User currentUser = userRepository.findByEmail(email)
		.orElseThrow(() -> new RuntimeException("User not found"));
		return cartService.getCartSummary(currentUser.getId());
	}
	
	@PreAuthorize("hasRole('USER')")
	@DeleteMapping("/id")
	public String deleteItem(@PathVariable Long id) {
		cartService.removeFromCart(id);
		
		return "Item Removed from cart";
	}
}
