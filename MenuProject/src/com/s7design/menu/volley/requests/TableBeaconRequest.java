package com.s7design.menu.volley.requests;

import java.util.Map;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.s7design.menu.volley.responses.GetItemInfoResponse;
import com.s7design.menu.volley.responses.TableBeaconResponse;

public class TableBeaconRequest extends GsonRequest<TableBeaconResponse> {

	public TableBeaconRequest(Activity context, Map<String, String> params, final int localRssi, final Listener<TableBeaconResponse> listener) {
		super(context, Request.Method.GET, "tablebeacon", params, TableBeaconResponse.class, new Listener<TableBeaconResponse>() {

			@Override
			public void onResponse(TableBeaconResponse response) {

				response.localRssi = localRssi;
				listener.onResponse(response);

			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

}
