package com.s7design.menu.volley.requests;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import android.app.Activity;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.s7design.menu.utils.Utils;
import com.s7design.menu.volley.Constants;

public class GsonRequest<T> extends Request<T> {

	private static final String TAG = GsonRequest.class.getSimpleName();

	protected Gson gson = Utils.getGson();
	private Listener<T> listener;
	private Class<T> outputType;
	private Activity activityContext;
	private String url;
	private Map<String, String> params;

	private static final String PROTOCOL_CHARSET = "utf-8";

	public GsonRequest(Activity context, int method, String url, Map<String, String> params, Class<T> outputType, Listener<T> listener, ErrorListener errorListener) {
		super(method, Constants.getPrefix() + Constants.BASE_URL + url + ".php" + getParams(params), errorListener);
		this.listener = listener;
		this.outputType = outputType;
		this.activityContext = context;
		this.url = url;
		this.params = params;

		setTag(outputType);
	}

	// @Override
	// public String getBodyContentType() {
	// return "application/json";
	// }

	// @Override
	// protected Map<String, String> getParams() throws AuthFailureError {
	// return params;
	// }

	private static String getParams(Map<String, String> params) {

		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, String> entry : params.entrySet()) {

			if (sb.length() > 0)
				sb.append("&");
			else
				sb.append("?");

			try {
				sb.append(String.format("%s=%s", URLEncoder.encode(entry.getKey(), "UTF-8"), URLEncoder.encode(entry.getValue(), "UTF-8")));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return sb.toString();
	}

	@Override
	protected void deliverResponse(T response) {
		listener.onResponse(response);
	}

	@Override
	public void deliverError(final VolleyError error) {

		try {

			if (error == null || error.networkResponse == null) {

				Log.w(TAG, "deliverError(), error message " + error.getMessage());
				return;
			}

			Log.w(TAG, "deliverError(), code " + error.networkResponse.statusCode);
			final String errorBody = new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers));

			Log.w(TAG, "deliverError(), body " + errorBody);

			super.deliverError(error);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			Log.v(TAG, "parseNetworkResponse() " + json);
			return Response.success(gson.fromJson(json, outputType), HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));

		}
	}

	protected Activity getContext() {
		return activityContext;
	}

	protected void onBeforeRequest() {
		// default implementation is none.
	}

}
