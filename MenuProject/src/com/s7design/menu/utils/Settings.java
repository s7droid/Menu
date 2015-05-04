package com.s7design.menu.utils;

import com.s7design.menu.volley.responses.GetRestaurantInfoResponse;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Settings {

	private static final String PREFERENCE_NAME = "Preferences";

	public static final String PREFERENCE_TAG_ACCESS_TOKEN = "access_token";
	public static final String PREFERENCE_TAG_MAJOR = "preference_access_tag_major";
	public static final String PREFERENCE_TAG_MINOR = "preference_access_tag_minor";
	public static final String PREFERENCE_TAG_BRAIN_TREE_TOKEN = "preference_brain_tree_token";
	public static final String PREFERENCE_TAG_RESTAURANT_INFO = "preference_tag_restaurant_info";
	public static final String PREFERENCE_TAG_DISCOUNT = "preference_tag_discount";
	public static final String PREFERENCE_TAG_CURRENCY = "preference_tag_currency";
	public static final String PREFERENCE_TAG_MIN_TIP = "preference_tag_min_tip";
	public static final String PREFERENCE_TAG_MAX_TIP = "preference_tag_max_tip";
	public static final String PREFERENCE_TAG_TAX = "preference_tag_tax";

	private static SharedPreferences getSharedPreferences(Context context) {
		return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
	}

	private static Editor getEditor(Context context) {
		return getSharedPreferences(context).edit();
	}

	public static void clear(Context context) {
		Editor editor = getEditor(context);
		editor.clear();
		editor.commit();
	}

	public static String getAccessToken(Context context) {
		return getSharedPreferences(context).getString(PREFERENCE_TAG_ACCESS_TOKEN, "");
	}

	public static void setAccessToken(Context context, String accessToken) {
		Editor editor = getEditor(context);
		editor.putString(PREFERENCE_TAG_ACCESS_TOKEN, accessToken);
		editor.commit();
	}

	public static String getMajor(Context context) {
		return getSharedPreferences(context).getString(PREFERENCE_TAG_MAJOR, "");
	}

	public static final void setMajor(Context context, String major) {
		Editor editor = getEditor(context);
		editor.putString(PREFERENCE_TAG_MAJOR, major);
		editor.commit();
	}

	public static String getMinor(Context context) {
		return getSharedPreferences(context).getString(PREFERENCE_TAG_MINOR, "");
	}

	public static final void setMinor(Context context, String minor) {
		Editor editor = getEditor(context);
		editor.putString(PREFERENCE_TAG_MINOR, minor);
		editor.commit();
	}

	public static final void setBraintreeToken(Context context, String token) {
		Editor editor = getEditor(context);
		editor.putString(PREFERENCE_TAG_BRAIN_TREE_TOKEN, token);
		editor.commit();
	}

	public static final String getBraintreeToken(Context context) {
		return getSharedPreferences(context).getString(PREFERENCE_TAG_BRAIN_TREE_TOKEN, "");
	}

	public static final void setRestaurantInfo(Context context, GetRestaurantInfoResponse restaurantInfo) {
		Editor editor = getEditor(context);
		editor.putString(PREFERENCE_TAG_RESTAURANT_INFO, Utils.getGson().toJson(restaurantInfo));
		editor.commit();
	}

	public static final GetRestaurantInfoResponse getRestaurantInfo(Context context) {
		GetRestaurantInfoResponse response = Utils.getGson().fromJson(getSharedPreferences(context).getString(PREFERENCE_TAG_RESTAURANT_INFO, ""), GetRestaurantInfoResponse.class);
		return response;
	}

	public static final void setDiscount(Context context, float discount) {
		Editor editor = getEditor(context);
		editor.putFloat(PREFERENCE_TAG_DISCOUNT, discount);
		editor.commit();
	}

	public static final float getDiscount(Context context) {
		return getSharedPreferences(context).getFloat(PREFERENCE_TAG_DISCOUNT, 0);
	}

	public static final void setCurrency(Context context, String currency) {
		Editor editor = getEditor(context);
		editor.putString(PREFERENCE_TAG_CURRENCY, currency);
		editor.commit();
	}

	public static final String getCurrency(Context context) {
		return getSharedPreferences(context).getString(PREFERENCE_TAG_CURRENCY, "");
	}

	public static final void setMinTip(Context context, float mintip) {
		Editor editor = getEditor(context);
		editor.putFloat(PREFERENCE_TAG_MIN_TIP, mintip);
		editor.commit();
	}

	public static final float getMinTip(Context context) {
		return getSharedPreferences(context).getFloat(PREFERENCE_TAG_MIN_TIP, 0);
	}

	public static final void setMaxTip(Context context, float maxtip) {
		Editor editor = getEditor(context);
		editor.putFloat(PREFERENCE_TAG_MAX_TIP, maxtip);
		editor.commit();
	}

	public static final float getMaxTip(Context context) {
		return getSharedPreferences(context).getFloat(PREFERENCE_TAG_MAX_TIP, 0);
	}

	public static final void setTax(Context context, float tax) {
		Editor editor = getEditor(context);
		editor.putFloat(PREFERENCE_TAG_TAX, tax);
		editor.commit();
	}

	public static final float getTax(Context context) {
		return getSharedPreferences(context).getFloat(PREFERENCE_TAG_TAX, 0);
	}

}