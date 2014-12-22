package com.s7design.menu.volley.requests;

import java.util.Map;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.s7design.menu.volley.responses.GetDiscountResponse;

public class GetDiscountRequest extends GsonRequest<GetDiscountResponse> {

	public GetDiscountRequest(Activity context, Map<String, String> params, Listener<GetDiscountResponse> listener) {
		super(context, Request.Method.GET, "getdiscount", params, GetDiscountResponse.class, listener, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

}
