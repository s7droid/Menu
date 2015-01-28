package com.s7design.menu.activities;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.s7design.menu.R;
import com.s7design.menu.app.Menu;
import com.s7design.menu.dialogs.OkCancelDialogFragment;
import com.s7design.menu.utils.Settings;
import com.s7design.menu.utils.Utils;
import com.s7design.menu.volley.VolleySingleton;
import com.s7design.menu.volley.requests.GetBraintreeTokenRequest;
import com.s7design.menu.volley.requests.GetCurrencyRequest;
import com.s7design.menu.volley.requests.GetDiscountRequest;
import com.s7design.menu.volley.requests.GetRestaurantInfoRequest;
import com.s7design.menu.volley.requests.GetTaxRateRequest;
import com.s7design.menu.volley.requests.GetTipRequest;
import com.s7design.menu.volley.responses.GetBraintreeTokenResponse;
import com.s7design.menu.volley.responses.GetCurrencyResponse;
import com.s7design.menu.volley.responses.GetDiscountResponse;
import com.s7design.menu.volley.responses.GetRestaurantInfoResponse;
import com.s7design.menu.volley.responses.GetTaxRateResponse;
import com.s7design.menu.volley.responses.GetTipResponse;

/**
 * Splash screen activity used for getting data from server, such are connection
 * to Bluetooth device, and gathering all other data needed for application to
 * work. <br>
 * Also, within this activity, connection on Internet is checked.
 * 
 * @author s7Design
 *
 */
public class SplashActivity extends BaseActivity implements BeaconConsumer, LeScanCallback {

	private static final String TAG = SplashActivity.class.getSimpleName();

	public static final int REQUEST_CODE_LOCATION = 1;
	public static final int REQUEST_CODE_BLUETOOTH = 2;

	// VIEWS
	private TextView mTitleText;

	// DATA
	private static final int SPLASH_SCREEN_TIMEOUT = 10000;

	private BroadcastReceiver bluetoothReceiver;

	private BeaconManager beaconManager;
	private BluetoothAdapter bluetoothAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		hideActionBar();
		initViews();
		doChecks();

