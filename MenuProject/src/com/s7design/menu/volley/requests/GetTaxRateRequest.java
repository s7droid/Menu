package com.s7design.menu.volley.requests;

import java.util.Map;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.s7design.menu.volley.responses.GetTaxRateResponse;

public class GetTaxRateRequest extends GsonRequest<GetTaxRateResponse> {

	public GetTaxRateRequest(Activity context, Map<String, String> params, Listener<GetTaxRateResponse> listener) {
		super(context, Request.Method.GET, "gettaxrate", params, true, GetTaxRateResponse.class, listener, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

}
