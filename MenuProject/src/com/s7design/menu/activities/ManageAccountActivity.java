package com.s7design.menu.activities;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.s7design.menu.R;
import com.s7design.menu.utils.Settings;
import com.s7design.menu.utils.Utils;
import com.s7design.menu.views.MenuEditText;
import com.s7design.menu.volley.VolleySingleton;
import com.s7design.menu.volley.requests.FetchAccountRequest;
import com.s7design.menu.volley.requests.ModifyAccountRequest;
import com.s7design.menu.volley.responses.FetchAccountResponse;
import com.s7design.menu.volley.responses.ModifyAccountResponse;

public class ManageAccountActivity extends BaseActivity{

	// VIEWS
	private EditText mEditTextNameOnCard;
	private EditText mEditTextEmail;
	private EditText mEditTextNewPassword;
	private EditText mEditTextRepeatPassword;
	private EditText mEditTextCreditCardNumber;
	// private EditText mEditTextNameOnCardNew;
	private EditText mEditTextMonth;
	private EditText mEditTextYear;
	private EditText mEditTextCCV;
	private TextView mButtonShowPassword;
	private Button mButtonChangeCreditCard;
	private Button mButtonConfirmChanges;
	private LinearLayout mLinearLayoutCreditCardDataCOntainer;

	// DATA
	private boolean isPasswordShowing = false;
	private int MY_SCAN_REQUEST_CODE = 124; // arbitrary int
	private String initialName;
	private String initialEmail;
	Map<String, String> params = new HashMap<String, String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		showProgressDialogLoading();

		initActionBar();

		Map<String, String> params = new HashMap<String, String>();
		params.put("accesstoken", Settings.getAccessToken(getApplicationContext()));

		FetchAccountRequest request = new FetchAccountRequest(ManageAccountActivity.this, params, new Response.Listener<FetchAccountResponse>() {

			@Override
			public void onResponse(FetchAccountResponse response) {
				setContentView(R.layout.activity_menage_account);

				initViews();
				mEditTextNameOnCard.setText(response.name);
				mEditTextEmail.setText(response.email);
				initialEmail = response.email;
				initialName = response.name;

				dismissProgressDialog();
			}
		});

		VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

	}

	private void initActionBar() {
		setActionBarMenuButtonVisibility(View.GONE);
		setActionBarForwardArrowVisibility(null);
		setActionBarForwardButtonText(getResources().getString(R.string.action_bar_account_management));
		setActionBarForwardButtonTextColor(getResources().getColor(R.color.menu_main_orange));

		setActionBarBackButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void initViews() {
		mEditTextEmail = (EditText) findViewById(R.id.edittextManageAccountActivityEmail);
		mEditTextNameOnCard = (EditText) findViewById(R.id.edittextManageAccountActivityNickname);
		mEditTextNewPassword = (EditText) findViewById(R.id.edittextManageAccountActivityPassword);
		mEditTextRepeatPassword = (EditText) findViewById(R.id.edittextManageAccountActivityRepeatPassword);
		mButtonShowPassword = (TextView) findViewById(R.id.textviewManageAccountActivityHide);
		mButtonChangeCreditCard = (Button) findViewById(R.id.buttonManageAccountActivityScanCreditCard);
		mButtonConfirmChanges = (Button) findViewById(R.id.buttonManageAccountCinfirmChanges);
		mEditTextCreditCardNumber = (EditText) findViewById(R.id.edittextManageAccountActivityCardNumber);
		// mEditTextNameOnCardNew = (EditText)
		// findViewById(R.id.edittextManageAccountActivityNameOnCard);
		mEditTextMonth = (EditText) findViewById(R.id.edittextManageAccountActivityMonth);
		mEditTextYear = (EditText) findViewById(R.id.edittextManageAccountActivityYear);
		mEditTextCCV = (EditText) findViewById(R.id.edittextManageAccountActivityCCV);
		mLinearLayoutCreditCardDataCOntainer = (LinearLayout) findViewById(R.id.linearlayoutManageAccountActivityCardContainer);

//		setupUI(mEditTextCCV);
//		setupUI(mEditTextCreditCardNumber);
//		setupUI(mEditTextEmail);
//		setupUI(mEditTextMonth);
//		setupUI(mEditTextNameOnCard);
//		setupUI(mEditTextNewPassword);
//		setupUI(mEditTextRepeatPassword);
//		setupUI(mEditTextYear);
		
		Utils.handleOutsideEditTextClick(findViewById(R.id.relativelayoutContainer),this);
		mEditTextNameOnCard.requestFocus();
		
		mButtonShowPassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (isPasswordShowing) {
					mButtonShowPassword.setText(R.string.sign_up_show);
					mEditTextNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
					((MenuEditText) mEditTextNewPassword).setFont(true);
				} else {
					mButtonShowPassword.setText(R.string.sign_up_hide);
					mEditTextNewPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					((MenuEditText) mEditTextNewPassword).setFont(true);

				}

				isPasswordShowing = !isPasswordShowing;
			}
		});

		mButtonChangeCreditCard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("SCAN BUTTON CLICKED");
				// This method is set up as an onClick handler in the layout xml
				// e.g. android:onClick="onScanPress"

				Intent scanIntent = new Intent(ManageAccountActivity.this, CardIOActivity.class);

				// customize these values to suit your needs.
				scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default:
																				// true
				scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, true); // default:
																				// false
				scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default:
																						// false
				// hides the manual entry button
				// if set, developers should provide their own manual entry
				// mechanism in the app
				scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, false); // default:
																						// false

				// MY_SCAN_REQUEST_CODE is arbitrary and is only used within
				// this activity.
				startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);

			}
		});

		mButtonConfirmChanges.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!mEditTextNewPassword.getText().toString().isEmpty() && !mEditTextRepeatPassword.getText().toString().isEmpty()
						&& mEditTextNewPassword.getText().toString().equals(mEditTextRepeatPassword.getText().toString())) {
					// TODO: do something smart :)
				} else if (!mEditTextNewPassword.getText().toString().equals(mEditTextRepeatPassword.getText().toString())) {
					showAlertDialog("", getResources().getString(R.string.manage_account_passwords_not_same));
					// Toast.makeText(getApplicationContext(),
					// "New password and repeated password are not same",
					// Toast.LENGTH_SHORT).show();
					return;
				}

				// Braintree braintree =
				// Braintree.getInstance(ManageAccountActivity.this,
				// Menu.getInstance().getDataManager().getClientBraintreeToken());
				// braintree.addListener(new
				// Braintree.PaymentMethodNonceListener() {
				// public void onPaymentMethodNonce(String paymentMethodNonce) {
				//
				// showProgressDialogLoading();
				//
				// params.put("accesstoken",
				// Settings.getAccessToken(getApplicationContext()));
				// params.put("name", mEditTextNameOnCard.getText().toString());
				// params.put("email", mEditTextEmail.getText().toString());
				//
				// if(mEditTextNewPassword.getText().toString().isEmpty())
				// params.put("password",
				// mEditTextNewPassword.getText().toString());
				//
				// params.put("nonce", paymentMethodNonce);
				//
				// ModifyAccountRequest request = new
				// ModifyAccountRequest(ManageAccountActivity.this, params, new
				// Listener<ModifyAccountResponse>() {
				//
				// @Override
				// public void onResponse(ModifyAccountResponse arg0) {
				// // TODO Auto-ge nerated method stub
				//
				// }
				// });
				//
				// VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
				//
				// SignUpRequest signUpRequest = new
				// SignUpRequest(ManageAccountActivity.this, params, new
				// Listener<SignUpResponse>() {
				//
				// @Override
				// public void onResponse(SignUpResponse signUpResponse) {
				//
				// if (signUpResponse.response.equals("success")) {
				// Settings.setAccessToken(ManageAccountActivity.this,
				// signUpResponse.accesstoken);
				// Intent i = new Intent(ManageAccountActivity.this,
				// RestaurantPreviewActivity.class);
				// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
				// Intent.FLAG_ACTIVITY_CLEAR_TASK |
				// Intent.FLAG_ACTIVITY_CLEAR_TOP);
				// startActivity(i);
				// dismissProgressDialog();
				// }
				//
				// dismissProgressDialog();
				// }
				// });
				//
				// VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(signUpRequest);
				// }
				// });

				// CardBuilder cardBuilder = new
				// CardBuilder().cardNumber(mCreditCardNumberEditText.getText().toString()).expirationDate(
				// mMonthEditText.getText().toString() + "/" +
				// mYearEditText.getText().toString());
				// braintree.tokenize(cardBuilder);

				showProgressDialogLoading();

				params.put("accesstoken", Settings.getAccessToken(getApplicationContext()));
				params.put("name", mEditTextNameOnCard.getText().toString());
				params.put("email", mEditTextEmail.getText().toString());

				if (mEditTextNewPassword.getText().toString().isEmpty())
					params.put("password", mEditTextNewPassword.getText().toString());

				ModifyAccountRequest request = new ModifyAccountRequest(ManageAccountActivity.this, params, new Listener<ModifyAccountResponse>() {

					@Override
					public void onResponse(ModifyAccountResponse arg0) {
						showAlertDialog("", getResources().getString(R.string.dialog_fields_changed), new OnClickListener() {
							@Override
							public void onClick(View v) {
								finish();
							}
						});
						dismissProgressDialog();
					}
				});

				VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
			CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

			mLinearLayoutCreditCardDataCOntainer.setAlpha(1.0f);

			// Never log a raw card number. Avoid displaying it, but if
			// necessary use getFormattedCardNumber()

			// creditCardNumber = scanResult.getRedactedCardNumber();
			// mCreditCardNumberEditText.setText(creditCardNumber);
			//
			// if (scanResult.isExpiryValid()) {
			// mYearEditText.setText(String.valueOf(scanResult.expiryYear));
			// mMonthEditText.setText(String.valueOf(scanResult.expiryMonth));
			// }
			//
			// if (scanResult.cvv != null) {
			// mCCVEditText.setText(scanResult.cvv);
			// }

		}
	}

}