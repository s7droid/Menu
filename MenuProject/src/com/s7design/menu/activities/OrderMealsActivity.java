package com.s7design.menu.activities;

import com.s7design.menu.R;
import com.s7design.menu.app.Menu;
import com.s7design.menu.utils.CustomMenuMealCategorySubTypeExpandable;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class OrderMealsActivity extends BaseActivity {

	// VIEWS
	private ScrollView mGlobalContainer;
	private LinearLayout mContainer;
	private Toolbar actionBar;
	private Button mActionBarCheckout;
	private Button mActionBarBack;
	private ImageButton mActionBarMenuButton;

	//DATA
	private int counter = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_meals);

		actionBar = (Toolbar) findViewById(R.id.actionBar);
		setSupportActionBar(actionBar);
		initViews();
	}

	private void initViews() {
		
		mGlobalContainer = (ScrollView) findViewById(R.id.scrollviewOrderMealsActivityContainer);
		mContainer = (LinearLayout) findViewById(R.id.scrollviewOrderMealsActivityLinearContainer);
		
		mActionBarBack = (Button) actionBar.findViewById(R.id.buttonActionBarBack);
		mActionBarCheckout = (Button) actionBar.findViewById(R.id.buttonActionBarForward);
		mActionBarMenuButton = (ImageButton) actionBar.findViewById(R.id.imagebuttonActionBarMenu);
		
		mActionBarCheckout.setText(getResources().getString(R.string.txt_checkout_small));
		
		mActionBarBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		mActionBarCheckout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(Menu.getContext(), "Proceed to checkout.", Toast.LENGTH_SHORT).show();
			}
		});
		
		mActionBarMenuButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String[] meals = {"meal1","meal1","meal1","meal1","meal1","meal1","meal1","meal1","meal1","meal1","meal1","meal1"};
				CustomMenuMealCategorySubTypeExpandable meal = new CustomMenuMealCategorySubTypeExpandable(Menu.getContext(), "Example", meals);
				mContainer.addView(meal,counter);
				counter++;
			}
		});
	}
}
