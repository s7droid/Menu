package com.s7design.menu.callbacks;

public interface OnIBeaconSearchResultCallback {

	public static final int SEARCH_RESULT_BEACON_FOUND = 1;
	public static final int SEARCH_RESULT_BEACON_NOT_FOUND = 2;

	public void onIBeaconSearchResult(int result);

}
