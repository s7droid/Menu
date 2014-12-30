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
import com.s7design.menu.volley.responses.ModifyAccountResponse;

public class ModifyAccountRequest extends GsonRequest<ModifyAccountResponse> {

	public ModifyAccountRequest(Activity context, Map<String, String> params, Listener<ModifyAccountResponse> listener) {
		super(context, Request.Method.POST, "modifyaccount", params, ModifyAccountResponse.class, listener, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

}
