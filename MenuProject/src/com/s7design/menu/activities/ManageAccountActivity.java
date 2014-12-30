package com.s7design.menu.activities;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.s7design.menu.R;
import com.s7design.menu.views.MenuEditText;

public class ManageAccountActivity extends BaseActivity {

	// VIEWS
	private EditText mEditTextNameOnCard;
	private EditText mEditTextEmail;
	private EditText mEditTextNewPassword;
	private EditText mEditTextRepeatPassword;
	private Button mButtonShowPassword;
	private Button mButtonChangeCreditCard;
	private Button mButtonConfirmChanges;

	// DATA
	private boolean isPasswordShowing = false;
	private int MY_SCAN_REQUEST_CODE = 124; // arbitrary int

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menage_account);

		initActionBar();
		initViews();
		initData();

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
		mButtonShowPassword = (Button) findViewById(R.id.buttonManageAccountActivityHide);
		mButtonChangeCreditCard = (Button) findViewById(R.id.buttonManageAccountActivityScanCreditCard);
		mButtonConfirmChanges = (Button) findViewById(R.id.buttonManageAccountCinfirmChanges);

		mButtonShowPassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (isPasswordShowing) {
					mButtonShowPassword.setText(R.string.sign_up_show);
					mEditTextNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
					((MenuEditText) mEditTextNewPassword).setFont(false);
				} else {
					mButtonShowPassword.setText(R.string.sign_up_hide);
					mEditTextNewPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					((MenuEditText) mEditTextNewPassword).setFont(false);

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

			}
		});
	}

	private void initData() {

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
			CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

			// Never log a raw card number. Avoid displaying it, but if
			// necessary use getFormattedCardNumber()

//			creditCardNumber = scanResult.getRedactedCardNumber();
//			mCreditCardNumberEditText.setText(creditCardNumber);
//
//			if (scanResult.isExpiryValid()) {
//				mYearEditText.setText(String.valueOf(scanResult.expiryYear));
//				mMonthEditText.setText(String.valueOf(scanResult.expiryMonth));
//			}
//
//			if (scanResult.cvv != null) {
//				mCCVEditText.setText(scanResult.cvv);
//			}

		}
	}

}