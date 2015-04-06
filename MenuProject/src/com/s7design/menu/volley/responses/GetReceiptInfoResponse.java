package com.s7design.menu.volley.responses;

import com.s7design.menu.dataclasses.Item;

public class GetReceiptInfoResponse extends GsonResponse {

	public String response;
	public float orderprice;
	public float discount;
	public float tip;
	public float tax;
	public String restaurantname;
	public String tablenumber;
	public long ordernumber;
	public float taxrate;
	public float discountrate;
	public float tiprate;
	public String date;
	public String time;
	public Item[] items;
	public String currency;

}
