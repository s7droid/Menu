package com.s7design.menu.activities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.s7design.menu.R;

public class AboutTheAppActivity extends BaseActivity {

	// VIEWS
	private Button mButtonTermsOfService;
	private Button mButtonAcknowledgements;
	private LinearLayout mLinearLayoutTOSContainer;
	private LinearLayout mLinearLayoutAknwoledgementsContainer;

	// CONTROLLERS
	private boolean isAknwoledgementVisible = false;
	private boolean isTOSVisible = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_menu);
		initActionBar();
		initViews();
	}

	private void initActionBar() {

		setActionBarForwardArrowVisibility(null);
		setActionBarForwardButtonText(getResources().getString(R.string.action_bar_about_the_app));
		setActionBarForwardButtonTextColor(getResources().getColor(R.color.menu_main_orange));

		setActionBarBackButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void initViews() {

		mButtonAcknowledgements = (Button) findViewById(R.id.buttonAboutMenuActivityAknowledgements);
		mButtonTermsOfService = (Button) findViewById(R.id.buttonAboutMenuActivityTermsOfUse);
		mLinearLayoutAknwoledgementsContainer = (LinearLayout) findViewById(R.id.linearlayoutAboutMenuActivityAknowledgementsContainer);
		mLinearLayoutTOSContainer = (LinearLayout) findViewById(R.id.linearlayoutAboutMenuActivityTermsOfUseContainer);

		mButtonAcknowledgements.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!isAknwoledgementVisible)
					mLinearLayoutAknwoledgementsContainer.setVisibility(View.GONE);
				else
					mLinearLayoutAknwoledgementsContainer.setVisibility(View.VISIBLE);

				isAknwoledgementVisible = !isAknwoledgementVisible;
			}
		});

		mButtonTermsOfService.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!isTOSVisible)
					mLinearLayoutTOSContainer.setVisibility(View.GONE);
				else
					mLinearLayoutTOSContainer.setVisibility(View.VISIBLE);

				isTOSVisible = !isTOSVisible;
			}
		});

	}

}
