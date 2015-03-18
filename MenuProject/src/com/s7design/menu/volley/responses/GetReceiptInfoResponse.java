package com.s7design.menu.volley.responses;

import com.s7design.menu.dataclasses.Item;

public class GetReceiptInfoResponse extends GsonResponse {

	public String response;
	public double orderprice;
	public double discount;
	public double tip;
	public double tax;
	public String date;
	public String time;
	public Item[] items;
	public String currency;

}
