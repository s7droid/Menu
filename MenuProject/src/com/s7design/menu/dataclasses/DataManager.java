package com.s7design.menu.dataclasses;

import java.util.ArrayList;

public class DataManager {

	private ArrayList<Item> checkoutList;

	public ArrayList<Item> getCheckoutList() {

		if (checkoutList == null)
			checkoutList = new ArrayList<Item>();

		Item item = new Item();
		item.name = "Tomato soup";
		item.largeprice = 8.00f;
		item.quantity = 1;

		Item item2 = new Item();
		item2.name = "Orange chicken";
		item2.largeprice = 10.50f;
		item2.quantity = 2;

		checkoutList.add(item);
		checkoutList.add(item2);

		return checkoutList;
	}

}
