package com.s7design.menu.activities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.s7design.menu.R;

public class AboutTheAppActivity extends BaseActivity {

	// VIEWS
	private Button mButtonTermsOfService;
	private Button mButtonAcknowledgements;
	private Button mButtonPrivacyTerms;
	private LinearLayout mLinearLayoutTOSContainer;
	private LinearLayout mLinearLayoutAknwoledgementsContainer;
	private LinearLayout mLinearLayoutPrivacyTerms;
	private WebView mWebViewTermsOfService;
	private WebView mWebViewAknowledgements;
	private WebView mWebViewPrivacyTerms;

	// CONTROLLERS
	private boolean isAknwoledgementVisible = false;
	private boolean isTOSVisible = false;
	private boolean isPrivacyTermsVisible = false;

	// DATA
	private String HTTP_ADDRESS_AKNOWLEDGEMENTS = "http://usemenu.com/app_html/acknowledgements.html";
	private String HTTP_ADDRESS_PRIVACY_TERMS = "http://usemenu.com/app_html/privacypolicy.html";
	private String HTTP_ADDRESS_TERMS_OF_SERVICE = "http://usemenu.com/app_html/termsofservice.html";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_menu);
		initActionBar();
		initViews();
		initData();
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

	private void initData() {
		loadAknowledgements();
		loadPrivacyTerms();
		loadTermsOfService();
	}

	private void initViews() {

		mButtonAcknowledgements = (Button) findViewById(R.id.buttonAboutMenuActivityAknowledgements);
		mButtonTermsOfService = (Button) findViewById(R.id.buttonAboutMenuActivityTermsOfUse);
		mButtonPrivacyTerms = (Button) findViewById(R.id.buttonAboutMenuActivityPrivacyTerms);
		mLinearLayoutPrivacyTerms = (LinearLayout) findViewById(R.id.linearlayoutAboutMenuActivityPrivacyTermsContainer);
		mLinearLayoutAknwoledgementsContainer = (LinearLayout) findViewById(R.id.linearlayoutAboutMenuActivityAknowledgementsContainer);
		mLinearLayoutTOSContainer = (LinearLayout) findViewById(R.id.linearlayoutAboutMenuActivityTermsOfUseContainer);
		mWebViewAknowledgements = (WebView) findViewById(R.id.webviewAboutMenuActivityAknowledgements);
		mWebViewPrivacyTerms = (WebView) findViewById(R.id.webviewAboutMenuActivityPrivacyTerms);
		mWebViewTermsOfService = (WebView) findViewById(R.id.webviewAboutMenuActivityTermsOfService);

		mButtonAcknowledgements.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!isAknwoledgementVisible) {
					mLinearLayoutAknwoledgementsContainer.setVisibility(View.GONE);
					mButtonAcknowledgements.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.about_app_down_arrow_down), null);
				} else {
					mLinearLayoutAknwoledgementsContainer.setVisibility(View.VISIBLE);
					mButtonAcknowledgements.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.about_app_down_arrow_up), null);
				}
				isAknwoledgementVisible = !isAknwoledgementVisible;
			}
		});

		mButtonTermsOfService.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!isTOSVisible) {
					mLinearLayoutTOSContainer.setVisibility(View.GONE);
					mButtonTermsOfService.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.about_app_down_arrow_down), null);
				} else {
					mLinearLayoutTOSContainer.setVisibility(View.VISIBLE);
					mButtonTermsOfService.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.about_app_down_arrow_up), null);
				}
				isTOSVisible = !isTOSVisible;
			}
		});

		mButtonPrivacyTerms.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!isPrivacyTermsVisible) {
					mLinearLayoutPrivacyTerms.setVisibility(View.GONE);
					mButtonPrivacyTerms.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.about_app_down_arrow_down), null);
				} else {
					mLinearLayoutPrivacyTerms.setVisibility(View.VISIBLE);
					mButtonPrivacyTerms.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.about_app_down_arrow_up), null);
				}

				isPrivacyTermsVisible = !isPrivacyTermsVisible;

			}
		});

	}

	private void loadAknowledgements() {
		mWebViewAknowledgements.loadUrl(HTTP_ADDRESS_AKNOWLEDGEMENTS);
	}

	private void loadPrivacyTerms() {
		mWebViewPrivacyTerms.loadUrl(HTTP_ADDRESS_PRIVACY_TERMS);
	}

	private void loadTermsOfService() {
		mWebViewTermsOfService.loadUrl(HTTP_ADDRESS_TERMS_OF_SERVICE);
	}

}
