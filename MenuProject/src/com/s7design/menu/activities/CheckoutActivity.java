package com.s7design.menu.activities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.s7design.menu.R;

public class CheckoutActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkout);
		initViews();
	}

	private void initViews() {
		
		setActionBarForwardArrowVisibility(null);
		setActionBarForwardButtonTextColor(getResources().getColor(R.color.menu_main_orange));
		setActionBarForwardButtonText(R.string.action_bar_order_summary);
		
		setActionBarBackButtonOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		setActionBarForwardButtonOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Will intent to order summary activity", Toast.LENGTH_SHORT).show();
			}
		});
		
	}
	
}
