package com.s7design.menu.volley.requests;

import java.util.Map;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.s7design.menu.volley.responses.GetAllItemsInCategoryResponse;
import com.s7design.menu.volley.responses.GetCategoriesResponse;
import com.s7design.menu.volley.responses.GetRestaurantInfoResponse;

public class GetAllItemsInCategoryRequest extends GsonRequest<GetAllItemsInCategoryResponse> {

	public GetAllItemsInCategoryRequest(Activity context, Map<String, String> params, Listener<GetAllItemsInCategoryResponse> listener) {
		super(context, Request.Method.GET, "getitems", params, true, GetAllItemsInCategoryResponse.class, listener, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

}
