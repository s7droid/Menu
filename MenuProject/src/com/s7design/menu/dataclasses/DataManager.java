package com.s7design.menu.dataclasses;

import java.util.ArrayList;

import android.util.Log;

import com.s7design.menu.volley.responses.GetRestaurantInfoResponse;

public class DataManager {

	private static final String TAG = DataManager.class.getSimpleName();

	private ArrayList<Item> checkoutList;
	private GetRestaurantInfoResponse restaurantInfo;
	private ArrayList<Category> categoriesList;
	private ArrayList<Item> itemsList;
	private Rate rate;
	private double discount;
	private String currency;
	private String clientBraintreeToken;
	private String major;
	private String minor;
	private String language = "en";

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getMinor() {
		return minor;
	}

	public void setMinor(String minor) {
		this.minor = minor;
	}

	public String getClientBraintreeToken() {
		return clientBraintreeToken;
	}

	public void setClientBraintreeToken(String clientBraintreeToken) {
		this.clientBraintreeToken = clientBraintreeToken;
	}

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

		for (Item item : checkoutList) {
			Log.w(TAG, "item qty small " + item.quantitySmall);
			Log.w(TAG, "item qty large " + item.quantityLarge);
		}

		return checkoutList;
	}

	public void addCheckoutListItem(Item item) {
		if (checkoutList == null)
			checkoutList = new ArrayList<Item>();
		Log.d(TAG, "item qty small " + item.quantitySmall);
		Log.d(TAG, "item qty large " + item.quantityLarge);
		checkoutList.add(item);

		for (Item i : checkoutList) {
			Log.w(TAG, "item qty small " + i.quantitySmall);
			Log.w(TAG, "item qty large " + i.quantityLarge);
		}
	}

	public void addCheckoutListItems(ArrayList<Item> items) {
		if (checkoutList == null)
			checkoutList = new ArrayList<Item>();
		checkoutList.addAll(items);
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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
