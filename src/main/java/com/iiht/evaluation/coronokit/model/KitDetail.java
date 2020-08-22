package com.iiht.evaluation.coronokit.model;

public class KitDetail {

	private int productId;
	private String productName;
	private String productDescription;
	private int quantity;
	private Double amount;
	
	public KitDetail() {
		// TODO Auto-generated constructor stub
	}
	
	public KitDetail(int productId, String productName, String productDescription, int quantity, double amount) {
		this.productName = productName;
		this.productDescription = productDescription;
		this.productId = productId;
		this.quantity = quantity;
		this.amount = amount;
	}
	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	
	
}
