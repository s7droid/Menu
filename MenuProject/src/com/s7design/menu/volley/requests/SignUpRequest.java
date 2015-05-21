package com.s7design.menu.volley.requests;

import java.util.Map;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.s7design.menu.volley.responses.GetCurrencyResponse;
import com.s7design.menu.volley.responses.LoginResponse;
import com.s7design.menu.volley.responses.SignUpResponse;

public class SignUpRequest extends GsonRequest<SignUpResponse> {

	public SignUpRequest(Activity context, Map<String, String> params, Listener<SignUpResponse> listener) {
		super(context, Request.Method.POST, "signup", params, SignUpResponse.class, listener, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
			}
		});
	}

}
