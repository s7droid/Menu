package com.s7design.menu.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.s7design.menu.R;

public class SignUpActivity extends BaseActivity {

	// VIEWS
	private Button mShowHidePasswordButton;
	private Button mScanCreditCardButton;
	private Button mSignUpButton;
	private EditText mPasswordEditText;
	private EditText mNicknameEditText;
	private EditText mRepeatPasswordEditText;
	private EditText mCreditCardNumberEditText;
	private EditText mNameOnCardEditText;
	private EditText mMonthEditText;
	private EditText mYearEditText;
	private EditText mCCVEditText;

	// DATA
	private boolean isPasswordShowing = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		initActionBar();
		initViews();

	}

	private void initActionBar() {
		setActionBarForwardButtonvisibility(View.INVISIBLE);
		setActionBarMenuButtonVisibility(View.INVISIBLE);

		setActionBarMenuButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		setActionBarBackButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	private void initViews() {
		mSignUpButton = (Button) findViewById(R.id.buttonSignUpActivitySignUp);
		mShowHidePasswordButton = (Button) findViewById(R.id.buttonSignUpActivityHide);
		mScanCreditCardButton = (Button) findViewById(R.id.buttonSignUpScanCreditCard);
		mPasswordEditText = (EditText) findViewById(R.id.edittextSignUpActivityPassword);
		mNicknameEditText = (EditText) findViewById(R.id.edittextSignUpActivityNickname);
		mRepeatPasswordEditText = (EditText) findViewById(R.id.edittextSignUpActivityRepeatPassword);
		mCreditCardNumberEditText = (EditText) findViewById(R.id.edittextSignUpActivityCardNumber);
		mNameOnCardEditText = (EditText) findViewById(R.id.edittextSignUpActivityNameOnCard);
		mMonthEditText = (EditText) findViewById(R.id.edittextSignUpActivityMonth);
		mYearEditText = (EditText) findViewById(R.id.edittextSignUpActivityYear);
		mCCVEditText = (EditText) findViewById(R.id.edittextSignUpActivityCCV);

		mSignUpButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		mShowHidePasswordButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (isPasswordShowing) {
					mShowHidePasswordButton.setText("SHOW");
					mPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
					mPasswordEditText.setTypeface(Typeface.createFromAsset(SignUpActivity.this.getAssets(), "fonts/GothamRounded-Book.otf"));
				} else {
					mShowHidePasswordButton.setText("HIDE");
					mPasswordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					mPasswordEditText.setTypeface(Typeface.createFromAsset(SignUpActivity.this.getAssets(), "fonts/GothamRounded-Book.otf"));

				}

				isPasswordShowing = !isPasswordShowing;

			}
		});

		mScanCreditCardButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
	}
}
