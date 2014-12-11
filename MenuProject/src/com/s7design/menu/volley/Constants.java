package com.s7design.menu.volley;

public class Constants {

	public static final String PREFIX_UNSECURE = "http://";
	public static final String PREFIX_SECURE = "https://";

	public static final boolean useSecurePrefix = false;

	public static String getPrefix() {
		if (useSecurePrefix)
			return PREFIX_SECURE;
		else
			return PREFIX_UNSECURE;
	}

	public static final String BASE_URL = "usemenu.com/api/v1/";

}
