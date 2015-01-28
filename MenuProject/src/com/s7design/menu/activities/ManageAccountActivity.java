package com.s7design.menu.activities;

import java.util.HashMap;
import java.util.Map;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.s7design.menu.R;
import com.s7design.menu.dialogs.ProgressDialogFragment;
import com.s7design.menu.utils.Settings;
import com.s7design.menu.views.MenuEditText;
import com.s7design.menu.volley.VolleySingleton;
import com.s7design.menu.volley.requests.FetchAccountRequest;
import com.s7design.menu.volley.responses.FetchAccountResponse;

public class ManageAccountActivity extends BaseActivity {

	// VIEWS
	private EditText mEditTextNameOnCard;
	private EditText mEditTextEmail;
	private EditText mEditTextNewPassword;
	private EditText mEditTextRepeatPassword;
	private EditText mEditTextCreditCardNumber;
//	private EditText mEditTextNameOnCardNew;
	private EditText mEditTextMonth;
	private EditText mEditTextYear;
	private EditText mEditTextCCV;
	private Button mButtonShowPassword;
	private Button mButtonChangeCreditCard;
	private Button mButtonConfirmChanges;
	private LinearLayout mLinearLayoutCreditCardDataCOntainer;
	// DATA
	private boolean isPasswordShowing = false;
	private int MY_SCAN_REQUEST_CODE = 124; // arbitrary int

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final ProgressDialogFragment progressDialog = new ProgressDialogFragment();
		progressDialog.show(getFragmentManager(), ManageAccountActivity.class.getSimpleName());

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
				progressDialog.dismiss();
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
		mButtonShowPassword = (Button) findViewById(R.id.buttonManageAccountActivityHide);
		mButtonChangeCreditCard = (Button) findViewById(R.id.buttonManageAccountActivityScanCreditCard);
		mButtonConfirmChanges = (Button) findViewById(R.id.buttonManageAccountCinfirmChanges);
		mEditTextCreditCardNumber = (EditText) findViewById(R.id.edittextManageAccountActivityCardNumber);
//		mEditTextNameOnCardNew = (EditText) findViewById(R.id.edittextManageAccountActivityNameOnCard);
		mEditTextMonth = (EditText) findViewById(R.id.edittextManageAccountActivityMonth);
		mEditTextYear = (EditText) findViewById(R.id.edittextManageAccountActivityYear);
		mEditTextCCV = (EditText) findViewById(R.id.edittextManageAccountActivityCCV);
		mLinearLayoutCreditCardDataCOntainer = (LinearLayout) findViewById(R.id.linearlayoutManageAccountActivityCardContainer);
				
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