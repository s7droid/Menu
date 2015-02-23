package com.s7design.menu.volley.requests;

import java.util.Map;

import android.app.Activity;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.s7design.menu.volley.responses.CheckIfPhoneNeededResponse;

public class CheckIfPhoneNeededRequest extends GsonRequest<CheckIfPhoneNeededResponse> {

	public CheckIfPhoneNeededRequest(Activity context, Map<String, String> params, Listener<CheckIfPhoneNeededResponse> listener) {
		super(context, Method.POST, "needsphonenumber", params, CheckIfPhoneNeededResponse.class, listener, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				
			}
		});
	}

}
