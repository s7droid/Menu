package com.s7design.menu.volley.requests;

import java.util.Map;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.s7design.menu.volley.responses.GetCategoriesResponse;
import com.s7design.menu.volley.responses.GetRestaurantInfoResponse;

public class GetCategoriesRequest extends GsonRequest<GetCategoriesResponse> {

	public GetCategoriesRequest(Activity context, Map<String, String> params, Listener<GetCategoriesResponse> listener) {
		super(context, Request.Method.GET, "getcat", params, true, GetCategoriesResponse.class, listener, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

}