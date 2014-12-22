package com.s7design.menu.dataclasses;

import java.util.ArrayList;

import com.s7design.menu.volley.responses.GetRestaurantInfoResponse;

public class DataManager {

	private ArrayList<Item> checkoutList;
	private GetRestaurantInfoResponse restaurantInfo;
	private ArrayList<Category> categoriesList;
	private ArrayList<Item> itemsList;
	private Rate rate;
	private double discount;
	private String currency;

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public ArrayList<Item> getItemsList() {
		return itemsList;
	}

	public void setItemsList(ArrayList<Item> itemsList) {
		this.itemsList = itemsList;
	}

	public ArrayList<Item> getCheckoutList() {

		if (checkoutList == null)
			checkoutList = new ArrayList<Item>();

		// Item item = new Item();
		// item.name = "Tomato soup";
		// item.largeprice = 8.00f;
		// item.quantity = 1;
		//
		// Item item2 = new Item();
		// item2.name = "Orange chicken";
		// item2.largeprice = 10.50f;
		// item2.quantity = 2;
		//
		// checkoutList.add(item);
		// checkoutList.add(item2);

		return checkoutList;
	}

	public void addCheckoutListItem(Item item) {
		if (checkoutList == null)
			checkoutList = new ArrayList<Item>();
		checkoutList.add(item);
	}

	public void addCheckoutListItems(ArrayList<Item> items) {
		if (checkoutList == null)
			checkoutList = new ArrayList<Item>();
		checkoutList.addAll(items);
	}

	public ArrayList<Item> getTestCheckoutList() {
		return checkoutList;
	}

	public void setRestaurantInfo(GetRestaurantInfoResponse restaurantInfo) {
		this.restaurantInfo = restaurantInfo;
	}

	public GetRestaurantInfoResponse getRestaurantInfo() {
		return restaurantInfo;
	}

	public ArrayList<Category> getCategoriesList() {
		return categoriesList;
	}

	public void setCategoriesList(ArrayList<Category> categoriesList) {
		this.categoriesList = categoriesList;
	}

	public void setTaxRate(double tax) {
		if (rate == null)
			rate = new Rate();

		rate.tax = tax;
	}

	public void setTipRate(double min, double max) {
		if (rate == null)
			rate = new Rate();

		rate.mintip = min;
		rate.maxtip = max;
	}

	public Rate getRate() {
		return rate;
	}

}
