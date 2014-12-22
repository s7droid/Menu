package com.s7design.menu.activities;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.s7design.menu.utils.Utils;
import com.s7design.menu.volley.VolleySingleton;
import com.s7design.menu.volley.requests.GetCurrencyRequest;
import com.s7design.menu.volley.requests.GetDiscountRequest;
import com.s7design.menu.volley.requests.GetRestaurantInfoRequest;
import com.s7design.menu.volley.requests.GetTaxRateRequest;
import com.s7design.menu.volley.requests.GetTipRequest;
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

		mTitleText = (TextView) findViewById(R.id.textviewSplashActivityMenuTitle);

		String finalString = getResources().getString(R.string.splash_screen_welcome_message);
		Spannable sb = new SpannableString(finalString);
		System.out.println("finalstring.lenght= " + finalString.length());
		System.out.println("lenght - 4=" + (finalString.length() - 4));
		sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), finalString.length() - 4, finalString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // bold

		mTitleText.setText(sb);

		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... pars) {

				final CountDownLatch countDownLatch = new CountDownLatch(5);

				Map<String, String> params = new HashMap<String, String>();
				params.put("major", "1");
				params.put("minor", "1");

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

				VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(restaurantInfoRequest);
				VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(taxRequest);
				VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(tipRequest);
				VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(discountRequest);
				VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(currencyRequest);

				try {
					countDownLatch.await(20000, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				return null;
			}

			protected void onPostExecute(Void result) {

				Intent i = new Intent(SplashActivity.this, RestaurantPreviewActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			};
		}.execute();

	}

	@Override
	protected void onPause() {
		super.onPause();

		if (bluetoothReceiver != null)
			unregisterReceiver(bluetoothReceiver);
	}

}
