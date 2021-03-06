package com.usemenu.MenuAndroidApplication.activities;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

import java.util.Calendar;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.braintreepayments.api.Braintree;
import com.braintreepayments.api.models.CardBuilder;
import com.usemenu.MenuAndroidApplication.R;
import com.usemenu.MenuAndroidApplication.app.Menu;
import com.usemenu.MenuAndroidApplication.utils.Settings;
import com.usemenu.MenuAndroidApplication.utils.Utils;
import com.usemenu.MenuAndroidApplication.views.MenuEditText;
import com.usemenu.MenuAndroidApplication.volley.VolleySingleton;
import com.usemenu.MenuAndroidApplication.volley.requests.FetchAccountRequest;
import com.usemenu.MenuAndroidApplication.volley.requests.ModifyAccountRequest;
import com.usemenu.MenuAndroidApplication.volley.requests.SignUpRequest;
import com.usemenu.MenuAndroidApplication.volley.responses.FetchAccountResponse;
import com.usemenu.MenuAndroidApplication.volley.responses.ModifyAccountResponse;
import com.usemenu.MenuAndroidApplication.volley.responses.SignUpResponse;

public class ManageAccountActivity extends BaseActivity {

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
	private Map<String, String> params = new HashMap<String, String>();
	private String creditCardNumber;

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

		Utils.handleOutsideEditTextClick(findViewById(R.id.relativelayoutContainer), this);
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
				if (!mEditTextNewPassword.getText().toString().equals(mEditTextRepeatPassword.getText().toString())) {
					showAlertDialog(getResources().getString(R.string.dialog_passwords_dont_match_title),
							getResources().getString(R.string.dialog_passwords_dont_match_content));
					return;
				} else if (!mEditTextCreditCardNumber.getText().toString().isEmpty()) {
					Calendar calendar = Calendar.getInstance();

					if (mEditTextMonth.getText().toString().isEmpty() || mEditTextYear.getText().toString().isEmpty()) {
						showAlertDialog(getResources().getString(R.string.dialog_invalid_expiry_date_error_title),
								getResources().getString(R.string.dialog_invalid_expiry_date_error_content));
						return;
					} else if (!Utils.isValidExpiryDate(mEditTextMonth.getText().toString(), mEditTextYear.getText().toString())) {
						System.out.println("EditText month= " + Integer.valueOf(mEditTextMonth.getText().toString()) + "\nCalendar month= "
								+ calendar.get(Calendar.MONTH) + 1);
						showAlertDialog(getResources().getString(R.string.dialog_invalid_expiry_date_error_title),
								getResources().getString(R.string.dialog_invalid_expiry_date_error_content));
						return;
					} else if (mEditTextCCV.getText().toString().isEmpty()) {
						showAlertDialog(getResources().getString(R.string.dialog_cvv_empty_title), getResources().getString(R.string.dialog_cvv_empty_content));
					} else {
						Braintree braintree = Braintree.getInstance(ManageAccountActivity.this,
								Menu.getInstance().getDataManager().getClientBraintreeToken(ManageAccountActivity.this));
						braintree.addListener(new Braintree.PaymentMethodNonceListener() {
							public void onPaymentMethodNonce(String paymentMethodNonce) {

								showProgressDialogLoading();

								params.put("accesstoken", Settings.getAccessToken(getApplicationContext()));
								params.put("name", mEditTextNameOnCard.getText().toString());
								params.put("email", mEditTextEmail.getText().toString());

								if (!mEditTextNewPassword.getText().toString().isEmpty())
									params.put("password", mEditTextNewPassword.getText().toString());

								params.put("nonce", paymentMethodNonce);

								ModifyAccountRequest request = new ModifyAccountRequest(ManageAccountActivity.this, params,
										new Listener<ModifyAccountResponse>() {

											@Override
											public void onResponse(ModifyAccountResponse arg0) {
												finish();
												dismissProgressDialog();
											}
										});

								VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

								if (!mEditTextNewPassword.getText().toString().isEmpty()) {
									SignUpRequest signUpRequest = new SignUpRequest(ManageAccountActivity.this, params, new Listener<SignUpResponse>() {

										@Override
										public void onResponse(SignUpResponse signUpResponse) {

											if (signUpResponse.response.equals("success")) {
												Settings.setAccessToken(ManageAccountActivity.this, signUpResponse.accesstoken);
												finish();
												dismissProgressDialog();
											}

											dismissProgressDialog();
										}
									});

									VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(signUpRequest);
								}
							}
						});

						CardBuilder cardBuilder = new CardBuilder().cardNumber(mEditTextCreditCardNumber.getText().toString())
								.expirationDate(mEditTextMonth.getText().toString() + "/" + mEditTextYear.getText().toString())
								.cvv(mEditTextCCV.getText().toString());
						braintree.tokenize(cardBuilder);
						return;
					}

				} else {

					showProgressDialogLoading();

					params.put("accesstoken", Settings.getAccessToken(getApplicationContext()));
					params.put("name", mEditTextNameOnCard.getText().toString());
					params.put("email", mEditTextEmail.getText().toString());

					if (!mEditTextNewPassword.getText().toString().isEmpty())
						params.put("password", mEditTextNewPassword.getText().toString());

					ModifyAccountRequest request = new ModifyAccountRequest(ManageAccountActivity.this, params, new Listener<ModifyAccountResponse>() {

						@Override
						public void onResponse(ModifyAccountResponse arg0) {
							finish();
							dismissProgressDialog();
						}
					});

					VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
				}
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
			CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

			// Never log a raw card number. Avoid displaying it, but if
			// necessary use getFormattedCardNumber()

			creditCardNumber = scanResult.getRedactedCardNumber();
			// mCreditCardNumberEditText.setText(creditCardNumber);
			mEditTextCreditCardNumber.setText(scanResult.getFormattedCardNumber());

			if (scanResult.isExpiryValid()) {
				Log.d("dialog", "Month= " + (scanResult.expiryMonth < 10 ? ("0" + String.valueOf(scanResult.expiryMonth)) : String.valueOf(scanResult.expiryMonth)));
				Log.d("dialog", "Year= " + String.valueOf(scanResult.expiryYear));
				String year = String.valueOf(scanResult.expiryYear).substring(2, 4);
				mEditTextYear.setText(year);
				mEditTextMonth.setText(scanResult.expiryMonth < 10 ? ("0" + String.valueOf(scanResult.expiryMonth)) : String.valueOf(scanResult.expiryMonth));
			}

			if (scanResult.cvv != null) {
				mEditTextCCV.setText(scanResult.cvv);
			}

			mEditTextCreditCardNumber.setTextColor(getResources().getColor(R.color.menu_main_gray));
			mEditTextMonth.setTextColor(getResources().getColor(R.color.menu_main_gray));
			mEditTextYear.setTextColor(getResources().getColor(R.color.menu_main_gray));
			mEditTextCCV.setTextColor(getResources().getColor(R.color.menu_main_gray));
		}
	}

}