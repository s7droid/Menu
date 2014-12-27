package com.s7design.menu.callbacks;

import com.android.volley.VolleyError;
import com.s7design.menu.volley.responses.GsonResponse;

public interface OnVolleyErrorCallback {

	public void onResponseError(GsonResponse response);
	public void onVolleyError(VolleyError volleyError);

}
