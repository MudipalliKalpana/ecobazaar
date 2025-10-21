package com.ecobazaar.ecobazaar.dto;

public class UserReport {
	
	private Long userId;
	private String userName;
	private Long totalPurchase;
	private Double totalSpent;
	private Double totalCarbonSaved;
	private String ecoBadge;
	
	public UserReport() {
		
	}
	
	public UserReport(Long userId, String userName, Long totalPurchase, Double totalSpent, Double totalCarbonSaved,
			String ecoBadge) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.totalPurchase = totalPurchase;
		this.totalSpent = totalSpent;
		this.totalCarbonSaved = totalCarbonSaved;
		this.ecoBadge = ecoBadge;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getTotalPurchase() {
		return totalPurchase;
	}
	public void setTotalPurchase(Long totalPurchase) {
		this.totalPurchase = totalPurchase;
	}
	public Double getTotalSpent() {
		return totalSpent;
	}
	public void setTotalSpent(Double totalSpent) {
		this.totalSpent = totalSpent;
	}
	public Double getTotalCarbonSaved() {
		return totalCarbonSaved;
	}
	public void setTotalCarbonSaved(Double totalCarbonSaved) {
		this.totalCarbonSaved = totalCarbonSaved;
	}
	public String getEcoBadge() {
		return ecoBadge;
	}
	public void setEcoBadge(String ecoBadge) {
		this.ecoBadge = ecoBadge;
	}

}
