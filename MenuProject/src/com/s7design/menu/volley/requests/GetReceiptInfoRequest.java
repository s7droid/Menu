package com.s7design.menu.volley.requests;

import java.util.Map;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.s7design.menu.volley.responses.GetReceiptInfoResponse;

public class GetReceiptInfoRequest extends GsonRequest<GetReceiptInfoResponse> {

	public GetReceiptInfoRequest(Activity context, Map<String, String> params, Listener<GetReceiptInfoResponse> listener) {
		super(context, Request.Method.POST, "getreceiptinfo", params, GetReceiptInfoResponse.class, listener, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

}
