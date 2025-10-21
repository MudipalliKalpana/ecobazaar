package com.ecobazaar.ecobazaar.controller;

//import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecobazaar.ecobazaar.dto.CartSummaryDTO;
import com.ecobazaar.ecobazaar.model.CartItem;
import com.ecobazaar.ecobazaar.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	
	private final CartService cartService;
	
	public CartController(CartService cartService) {
		this.cartService=cartService;
	}
	
	@PostMapping
	public CartItem addItem(@RequestBody CartItem cartItem) {
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
	@GetMapping("/{userId}")
	public CartSummaryDTO getCartSummary(@PathVariable Long userId) {
		return cartService.getCartSummary(userId);
	}
	@DeleteMapping("/id")
	public ResponseEntity<CartItem> deleteItem(@PathVariable Long id) {
		boolean deleted=cartService.removeFromCart(id);
		
		return deleted? ResponseEntity.noContent().build(): ResponseEntity.notFound().build();
	}
}
