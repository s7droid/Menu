package com.s7design.menu.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.TextView;

import com.s7design.menu.R;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tutorial_screen_first);
		initViews();
	}

	/**
	 * Method for initializing all views in {@link SplashSActivity}
	 */
	private void initViews() {

//		mTitleText = (TextView) findViewById(R.id.textviewSplashActivityMenuTitle);
//
//		String normalBefore= "First Part Not Bold ";
//		String normalBOLD = "BOLD ";
//		String normalAfter = "rest not bold";
//		String finalString = normalBefore + normalBOLD + normalAfter;
//		Spannable sb = new SpannableString(finalString);
//		sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), finalString.indexOf(normalBOLD), normalBOLD.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // bold
//
//		mTitleText.setText(finalString);

	}

}
