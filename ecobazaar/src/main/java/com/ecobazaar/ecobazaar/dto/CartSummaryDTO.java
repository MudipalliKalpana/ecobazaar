package com.ecobazaar.ecobazaar.dto;

import java.util.List;

import com.ecobazaar.ecobazaar.model.CartItem;

public class CartSummaryDTO {

	private List<CartItem> cartItems;
	private double totalPrice;
	private double totalCarbon;
	private String ecoSuggestion;

	public CartSummaryDTO() {

	}

	public CartSummaryDTO(List<CartItem> cartItems, double totalPrice, double totalCarbon, String ecoSuggestion) {
		super();
		this.cartItems = cartItems;
		this.totalPrice = totalPrice;
		this.totalCarbon = totalCarbon;
		this.ecoSuggestion = ecoSuggestion;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public double getTotalCarbon() {
		return totalCarbon;
	}

	public void setTotalCarbon(double totalCarbon) {
		this.totalCarbon = totalCarbon;
	}

	public String getEcoSuggestion() {
		return ecoSuggestion;
	}

	public void setEcoSuggestion(String ecoSuggestion) {
		this.ecoSuggestion = ecoSuggestion;
	}

}
