package com.s7design.menu.activities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.s7design.menu.R;

public class PickupInfoActivity extends BaseActivity {

	// VIEWS
	private Button mButtonConfirm;;
	private EditText mEditTextCountryCode; 
	private EditText mEditTextPhoneNumber;
	
	// DATA

	// CONTROLLERS

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pickup_info);
		initActionBar();
		initViews();
	}

	private void initActionBar() {
		setActionBarForwardArrowVisibility(null);
		setActionBarForwardButtonTextColor(getResources().getColor(R.color.menu_main_orange));
		setActionBarForwardButtonText(getResources().getString(R.string.action_bar_pickup_info));

		setActionBarMenuButtonVisibility(View.GONE);

		setActionBarBackButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	private void initViews() {
		mButtonConfirm = (Button) findViewById(R.id.buttonPickupInfoActivityConfirm);
		mEditTextCountryCode = (EditText) findViewById(R.id.edittextPickupInfoActivityCountryCodeValue);
		mEditTextPhoneNumber = (EditText) findViewById(R.id.edittextPickupInfoActivityPhoneNumberValue);
	}

}
