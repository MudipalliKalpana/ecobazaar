package com.ecobazaar.ecobazaar.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ecobazaar.ecobazaar.model.CartItem;
import com.ecobazaar.ecobazaar.model.Order;
import com.ecobazaar.ecobazaar.model.OrderItem;
import com.ecobazaar.ecobazaar.model.Product;
import com.ecobazaar.ecobazaar.repository.CartRepository;
import com.ecobazaar.ecobazaar.repository.OrderItemRepository;
import com.ecobazaar.ecobazaar.repository.OrderRepository;
import com.ecobazaar.ecobazaar.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

	private final OrderRepository orderRepository;
	private final CartRepository cartRepository;
	private final ProductRepository productRepository;
	private final OrderItemRepository orderItemRepository;

	public OrderService(OrderRepository orderRepository, CartRepository cartRepository,
			ProductRepository productRepository, OrderItemRepository orderItemRepository) {

		this.orderRepository = orderRepository;
		this.cartRepository = cartRepository;
		this.productRepository = productRepository;
		this.orderItemRepository = orderItemRepository;
	}

	@Transactional
	public Order checkOut(Long userId) {
		List<CartItem> cartItems = cartRepository.findByUserId(userId);

		if (cartItems.isEmpty()) {
			throw new RuntimeException("Cart is Empty! can't checkout");
		}

		double totalPrice = 0;
		double totalCarbon = 0;

		for (CartItem item : cartItems) {
			Product product = productRepository.findById(item.getProductId())
					.orElseThrow(() -> new RuntimeException("Product not found"));
			totalPrice += product.getPrice() * item.getQuantity();
			totalCarbon += product.getCarbonImpact() * item.getQuantity();
		}

		Order order = new Order(null, userId, LocalDate.now(), totalPrice, totalCarbon);
		Order savedOrder = orderRepository.save(order);

		for (CartItem item : cartItems) {
			OrderItem orderItem = new OrderItem();

			orderItem.setOrderId(savedOrder.getId());
			orderItem.setProductId(item.getProductId());
			orderItem.setQuantity(item.getQuantity());
			orderItemRepository.save(orderItem);
		}

		cartRepository.deleteAll(cartItems);
		return savedOrder;
	}

	public List<Order> getOrderByUserId(Long userId) {
		return orderRepository.findByUserId(userId);
	}

}
