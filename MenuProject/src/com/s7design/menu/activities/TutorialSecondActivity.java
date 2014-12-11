package com.s7design.menu.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.s7design.menu.R;

/**
 * Activity for showing second screen of the two screens tutorial. <br>
 * This activity is response for completing tutorial, and for transfering user
 * to
 * 
 * @author s7Design
 *
 */
public class TutorialSecondActivity extends Activity {

	// VIEWS
	private Button mMakeOrder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tutorial_screen_second);
		initViews();
	}

	/**
	 * Method for initializing all views in {@link TutorialSecondActivity}
	 */
	private void initViews() {
		mMakeOrder = (Button) findViewById(R.id.buttonuTutorialScreenSecondContinueButton);

		mMakeOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), CategoryMealsActivity.class);
				// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
				// Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});
	}
}
