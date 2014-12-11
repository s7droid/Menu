package com.s7droid.menu.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.TextView;

import com.s7design.menu.R;
import com.s7droid.menu.app.Menu;

/**
 * Splash screen activity used for getting data from server, such are connection
 * to Bluetooth device, and gathering all other data needed for application to
 * work. <br>
 * Also, within this activity, connection on Internet is checked.
 * 
 * @author s7Design
 *
 */
public class SplashActivity extends Activity {

	// VIEWS
	private TextView mTitleText;

	// DATA
	private static final int SPLASH_SCREEN_TIMEOUT = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		initViews();
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

		new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

			@Override
			public void run() {
				// This method will be executed once the timer is over
				// Start your app main activity
				Intent i = new Intent(SplashActivity.this, RestaurantPreviewActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);

				// close this activity
				finish();
			}
		}, SPLASH_SCREEN_TIMEOUT);

	}

}
