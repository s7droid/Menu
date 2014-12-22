package com.s7design.menu.activities;

import java.util.HashMap;
import java.util.Map;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.s7design.menu.R;
import com.s7design.menu.app.Menu;
import com.s7design.menu.dialogs.OkCancelDialogFragment;
import com.s7design.menu.utils.Utils;
import com.s7design.menu.volley.VolleySingleton;
import com.s7design.menu.volley.requests.GetRestaurantInfoRequest;
import com.s7design.menu.volley.responses.GetRestaurantInfoResponse;

/**
 * Splash screen activity used for getting data from server, such are connection
 * to Bluetooth device, and gathering all other data needed for application to
 * work. <br>
 * Also, within this activity, connection on Internet is checked.
 * 
 * @author s7Design
 *
 */
public class SplashActivity extends BaseActivity {

	private static final String TAG = SplashActivity.class.getSimpleName();

	// VIEWS
	private TextView mTitleText;

	// DATA
	private static final int SPLASH_SCREEN_TIMEOUT = 1000;

	private BroadcastReceiver bluetoothReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		hideActionBar();

		if (!Utils.isNetworkAvailable(this)) {
			showAlertDialog(R.string.dialog_title_error, R.string.dialog_no_internet_connection, new OnClickListener() {

				@Override
				public void onClick(View v) {
					finish();

				}
			});
		} else if (!Utils.isBluetoothEnabled()) {

			OkCancelDialogFragment okCancelDialog = new OkCancelDialogFragment();
			okCancelDialog.showDialog(getFragmentManager(), getString(R.string.dialog_title_error), getString(R.string.dialog_bluetooth_off), new OnClickListener() {

				@Override
				public void onClick(View v) {

					IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
					bluetoothReceiver = new BroadcastReceiver() {
						@Override
						public void onReceive(Context context, Intent intent) {
							final String action = intent.getAction();

							if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
								final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
								switch (state) {
								case BluetoothAdapter.STATE_OFF:
									Log.d(TAG, "off");
									break;
								case BluetoothAdapter.STATE_TURNING_OFF:
									Log.d(TAG, "turning off");
									break;
								case BluetoothAdapter.STATE_ON:
									dismissProgressDialog();
									initViews();
									Log.d(TAG, "on");
									break;
								case BluetoothAdapter.STATE_TURNING_ON:
									Log.d(TAG, "turning on");
									break;
								}
							}
						}
					};
					registerReceiver(bluetoothReceiver, filter);

					showProgressDialog(R.string.dialog_please_wait);

					BluetoothAdapter.getDefaultAdapter().enable();
				}
			}, new OnClickListener() {

				@Override
				public void onClick(View v) {
					finish();
				}
			});
		} else {
			initViews();
		}

	}

	/**
	 * Method for initializing all views in {@link SplashSActivity}
	 */
	private void initViews() {

		// mTitleText = (TextView)
		// findViewById(R.id.textviewSplashActivityMenuTitle);
		//
		// String normalBefore= "First Part Not Bold ";
		// String normalBOLD = "BOLD ";
		// String normalAfter = "rest not bold";
		// String finalString = normalBefore + normalBOLD + normalAfter;
		// Spannable sb = new SpannableString(finalString);
		// sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
		// finalString.indexOf(normalBOLD), normalBOLD.length(),
		// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // bold
		//
		// mTitleText.setText(finalString);

		Map<String, String> params = new HashMap<String, String>();
		params.put("major", "1");
		params.put("minor", "1");

		GetRestaurantInfoRequest request = new GetRestaurantInfoRequest(this, params, new Listener<GetRestaurantInfoResponse>() {

			@Override
			public void onResponse(GetRestaurantInfoResponse restaurantInfo) {

				Menu.getInstance().getDataManager().setRestaurantInfo(restaurantInfo);

				Intent i = new Intent(SplashActivity.this, RestaurantPreviewActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});

		VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (bluetoothReceiver != null)
			unregisterReceiver(bluetoothReceiver);
	}

}
