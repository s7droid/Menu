package com.s7design.menu.dataclasses;

import java.util.ArrayList;

import android.util.Log;

import com.s7design.menu.utils.Settings;
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
	private String major = "";
	private String minor = "";
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

		Log.d(TAG, "item large quantity " + item.quantityLarge);
		Log.d(TAG, "item small quantity " + item.quantitySmall);

		if (getItemByTag(item.largetag) == null)
			checkoutList.add(item);

		// Item it = getItemByTag(item.largetag);
		//
		//
		//
		// if (it != null) {
		//
		// Log.d(TAG, "it large quantity "+it.quantityLarge);
		// Log.d(TAG, "it small quantity "+it.quantitySmall);
		//
		// it.quantityLarge += item.quantityLarge;
		// it.quantitySmall += item.quantitySmall;
		//
		// Log.d(TAG, "it large quantity after "+it.quantityLarge);
		// Log.d(TAG, "it small quantity after "+it.quantitySmall);
		// } else {
		// checkoutList.add(item);
		// }

	}

	public void removeCheckoutListItem(int tag) {

		try {
			for (Item item : checkoutList) {
				if (item.largetag == tag) {
					if (--item.quantityLarge == 0 && item.quantitySmall == 0) {
						checkoutList.remove(item);
					}
				} else if (item.smalltag == tag) {
					if (--item.quantitySmall == 0 && item.quantityLarge == 0) {
						checkoutList.remove(item);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addCheckoutListItems(ArrayList<Item> items) {
		if (checkoutList == null)
			checkoutList = new ArrayList<Item>();
		checkoutList.addAll(items);
	}

	public boolean isCheckoutListEmpty() {

		if (checkoutList == null)
			checkoutList = new ArrayList<Item>();

		return checkoutList.size() == 0;
	}

	public void clearCheckoutList() {
		checkoutList.clear();
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

	public Item getItemByTag(int tag) {

		for (Item item : checkoutList) {
			if (item.largetag == tag || item.smalltag == tag)
				return item;
		}

		return null;
	}

}
