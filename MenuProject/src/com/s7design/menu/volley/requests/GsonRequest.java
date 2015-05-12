package com.s7design.menu.volley.requests;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import com.s7design.menu.app.Menu;
import com.s7design.menu.utils.Utils;
import com.s7design.menu.volley.Constants;
import com.s7design.menu.volley.responses.GsonResponse;

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
		super(method, Constants.getPrefix() + Constants.BASE_URL + url + ".php" + (method == Request.Method.GET ? getParams(params, true) : ""), errorListener);
		this.listener = listener;
		this.outputType = outputType;
		this.activityContext = context;
		this.url = url;
		this.params = params;

		setTag(outputType);
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return params;
	}

	private static String getParams(Map<String, String> params, boolean isGet) {

		String locale = "en";
		
		try {
			if(Locale.getDefault().getCountry().equalsIgnoreCase("de")){
				locale = Locale.getDefault().getCountry().toLowerCase();
			}
		} catch (Exception e) {
			e.printStackTrace();
			locale = "en";
		}
		
		if (params == null)
			return "";

		params.put("lang", locale);
		
		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, String> entry : params.entrySet()) {

			if (sb.length() > 0)
				sb.append("&");
			else if (isGet)
				sb.append("?");

			try {
				sb.append(String.format("%s=%s", URLEncoder.encode(entry.getKey(), PROTOCOL_CHARSET), URLEncoder.encode(entry.getValue(), PROTOCOL_CHARSET)));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		}

		Log.d(TAG, "" + sb.toString());

		return sb.toString();
	}

	@Override
	public byte[] getBody() {
		try {
			return getParams(params, false).getBytes(PROTOCOL_CHARSET);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	@Override
	protected void deliverResponse(T response) {
		GsonResponse gsonResponse = GsonResponse.class.cast(response);
		String errordata = gsonResponse.errordata;

		Log.w(TAG, "deliverResponse " + gsonResponse.response);

		if (gsonResponse.response != null) {
			Log.w(TAG, "2 " + (!gsonResponse.response.equals("success")));
			Log.w(TAG, "3 " + (gsonResponse.response.length() != 0));
		}
		if (errordata != null)
			Log.w(TAG, "5 " + (errordata.equals("yes")));

		if ((gsonResponse.response != null && gsonResponse.response.equals("wronglogindetails")) || (errordata != null && errordata.equals("yes"))) {
			Menu.getInstance().onResponseErrorReceived(gsonResponse);
		} else {
			listener.onResponse(response);
		}
	}

	@Override
	public void deliverError(final VolleyError error) {

		try {

			if (error == null || error.networkResponse == null) {

				Log.w(TAG, "deliverError(), error message " + error.getMessage());
				
				if (error.networkResponse == null)
					Menu.getInstance().onVolleyErrorReceived(error);
				
				return;
			}

			Log.w(TAG, "deliverError(), code " + error.networkResponse.statusCode);
			final String errorBody = new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers));

			Log.w(TAG, "deliverError(), body " + errorBody);

			Menu.getInstance().onVolleyErrorReceived(error);

			super.deliverError(error);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			Log.v(outputType.getSimpleName(), "parseNetworkResponse() " + json);
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
