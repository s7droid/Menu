package com.s7design.menu.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v4.util.Pair;

import com.android.volley.VolleyError;
import com.s7design.menu.callbacks.OnVolleyErrorCallback;
import com.s7design.menu.dataclasses.DataManager;
import com.s7design.menu.utils.Settings;

public class Menu extends Application {

	private final String TAG = Menu.class.getSimpleName();

	private static Menu instance;

	private List<Activity> activityStack;
	private Map<String, Pair<String, Pair<String, String>>> responseTimeStatistics;

	private static Context context;

	private DataManager dataManager;

	private OnVolleyErrorCallback onVolleyErrorCallback;

	public static final Integer DIABLING_MINOR_VALUE = 1;

	public Menu() {
		instance = this;
		activityStack = new ArrayList<Activity>();
		responseTimeStatistics = new HashMap<String, Pair<String, Pair<String, String>>>();

		dataManager = new DataManager();

	}

	public static synchronized Context getContext() {
		if (instance == null)
			new Menu();
		return instance;
	}

	public static Menu getInstance() {
		if (instance == null)
			new Menu();
		return instance;
	}

	public static Context getMenuApplicationContext() {
		return context;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		this.context = getApplicationContext();
	}

	public void destroyActivity(int position) {
		try {
			activityStack.get(position).finish();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void destroyActivity(Activity activity) {
		try {
			activityStack.get(activityStack.indexOf(activity)).finish();
			activityStack.remove(activityStack.indexOf(activity));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addActivityToStack(Activity activity) {
		try {
			activityStack.add(activity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void popBackActivityStack() {
		try {
			activityStack.get(activityStack.size() - 1).finish();
			activityStack.remove(activityStack.size() - 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void destroyAllActivitiesFromStack() {
		try {
			for (Activity activity : activityStack) {
				activity.finish();
			}
			activityStack.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<String, Pair<String, Pair<String, String>>> getTimeResponses() {
		return this.responseTimeStatistics;
	}

	public DataManager getDataManager() {
		return dataManager;
	}

	public void registerOnVolleyErrorCallback(OnVolleyErrorCallback onVolleyErrorCallback) {
		this.onVolleyErrorCallback = onVolleyErrorCallback;
	}

	public void unregisterOnVolleyErrorCallback() {
		this.onVolleyErrorCallback = null;
	}

	public void onVolleyErrorReceived(VolleyError error) {

		if (onVolleyErrorCallback != null)
			onVolleyErrorCallback.onVolleyError(error);
	}

	public boolean isOrderEnabled() {

		if ((int) Integer.valueOf(Settings.getMinor(Menu.getContext())) == DIABLING_MINOR_VALUE)
			return false;
		else
			return true;
	}
}