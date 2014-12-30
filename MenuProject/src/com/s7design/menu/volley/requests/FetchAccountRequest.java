package com.s7design.menu.volley.requests;

import java.util.Map;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.s7design.menu.volley.responses.FetchAccountResponse;
import com.s7design.menu.volley.responses.GetCurrencyResponse;
import com.s7design.menu.volley.responses.LoginResponse;

public class FetchAccountRequest extends GsonRequest<FetchAccountResponse> {

	public FetchAccountRequest(Activity context, Map<String, String> params, Listener<FetchAccountResponse> listener) {
		super(context, Request.Method.POST, "fetchaccountinfo", params, FetchAccountResponse.class, listener, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

}
