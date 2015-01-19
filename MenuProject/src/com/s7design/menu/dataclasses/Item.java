package com.s7design.menu.dataclasses;

public class Item {

	public String name;
	public String image;
	public String smalllabel;
	public String largelabel;
	public double smallprice;
	public double largeprice;
	public int smalltag;
	public int largetag;
	public String currency;
	public String category;
	public String description;
	public String ingredients;
	public int quantityLarge = 0;
	public int quantitySmall = 0;
	public double price;

	public Item getSmall() {
		Item item = getCopy();
		item.quantitySmall = 1;
		return item;
	}

	public Item getLarge() {
		Item item = getCopy();
		item.quantityLarge = 1;
		return item;
	}

	private Item getCopy() {

		Item item = new Item();
		item.name = name;
		item.image = image;
		item.smalllabel = smalllabel;
		item.largelabel = largelabel;
		item.smallprice = smallprice;
		item.largeprice = largeprice;
		item.smalltag = smalltag;
		item.largetag = largetag;
		item.currency = currency;
		item.category = category;
		item.price = price;

		return item;
	}
}
