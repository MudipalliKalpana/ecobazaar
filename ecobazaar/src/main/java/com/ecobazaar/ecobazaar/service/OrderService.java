package com.ecobazaar.ecobazaar.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ecobazaar.ecobazaar.model.CartItem;
import com.ecobazaar.ecobazaar.model.Order;
import com.ecobazaar.ecobazaar.model.Product;
import com.ecobazaar.ecobazaar.repository.CartRepository;
import com.ecobazaar.ecobazaar.repository.OrderRepository;
import com.ecobazaar.ecobazaar.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {
	
	private final OrderRepository orderRepository;
	private final CartRepository cartRepository;
	private final ProductRepository productRepository;
	
	public OrderService(OrderRepository orderRepository, CartRepository cartRepository, ProductRepository productRepository) {
		
		this.orderRepository = orderRepository;
		this.cartRepository = cartRepository;
		this.productRepository = productRepository;
	}
	
	@Transactional
	public Order checkOut(Long userId) {
		List<CartItem> cartItems=cartRepository.findByUserId(userId);
		
		if(cartItems.isEmpty()) {
			throw new RuntimeException("Cart is Empty! can't checkout");
		}
		
		double totalPrice=0;
		double totalCarbon=0;
		
		for (CartItem cartItem : cartItems) {
			Product product=productRepository.findById(cartItem.getProductId())
							.orElseThrow(()->new RuntimeException("Product not found"));
			totalPrice+=product.getPrice()*cartItem.getQuantity();
			totalCarbon+=product.getCarbonImpact()*cartItem.getQuantity();
				
		}
		
		 Order order=new Order(null,userId, LocalDate.now(), totalPrice, totalCarbon);
		 Order savedOrder=orderRepository.save(order);
		 
		 cartRepository.deleteAll(cartItems);
		 return savedOrder;
	}
	
	public List<Order> getOrderByUserId(Long userId){
		return orderRepository.findByUserId(userId);
	}
	
}

	
