package com.s7design.menu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.s7design.menu.R;

/**
 * Welcome activity. This activity contains data about current restaurant. From
 * this activity user can go to tutorials, or see menu of the restaurant, and
 * make an order. <br>
 * In order to enter this activity, user first must satisfied several
 * conditions, such are: Internet and bluetooth connection enabled, and some
 * several conditions, which are checked in {@link SplashActivity}.
 * 
 * @author s7Design
 *
 */
public class RestaurantPreviewActivity extends BaseActivity {

	// VIEWS
	private Button mMakeOrderButton;
	private TextView mRestaurantName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_preview);
		initViews();
	}

	/**
	 * Method for initializing all views in {@link RestaurantPreviewActivity}.
	 */
	private void initViews() {

		mMakeOrderButton = (Button) findViewById(R.id.buttonRestaurantPreviewActivityMakeOrder);

		
		setActionBarBackButtonVisibility(View.INVISIBLE);
		setActionBarForwardButtonText(R.string.action_bar_tutorial);
		mMakeOrderButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(RestaurantPreviewActivity.this, CategoryMealsActivity.class));
			}
		});

		setActionBarForwardButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(RestaurantPreviewActivity.this, TutorialFirstActivity.class));
			}
		});
	}

}
