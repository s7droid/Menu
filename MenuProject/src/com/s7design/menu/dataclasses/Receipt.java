package com.s7design.menu.dataclasses;

import java.io.Serializable;

public class Receipt implements Serializable{


	private static final long serialVersionUID = 8549055767470018716L;
	
	private String date;
	private String restaurantName;
	private String ammount;
	private String receiptId;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getAmmount() {
		return ammount;
	}
	public void setAmmount(String ammount) {
		this.ammount = ammount;
	}
	public String getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}
	
}
