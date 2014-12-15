package com.s7design.menu.activities;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.s7design.menu.R;

public class OrderMealsActivity extends BaseActivity {

	// VIEWS
	private ScrollView mGlobalContainer;
	private LinearLayout mContainer;

	// DATA
	private int counter = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_meals);

		initViews();
	}

	private void initViews() {

		mGlobalContainer = (ScrollView) findViewById(R.id.scrollviewOrderMealsActivityContainer);
		mContainer = (LinearLayout) findViewById(R.id.scrollviewOrderMealsActivityLinearContainer);

	}
}
