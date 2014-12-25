package com.s7design.menu.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Settings {

	private static final String PREFERENCE_NAME = "Preferences";

	public static final String PREFERENCE_TAG_ACCESS_TOKEN = "access_token";

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

}
