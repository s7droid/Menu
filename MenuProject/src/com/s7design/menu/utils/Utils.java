package com.s7design.menu.utils;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class Utils {

	public static Gson getGson() {
		return new GsonBuilder().registerTypeAdapter(Date.class, new GsonDateSerializer()).registerTypeAdapter(Date.class, new GsonDateDeserializer()).disableHtmlEscaping().create();
	}

	static public class GsonDateSerializer implements JsonSerializer<Date> {
		public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
			return src == null ? null : new JsonPrimitive(src.getTime());
		}
	}

	static public class GsonDateDeserializer implements JsonDeserializer<Date> {
		public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			return json == null ? null : new Date(json.getAsLong());
		}
	}

	public static boolean isBluetoothEnabled() {
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (bluetoothAdapter == null) {
			return false;
		} else {
			if (bluetoothAdapter.isEnabled()) {
				return true;
			} else {
				return false;
			}
		}
	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	public static boolean isLocationEnabled(Context context) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	public static float round(float value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (float) tmp / factor;
	}

	public static float convertDpToPixel(int dp, Context context) {

		Resources r = context.getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
		return px;
	}

	public static void hideSoftKeyboard(Activity activity) {
		InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
	}

	public static void handleOutsideEditTextClick(View view, final Activity activity) {

		System.out.println("Called");

		// Set up touch listener for non-text box views to hide keyboard.
		if (!(view instanceof EditText)) {
			System.err.println("Instantiate proprely");
			view.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					System.err.println("ON TOUCH CALLED");
					hideSoftKeyboard(activity);
					return false;
				}

			});
		}

		// If a layout container, iterate over children and seed recursion.
		if (view instanceof ViewGroup) {

			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

				View innerView = ((ViewGroup) view).getChildAt(i);

				handleOutsideEditTextClick(innerView, activity);
			}
		}
	}

	public static boolean isValidExpiryDate(String month, String year) {

		if (month.length() < 2 || year.length() < 4)
			return false;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yy");
		simpleDateFormat.setLenient(false);
		try {
			Date cardDate = simpleDateFormat.parse(month + "/" + year);
			return !cardDate.before(new Date());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

}
