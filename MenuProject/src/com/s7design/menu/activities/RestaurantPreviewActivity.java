package com.s7design.menu.activities;

import com.s7design.menu.R;
import com.s7design.menu.app.Menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

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

	//VIEWS
	private Button mMakeOrderButton;
	private Button mActionBarTutorial;
	private TextView mRestaurantName;
	private Toolbar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_preview);
		actionBar = (Toolbar) findViewById(R.id.actionBar);
		setSupportActionBar(actionBar);
		initViews();
	}

	/**
	 * Method for initializing all views in {@link RestaurantPreviewActivity}.
	 */
	private void initViews() {
		mActionBarTutorial = (Button) actionBar.findViewById(R.id.buttonActionBarForward);
		
		mMakeOrderButton = (Button) findViewById(R.id.buttonRestaurantPreviewActivityMakeOrder);
		mRestaurantName = (TextView) findViewById(R.id.textviewRestaurantPreviewActivityRestaurantName);
		
		((Button) actionBar.findViewById(R.id.buttonActionBarBack)).setVisibility(View.GONE);
		
		mActionBarTutorial.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RestaurantPreviewActivity.this, TutorialFirstActivity.class);
//				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});
		
		mMakeOrderButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(RestaurantPreviewActivity.this, CategoryMealsActivity.class));
			}
		});
	}

}
