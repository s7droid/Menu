package com.s7design.menu.activities;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response.Listener;
import com.braintreepayments.api.Braintree;
import com.braintreepayments.api.models.CardBuilder;
import com.s7design.menu.R;
import com.s7design.menu.app.Menu;
import com.s7design.menu.utils.Settings;
import com.s7design.menu.views.MenuEditText;
import com.s7design.menu.volley.VolleySingleton;
import com.s7design.menu.volley.requests.SignUpRequest;
import com.s7design.menu.volley.responses.SignUpResponse;

public class SignUpActivity extends BaseActivity {

	private static final String TAG = SignUpActivity.class.getSimpleName();

	// VIEWS
	private Button mShowHidePasswordButton;
	private Button mScanCreditCardButton;
	private Button mSignUpButton;
	private EditText mEmailEditText;
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
		mEmailEditText = (EditText) findViewById(R.id.edittextSignUpActivityEmail);
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

				Braintree braintree = Braintree.getInstance(SignUpActivity.this, Menu.getInstance().getDataManager().getClientBraintreeToken());
				braintree.addListener(new Braintree.PaymentMethodNonceListener() {
					public void onPaymentMethodNonce(String paymentMethodNonce) {

						showProgressDialogLoading();

						Map<String, String> params = new HashMap<String, String>();
						params.put("email", mEmailEditText.getText().toString());
						params.put("password", mPasswordEditText.getText().toString());
						params.put("nonce", paymentMethodNonce);
						params.put("name", mNameOnCardEditText.getText().toString());
						params.put("phonenumber", "0641234567");

						SignUpRequest signUpRequest = new SignUpRequest(SignUpActivity.this, params, new Listener<SignUpResponse>() {

							@Override
							public void onResponse(SignUpResponse signUpResponse) {

								if (signUpResponse.response.equals("success")) {
									Settings.setAccessToken(SignUpActivity.this, signUpResponse.accesstoken);
									Intent i = new Intent(SignUpActivity.this, RestaurantPreviewActivity.class);
									i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(i);
									dismissProgressDialog();
								}
							}
						});

						VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(signUpRequest);
					}
				});

				CardBuilder cardBuilder = new CardBuilder().cardNumber(mCreditCardNumberEditText.getText().toString()).expirationDate(
						mMonthEditText.getText().toString() + "/" + mYearEditText.getText().toString());
				braintree.tokenize(cardBuilder);
			}
		});

		mShowHidePasswordButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (isPasswordShowing) {
					mShowHidePasswordButton.setText(R.string.sign_up_show);
					mPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
					((MenuEditText) mPasswordEditText).setFont(false);
				} else {
					mShowHidePasswordButton.setText(R.string.sign_up_hide);
					mPasswordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					((MenuEditText) mPasswordEditText).setFont(false);

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
