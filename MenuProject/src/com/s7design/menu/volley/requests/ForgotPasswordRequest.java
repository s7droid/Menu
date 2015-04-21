package com.s7design.menu.volley.requests;

import java.util.Map;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.s7design.menu.volley.responses.ForgotPasswordResponse;
import com.s7design.menu.volley.responses.GetCurrencyResponse;
import com.s7design.menu.volley.responses.LoginResponse;

public class ForgotPasswordRequest extends GsonRequest<ForgotPasswordResponse> {

	public ForgotPasswordRequest(Activity context, Map<String, String> params, Listener<ForgotPasswordResponse> listener) {
		super(context, Request.Method.POST, "forgotpassword", params, ForgotPasswordResponse.class, listener, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

}
