package com.s7design.menu.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;

import android.app.Activity;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;
import android.os.RemoteException;
import android.support.v4.util.Pair;
import android.util.Log;

import com.android.volley.VolleyError;
import com.s7design.menu.callbacks.OnIBeaconSearchResultCallback;
import com.s7design.menu.callbacks.OnVolleyErrorCallback;
import com.s7design.menu.dataclasses.DataManager;
import com.s7design.menu.volley.responses.GsonResponse;

//@ReportsCrashes(formUri = "", mailTo = "comajlo@gmail.com" /* my email here */)
public class Menu extends Application implements BeaconConsumer, LeScanCallback, OnIBeaconSearchResultCallback {

	private final String TAG = Menu.class.getSimpleName();

	private static Menu instance;

	private List<Activity> activityStack;
	private Map<String, Pair<String, Pair<String, String>>> responseTimeStatistics;

	private static Context context;

	private DataManager dataManager;

	private OnVolleyErrorCallback onVolleyErrorCallback;

	public static final Integer DISABLING_MINOR_VALUE = 0;

	private static final int SPLASH_SCREEN_TIMEOUT = 10000;

	private static final int SCAN_PERIOD = 300000;

	private BeaconManager beaconManager;
	private BluetoothAdapter bluetoothAdapter;

	private Timer timer;

	public Menu() {
		instance = this;
		activityStack = new ArrayList<Activity>();
		responseTimeStatistics = new HashMap<String, Pair<String, Pair<String, String>>>();

		dataManager = new DataManager();

		timer = new Timer();
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

		beaconManager = BeaconManager.getInstanceForApplication(this);
		beaconManager.bind(this);
		BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		bluetoothAdapter = bluetoothManager.getAdapter();

//		ACRA.init(this);

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

	public void onResponseErrorReceived(GsonResponse response) {

		if (onVolleyErrorCallback != null)
			onVolleyErrorCallback.onResponseError(response);
	}

	public void onVolleyErrorReceived(VolleyError error) {

		if (onVolleyErrorCallback != null)
			onVolleyErrorCallback.onVolleyError(error);
	}

	public boolean isOrderEnabled() {

		if ((int) Integer.valueOf(dataManager.getMinor(getApplicationContext())) == DISABLING_MINOR_VALUE)
			return false;
		else
			return true;
	}

	public boolean isInARestaurant() {

		return dataManager.getMinor(getApplicationContext()).length() > 0 || dataManager.getMajor(getApplicationContext()).length() > 0;
	}

	Handler handler = new Handler();
	private Runnable runnable = new Runnable() {
		public void run() {

			bluetoothAdapter.stopLeScan(Menu.this);
			onIBeaconSearchResult(OnIBeaconSearchResultCallback.SEARCH_RESULT_BEACON_NOT_FOUND);
			handler.removeCallbacks(runnable);
		}
	};

	private OnIBeaconSearchResultCallback onIBeaconSearchResultCallback;

	private TimerTask timerTask;

	public void searchForIBeacon(OnIBeaconSearchResultCallback callback) {

		onIBeaconSearchResultCallback = callback;

		bluetoothAdapter.startLeScan(this);
		handler.postDelayed(runnable, SPLASH_SCREEN_TIMEOUT);

		if (timerTask != null) {
			timer.cancel();
			timer = new Timer();
		}

		timerTask = new TimerTask() {

			@Override
			public void run() {

				Log.w(TAG, "timer run!!!");

				bluetoothAdapter.startLeScan(Menu.this);
				handler.postDelayed(runnable, SPLASH_SCREEN_TIMEOUT);
			}
		};

		try {
			timer.schedule(timerTask, SCAN_PERIOD, SCAN_PERIOD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onBeaconServiceConnect() {
		beaconManager.setMonitorNotifier(new MonitorNotifier() {
			@Override
			public void didEnterRegion(Region region) {
				Log.i(TAG, "I just saw a beacon for the first time!");
			}

			@Override
			public void didExitRegion(Region region) {
				Log.i(TAG, "I no longer see a beacon");
			}

			@Override
			public void didDetermineStateForRegion(int state, Region region) {
				Log.i(TAG, "I have just switched from seeing/not seeing beacons: " + state);
			}
		});

		try {
			beaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));
		} catch (RemoteException e) {
		}
	}

	@Override
	public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {

		Log.d(TAG, "onLeScan");
		bluetoothAdapter.stopLeScan(this);
		handler.removeCallbacks(runnable);

		int startByte = 2;
		boolean patternFound = false;
		while (startByte <= 5) {
			if (((int) scanRecord[startByte + 2] & 0xff) == 0x02 && // Identifies
																	// an
																	// iBeacon
					((int) scanRecord[startByte + 3] & 0xff) == 0x15) { // Identifies
																		// correct
																		// data
																		// length
				patternFound = true;
				break;
			}
			startByte++;
		}
		if (patternFound) {
			// Convert to hex String
			byte[] uuidBytes = new byte[16];
			System.arraycopy(scanRecord, startByte + 4, uuidBytes, 0, 16);
			String hexString = bytesToHex(uuidBytes);
			// Here is your UUID
			String uuid = hexString.substring(0, 8) + "-" + hexString.substring(8, 12) + "-" + hexString.substring(12, 16) + "-"
					+ hexString.substring(16, 20) + "-" + hexString.substring(20, 32);
			// Here is your Major value
			int major = (scanRecord[startByte + 20] & 0xff) * 0x100 + (scanRecord[startByte + 21] & 0xff);
			// Here is your Minor value
			int minor = (scanRecord[startByte + 22] & 0xff) * 0x100 + (scanRecord[startByte + 23] & 0xff);

			Log.d(TAG, "major " + major);
			Log.d(TAG, "minor " + minor);

			Menu.getInstance().getDataManager().setMajor(getApplicationContext(), String.valueOf(major));
			Menu.getInstance().getDataManager().setMinor(getApplicationContext(), String.valueOf(minor));

			onIBeaconSearchResult(OnIBeaconSearchResultCallback.SEARCH_RESULT_BEACON_FOUND);
		}
	}

	static final char[] hexArray = "0123456789ABCDEF".toCharArray();

	private static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	@Override
	public void onIBeaconSearchResult(int result) {

		if (result == OnIBeaconSearchResultCallback.SEARCH_RESULT_BEACON_FOUND) {

		} else if (result == OnIBeaconSearchResultCallback.SEARCH_RESULT_BEACON_NOT_FOUND) {

			Menu.getInstance().getDataManager().setMajor(getApplicationContext(), String.valueOf(""));
			Menu.getInstance().getDataManager().setMinor(getApplicationContext(), String.valueOf(""));
		}

		if (onIBeaconSearchResultCallback != null) {
			onIBeaconSearchResultCallback.onIBeaconSearchResult(result);
			onIBeaconSearchResultCallback = null;
		}
	}

}
