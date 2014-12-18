package com.s7design.menu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.s7design.menu.R;
import com.s7design.menu.app.Menu;
import com.s7design.menu.utils.CustomMenuMealCategorySubTypeExpandable;

public class OrderMealsActivity extends BaseActivity {

	// VIEWS
	private ScrollView mGlobalContainer;
	private LinearLayout mContainer;

	// DATA
	private int counter = 0;
	private String[] titles = {"Soups", "Main dishes", "Salads"};
	private String[] meals = {"Meal 1", "Meal 2", "Meal 3", "Meal 4"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_meals);

		initViews();
		initData();
	}

	private void initViews() {

		mGlobalContainer = (ScrollView) findViewById(R.id.scrollviewOrderMealsActivityContainer);
		mContainer = (LinearLayout) findViewById(R.id.scrollviewOrderMealsActivityLinearContainer);
		
		setActionBarForwardButtonText(R.string.action_bar_checkout);
		
		setActionBarBackButtonOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		setActionBarForwardButtonOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(Menu.getInstance().getDataManager().getTestCheckoutList() != null)
					startActivity(new Intent(getApplicationContext(), CheckoutActivity.class));
				else
					showAlertDialog("Alert", "Your checkout list is empty. Add some things to Your chart.");
			}
		});
		
	}
	
	private void initData() {
		for (int i = 0; i < 3; i++) {
			CustomMenuMealCategorySubTypeExpandable item = new CustomMenuMealCategorySubTypeExpandable(OrderMealsActivity.this, titles[i], meals);
			mContainer.addView(item);
		}		
	}
	
	
}
