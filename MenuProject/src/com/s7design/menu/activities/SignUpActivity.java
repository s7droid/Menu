package com.s7design.menu.activities;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
	private TextView mShowHidePasswordButton;
	private Button mScanCreditCardButton;
	private Button mSignUpButton;
	private EditText mEmailEditText;
	private EditText mPasswordEditText;
	// private EditText mNicknameEditText;
	private EditText mRepeatPasswordEditText;
	private EditText mCreditCardNumberEditText;
	private EditText mNameOnCardEditText;
	private EditText mMonthEditText;
	private EditText mYearEditText;
	private EditText mCCVEditText;
	private TextView mTextViewTermsOfUse;
	// DATA
	private boolean isPasswordShowing = false;
	private int MY_SCAN_REQUEST_CODE = 123; // arbitrary int

	private String creditCardNumber;
	private String nameOnCard;
	private String expiryDate;
	private String cardCCV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		initActionBar();
		initViews();

	}

	private void initActionBar() {
		setActionBarMenuButtonVisibility(View.INVISIBLE);

		setActionBarForwardArrowVisibility(null);
		setActionBarForwardButtonText(getResources().getString(R.string.action_bar_sign_up));
		setActionBarForwardButtonTextColor(getResources().getColor(R.color.menu_main_orange));

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
		mShowHidePasswordButton = (TextView) findViewById(R.id.buttonSignUpActivityHide);
		mScanCreditCardButton = (Button) findViewById(R.id.buttonSignUpScanCreditCard);
		mEmailEditText = (EditText) findViewById(R.id.edittextSignUpActivityEmail);
		mPasswordEditText = (EditText) findViewById(R.id.edittextSignUpActivityPassword);
		// mNicknameEditText = (EditText)
		// findViewById(R.id.edittextSignUpActivityNickname);
		mRepeatPasswordEditText = (EditText) findViewById(R.id.edittextSignUpActivityRepeatPassword);
		mCreditCardNumberEditText = (EditText) findViewById(R.id.edittextSignUpActivityCardNumber);
		mNameOnCardEditText = (EditText) findViewById(R.id.edittextSignUpActivityNameOnCard);
		mMonthEditText = (EditText) findViewById(R.id.edittextSignUpActivityMonth);
		mYearEditText = (EditText) findViewById(R.id.edittextSignUpActivityYear);
		mCCVEditText = (EditText) findViewById(R.id.edittextSignUpActivityCCV);
		mTextViewTermsOfUse = (TextView) findViewById(R.id.textviewSignUpScanTermsOfUse);

		initClicksInTermsText();

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
								} else if (signUpResponse.response.equals("emailinuse")) {
									showAlertDialog(R.string.dialog_title_error, R.string.dialog_email_in_use);
								} else if (signUpResponse.response.equals("ccdeclined")) {
									showAlertDialog(R.string.dialog_title_error, R.string.dialog_card_declined);
								}

								dismissProgressDialog();
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

				System.out.println("SHOW/HIDE PASS BUTTON CLICKED");

				if (isPasswordShowing) {
					mShowHidePasswordButton.setText(R.string.sign_up_show);
					mPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
					((MenuEditText) mPasswordEditText).setFont(true);
				} else {
					mShowHidePasswordButton.setText(R.string.sign_up_hide);
					mPasswordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					((MenuEditText) mPasswordEditText).setFont(true);

				}

				isPasswordShowing = !isPasswordShowing;

			}
		});

		mScanCreditCardButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("SCAN BUTTON CLICKED");
				// This method is set up as an onClick handler in the layout xml
				// e.g. android:onClick="onScanPress"

				Intent scanIntent = new Intent(SignUpActivity.this, CardIOActivity.class);

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
	}

	private void initClicksInTermsText() {
		String terms_intro = getResources().getString(R.string.sign_up_privacy_terms_intro);
		String terms_privacy_clickable = " " + getResources().getString(R.string.sign_up_privacy_terms_clickable);
		String terms_and = " " + getResources().getString(R.string.sign_up_privacy_policy_and);
		String terms_privacy_policy_clickable = " " + getResources().getString(R.string.sign_up_privacy_policy_clickable);
		String terms_tokenizer = ". " + getResources().getString(R.string.sign_up_privacy_tokenizer);
		String terms_tokenizer_clickable = " " + getResources().getString(R.string.sign_up_privacy_tokenizer_clickable) + ".";

		SpannableString ss = new SpannableString(terms_intro + terms_privacy_clickable + terms_and + terms_privacy_policy_clickable + terms_tokenizer + terms_tokenizer_clickable);
		ClickableSpan clickableSpanTermsOfService = new ClickableSpan() {
			@Override
			public void onClick(View textView) {
				startActivity(new Intent(SignUpActivity.this, AboutTheAppActivity.class));
			}
		};
		ss.setSpan(clickableSpanTermsOfService, terms_intro.length() + 1, (terms_intro.length() + 1 + terms_privacy_clickable.length()), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.menu_main_orange)), terms_intro.length() + 1, (terms_intro.length() + 1 + terms_privacy_clickable.length() - 1),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		ClickableSpan clickableSpanPrivacyPolicy = new ClickableSpan() {
			@Override
			public void onClick(View textView) {
				startActivity(new Intent(SignUpActivity.this, AboutTheAppActivity.class));
			}
		};
		ss.setSpan(clickableSpanPrivacyPolicy, (terms_intro.length() + terms_privacy_clickable.length() + terms_and.length() + 1),
				(terms_intro.length() + terms_privacy_clickable.length() + terms_and.length() + terms_privacy_policy_clickable.length()), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.menu_main_orange)), (terms_intro.length() + terms_privacy_clickable.length() + terms_and.length() + 1),
				(terms_intro.length() + terms_privacy_clickable.length() + terms_and.length() + terms_privacy_policy_clickable.length()), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		ClickableSpan clickableSpanTokenizerSeeHow = new ClickableSpan() {
			@Override
			public void onClick(View textView) {
				startActivity(new Intent(SignUpActivity.this, TokenizationExplainedActivity.class));
			}
		};
		ss.setSpan(clickableSpanTokenizerSeeHow,
				(terms_intro.length() + terms_privacy_clickable.length() + terms_and.length() + terms_privacy_policy_clickable.length() + terms_tokenizer.length() + 1), (terms_intro.length()
						+ terms_privacy_clickable.length() + terms_and.length() + terms_privacy_policy_clickable.length() + terms_tokenizer.length() + terms_tokenizer_clickable.length() - 1),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.menu_main_orange)), (terms_intro.length() + terms_privacy_clickable.length() + terms_and.length()
				+ terms_privacy_policy_clickable.length() + terms_tokenizer.length() + 1), (terms_intro.length() + terms_privacy_clickable.length() + terms_and.length()
				+ terms_privacy_policy_clickable.length() + terms_tokenizer.length() + terms_tokenizer_clickable.length() - 1), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		mTextViewTermsOfUse.setText(ss);
		mTextViewTermsOfUse.setMovementMethod(LinkMovementMethod.getInstance());

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
			CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

			// Never log a raw card number. Avoid displaying it, but if
			// necessary use getFormattedCardNumber()

			creditCardNumber = scanResult.getRedactedCardNumber();
			System.out.println("credit card number redacted= " + creditCardNumber);
			System.out.println("credit card number formatted=" + scanResult.getFormattedCardNumber());
			System.out.println("credit card number=" + scanResult.cardNumber);
			// mCreditCardNumberEditText.setText(creditCardNumber);
			mCreditCardNumberEditText.setText(scanResult.getFormattedCardNumber());

			if (scanResult.isExpiryValid()) {
				mYearEditText.setText(String.valueOf(scanResult.expiryYear));
				mMonthEditText.setText(String.valueOf(scanResult.expiryMonth));
			}

			if (scanResult.cvv != null) {
				mCCVEditText.setText(scanResult.cvv);
			}

		}
	}

}