		// new AsyncTask<Void, Void, Void>() {
		//
		// @Override
		// protected Void doInBackground(Void... pars) {
		//
		// final CountDownLatch countDownLatch = new CountDownLatch(6);
		//
		// Map<String, String> params = new HashMap<String, String>();
		// params.put("major", Menu.getInstance().getDataManager().getMajor());
		// params.put("minor", Menu.getInstance().getDataManager().getMinor());
		//
		// GetRestaurantInfoRequest restaurantInfoRequest = new
		// GetRestaurantInfoRequest(SplashActivity.this, params, new
		// Listener<GetRestaurantInfoResponse>() {
		//
		// @Override
		// public void onResponse(GetRestaurantInfoResponse restaurantInfo) {
		//
		// Menu.getInstance().getDataManager().setRestaurantInfo(restaurantInfo);
		// countDownLatch.countDown();
		// }
		// });
		//
		// GetTaxRateRequest taxRequest = new
		// GetTaxRateRequest(SplashActivity.this, params, new
		// Listener<GetTaxRateResponse>() {
		//
		// @Override
		// public void onResponse(GetTaxRateResponse taxRate) {
		//
		// Menu.getInstance().getDataManager().setTaxRate(taxRate.rate[0].tax);
		// countDownLatch.countDown();
		// }
		// });
		//
		// GetTipRequest tipRequest = new GetTipRequest(SplashActivity.this,
		// params, new Listener<GetTipResponse>() {
		//
		// @Override
		// public void onResponse(GetTipResponse tipRate) {
		//
		// Menu.getInstance().getDataManager().setTipRate(tipRate.rate[0].mintip,
		// tipRate.rate[0].maxtip);
		// countDownLatch.countDown();
		// }
		// });
		//
		// GetDiscountRequest discountRequest = new
		// GetDiscountRequest(SplashActivity.this, params, new
		// Listener<GetDiscountResponse>() {
		//
		// @Override
		// public void onResponse(GetDiscountResponse discount) {
		//
		// Menu.getInstance().getDataManager().setDiscount(discount.discount);
		// countDownLatch.countDown();
		// }
		// });
		//
		// GetCurrencyRequest currencyRequest = new
		// GetCurrencyRequest(SplashActivity.this, params, new
		// Listener<GetCurrencyResponse>() {
		//
		// @Override
		// public void onResponse(GetCurrencyResponse currency) {
		//
		// Menu.getInstance().getDataManager().setCurrency(currency.currency);
		// countDownLatch.countDown();
		// }
		// });
		//
		// GetBraintreeTokenRequest tokenRequest = new
		// GetBraintreeTokenRequest(SplashActivity.this, null, new
		// Listener<GetBraintreeTokenResponse>() {
		//
		// @Override
		// public void onResponse(GetBraintreeTokenResponse token) {
		//
		// Menu.getInstance().getDataManager().setClientBraintreeToken(token.token);
		// countDownLatch.countDown();
		// }
		// });
		//
		// VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(restaurantInfoRequest);
		// VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(taxRequest);
		// VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(tipRequest);
		// VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(discountRequest);
		// VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(currencyRequest);
		// VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(tokenRequest);
		//
		// try {
		// countDownLatch.await(20000, TimeUnit.MILLISECONDS);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		//
		// return null;
		// }
		//
		// protected void onPostExecute(Void result) {
		//
		// String accessToken =
		// Settings.getAccessToken(getApplicationContext());
		//
		// // if (accessToken.length() > 0) {
		// // Intent i = new Intent(SplashActivity.this,
		// RestaurantPreviewActivity.class);
		// // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
		// Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// // startActivity(i);
		// // } else {
		// // Intent i = new Intent(SplashActivity.this,
		// MainMenuActivity.class);
		// // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
		// Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// // startActivity(i);
		// // }
		//
		// String major = Menu.getInstance().getDataManager().getMajor();
		// String minor = Menu.getInstance().getDataManager().getMinor();
		//
		// if((major != null && minor != null)){
		// Intent i = new Intent(SplashActivity.this,
		// RestaurantPreviewActivity.class);
		// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
		// Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// startActivity(i);
		// }else if((major == null || minor == null)){
		// Intent i = new Intent(SplashActivity.this, MainMenuActivity.class);
		// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
		// Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// startActivity(i);
		// }
		//
		// };
		// }.execute();
	}

	private void doChecks() {

		if (!Utils.isNetworkAvailable(this)) {
			showAlertDialog(R.string.dialog_title_error, R.string.dialog_no_internet_connection, new OnClickListener() {

				@Override
				public void onClick(View v) {
					finish();

				}
			});
		} else if (!Utils.isLocationEnabled(this)) {

			Intent intent = new Intent(this, SplashWarningActivity.class);
			intent.putExtra(SplashWarningActivity.INTENT_EXTRA_TAG_START, SplashWarningActivity.INTENT_EXTRA_START_LOCATION);
			startActivityForResult(intent, REQUEST_CODE_LOCATION);

		} else if (!Utils.isBluetoothEnabled()) {

			Intent intent = new Intent(this, SplashWarningActivity.class);
			intent.putExtra(SplashWarningActivity.INTENT_EXTRA_TAG_START, SplashWarningActivity.INTENT_EXTRA_START_BLUETOOTH);
			startActivityForResult(intent, REQUEST_CODE_BLUETOOTH);

			// OkCancelDialogFragment okCancelDialog = new
			// OkCancelDialogFragment();
			// okCancelDialog.showDialog(getFragmentManager(),
			// getString(R.string.dialog_title_error),
			// getString(R.string.dialog_bluetooth_off), new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			//
			// IntentFilter filter = new
			// IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
			// bluetoothReceiver = new BroadcastReceiver() {
			// @Override
			// public void onReceive(Context context, Intent intent) {
			// final String action = intent.getAction();
			//
			// if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
			// final int state =
			// intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
			// BluetoothAdapter.ERROR);
			// switch (state) {
			// case BluetoothAdapter.STATE_OFF:
			// Log.d(TAG, "off");
			// break;
			// case BluetoothAdapter.STATE_TURNING_OFF:
			// Log.d(TAG, "turning off");
			// break;
			// case BluetoothAdapter.STATE_ON:
			// dismissProgressDialog();
			// initViews();
			// initData();
			// Log.d(TAG, "on");
			// break;
			// case BluetoothAdapter.STATE_TURNING_ON:
			// Log.d(TAG, "turning on");
			// break;
			// }
			// }
			// }
			// };
			// registerReceiver(bluetoothReceiver, filter);
			//
			// showProgressDialog(R.string.dialog_please_wait);
			//
			// BluetoothAdapter.getDefaultAdapter().enable();
			// }
			// }, new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// finish();
			// }
			// });
		} else {

			initData();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_CODE_LOCATION) {
			if (!Utils.isLocationEnabled(this)) {
				finish();
			} else {
				if (!Utils.isBluetoothEnabled()) {
					Intent intent = new Intent(this, SplashWarningActivity.class);
					intent.putExtra(SplashWarningActivity.INTENT_EXTRA_TAG_START, SplashWarningActivity.INTENT_EXTRA_START_BLUETOOTH);
					startActivityForResult(intent, REQUEST_CODE_BLUETOOTH);
				} else {
					initData();
				}
			}
		} else if (requestCode == REQUEST_CODE_BLUETOOTH) {
			if (!Utils.isBluetoothEnabled()) {
				finish();
			} else {
				if (!Utils.isLocationEnabled(this)) {
					Intent intent = new Intent(this, SplashWarningActivity.class);
					intent.putExtra(SplashWarningActivity.INTENT_EXTRA_TAG_START, SplashWarningActivity.INTENT_EXTRA_START_LOCATION);
					startActivityForResult(intent, REQUEST_CODE_LOCATION);

				} else {
					initData();
				}
			}
		}
	}

	/**
	 * Method for initializing all views in {@link SplashSActivity}
	 */
	private void initViews() {

		mTitleText = (TextView) findViewById(R.id.textviewSplashActivityMenuTitle);

		String finalString = getResources().getString(R.string.splash_screen_welcome_message);
		Spannable sb = new SpannableString(finalString);
		System.out.println("finalstring.lenght= " + finalString.length());
		System.out.println("lenght - 4=" + (finalString.length() - 4));
		sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), finalString.length() - 4, finalString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // bold

		mTitleText.setText(sb);
	}

	private void initData() {

		beaconManager = BeaconManager.getInstanceForApplication(this);
		beaconManager.bind(this);
		BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		bluetoothAdapter = bluetoothManager.getAdapter();

		searchForIBeacon();
	}

	private int searchAtempts = 2;
	Handler handler = new Handler();
	private Runnable runnable = new Runnable() {
		public void run() {

			bluetoothAdapter.stopLeScan(SplashActivity.this);
			dismissProgressDialog();

			if (searchAtempts > 0) {
				--searchAtempts;
				OkCancelDialogFragment dialog = new OkCancelDialogFragment();
				dialog.showDialog(getFragmentManager(), "", getString(R.string.dialog_no_beacon_found), new OnClickListener() {

					@Override
					public void onClick(View v) {
						showProgressDialogLoading();
						searchForIBeacon();

					}
				}, new OnClickListener() {

					@Override
					public void onClick(View v) {
						handler.removeCallbacks(runnable);
						startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
						finish();
					}
				});
			} else {
				handler.removeCallbacks(runnable);
				startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
				finish();
			}

		}
	};

	private void searchForIBeacon() {

		bluetoothAdapter.startLeScan(this);

		handler.postDelayed(runnable, SPLASH_SCREEN_TIMEOUT);
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (bluetoothReceiver != null)
			unregisterReceiver(bluetoothReceiver);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (beaconManager != null)
			beaconManager.unbind(this);
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
		dismissProgressDialog();
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
			String uuid = hexString.substring(0, 8) + "-" + hexString.substring(8, 12) + "-" + hexString.substring(12, 16) + "-" + hexString.substring(16, 20) + "-" + hexString.substring(20, 32);
			// Here is your Major value
			int major = (scanRecord[startByte + 20] & 0xff) * 0x100 + (scanRecord[startByte + 21] & 0xff);
			// Here is your Minor value
			int minor = (scanRecord[startByte + 22] & 0xff) * 0x100 + (scanRecord[startByte + 23] & 0xff);

			Log.d(TAG, "major " + major);
			Log.d(TAG, "minor " + minor);

			Menu.getInstance().getDataManager().setMajor(String.valueOf(major));
			Menu.getInstance().getDataManager().setMinor(String.valueOf(minor));

			new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... pars) {

					final CountDownLatch countDownLatch = new CountDownLatch(6);

					Map<String, String> params = new HashMap<String, String>();
					params.put("major", Menu.getInstance().getDataManager().getMajor());
					params.put("minor", Menu.getInstance().getDataManager().getMinor());

					GetRestaurantInfoRequest restaurantInfoRequest = new GetRestaurantInfoRequest(SplashActivity.this, params, new Listener<GetRestaurantInfoResponse>() {

						@Override
						public void onResponse(GetRestaurantInfoResponse restaurantInfo) {

							Menu.getInstance().getDataManager().setRestaurantInfo(restaurantInfo);
							countDownLatch.countDown();
						}
					});

					GetTaxRateRequest taxRequest = new GetTaxRateRequest(SplashActivity.this, params, new Listener<GetTaxRateResponse>() {

						@Override
						public void onResponse(GetTaxRateResponse taxRate) {

							Menu.getInstance().getDataManager().setTaxRate(taxRate.rate[0].tax);
							countDownLatch.countDown();
						}
					});

					GetTipRequest tipRequest = new GetTipRequest(SplashActivity.this, params, new Listener<GetTipResponse>() {

						@Override
						public void onResponse(GetTipResponse tipRate) {

							Menu.getInstance().getDataManager().setTipRate(tipRate.rate[0].mintip, tipRate.rate[0].maxtip);
							countDownLatch.countDown();
						}
					});

					GetDiscountRequest discountRequest = new GetDiscountRequest(SplashActivity.this, params, new Listener<GetDiscountResponse>() {

						@Override
						public void onResponse(GetDiscountResponse discount) {

							Menu.getInstance().getDataManager().setDiscount(discount.discount);
							countDownLatch.countDown();
						}
					});

					GetCurrencyRequest currencyRequest = new GetCurrencyRequest(SplashActivity.this, params, new Listener<GetCurrencyResponse>() {

						@Override
						public void onResponse(GetCurrencyResponse currency) {

							Menu.getInstance().getDataManager().setCurrency(currency.currency);
							countDownLatch.countDown();
						}
					});

					GetBraintreeTokenRequest tokenRequest = new GetBraintreeTokenRequest(SplashActivity.this, null, new Listener<GetBraintreeTokenResponse>() {

						@Override
						public void onResponse(GetBraintreeTokenResponse token) {

							Menu.getInstance().getDataManager().setClientBraintreeToken(token.token);
							countDownLatch.countDown();
						}
					});

					VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(restaurantInfoRequest);
					VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(taxRequest);
					VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(tipRequest);
					VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(discountRequest);
					VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(currencyRequest);
					VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(tokenRequest);

					try {
						countDownLatch.await(20000, TimeUnit.MILLISECONDS);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					return null;
				}

				protected void onPostExecute(Void result) {

					String accessToken = Settings.getAccessToken(getApplicationContext());

					// if (accessToken.length() > 0) {
					// Intent i = new Intent(SplashActivity.this,
					// RestaurantPreviewActivity.class);
					// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
					// Intent.FLAG_ACTIVITY_CLEAR_TASK |
					// Intent.FLAG_ACTIVITY_CLEAR_TOP);
					// startActivity(i);
					// } else {
					// Intent i = new Intent(SplashActivity.this,
					// MainMenuActivity.class);
					// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
					// Intent.FLAG_ACTIVITY_CLEAR_TASK |
					// Intent.FLAG_ACTIVITY_CLEAR_TOP);
					// startActivity(i);
					// }

					String major = Menu.getInstance().getDataManager().getMajor();
					String minor = Menu.getInstance().getDataManager().getMinor();

					if ((major != null && minor != null)) {
						Intent i = new Intent(SplashActivity.this, RestaurantPreviewActivity.class);
						i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(i);
					} else if ((major == null || minor == null)) {
						Intent i = new Intent(SplashActivity.this, MainMenuActivity.class);
						i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(i);
					}

				};
			}.execute();
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

}
