package com.s7design.menu.volley.responses;

public class GetRestaurantInfoResponse extends GsonResponse {

	public String restaurantname;
	public String imageurl;

	@Override
	public String toString() {
		return "GetRestaurantInfoResponse [restaurantname=" + restaurantname + ", imageurl=" + imageurl + ", errordata=" + errordata + ", errorlog=" + errorlog + "]";
	}

}
