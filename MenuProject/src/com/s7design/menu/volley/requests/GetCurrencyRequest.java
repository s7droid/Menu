package com.s7design.menu.volley.requests;

import java.util.Map;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.s7design.menu.volley.responses.GetCurrencyResponse;

public class GetCurrencyRequest extends GsonRequest<GetCurrencyResponse> {

	public GetCurrencyRequest(Activity context, Map<String, String> params, Listener<GetCurrencyResponse> listener) {
		super(context, Request.Method.GET, "getcurrency", params, GetCurrencyResponse.class, listener, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

}