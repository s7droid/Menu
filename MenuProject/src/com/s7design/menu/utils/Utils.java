package com.s7design.menu.utils;

import java.lang.reflect.Type;
import java.util.Date;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
}
