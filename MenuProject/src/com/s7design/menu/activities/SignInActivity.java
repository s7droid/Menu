package com.s7design.menu.activities;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.s7design.menu.R;
import com.s7design.menu.utils.Settings;
import com.s7design.menu.utils.Utils;
import com.s7design.menu.volley.VolleySingleton;
import com.s7design.menu.volley.requests.LoginRequest;
import com.s7design.menu.volley.responses.LoginResponse;

public class SignInActivity extends BaseActivity {

	// VIEWS
	private EditText mUsernameEditText;
	private EditText mPasswordEditText;
	private TextView mForgottPasswordButton;
	private TextView mRegisterButton;
	private Button mSignInButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
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

		mUsernameEditText = (EditText) findViewById(R.id.edittextSignInActivityUsername);
		mPasswordEditText = (EditText) findViewById(R.id.edittextSignInActivityPassword);
		mForgottPasswordButton = (TextView) findViewById(R.id.textviewSignInActivityForgotPassword);
		mRegisterButton = (TextView) findViewById(R.id.textviewSignInActivitySignUp);
		mSignInButton = (Button) findViewById(R.id.buttonSignInActivitySignin);

		mRegisterButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
			}
		});

		mForgottPasswordButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			}
		});

		mSignInButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (Utils.isNetworkAvailable(getApplicationContext())) {
					if (mUsernameEditText.getText().toString().equals("")) {
						showAlertDialog(null, getResources().getString(R.string.dialog_insert_email_address));
					} else if (mPasswordEditText.getText().toString().equals("")) {
						showAlertDialog(null, getResources().getString(R.string.dialog_insert_password));
					} else {

						showProgressDialogLoading();

						Map<String, String> params = new HashMap<String, String>();
						params.put("email", mUsernameEditText.getText().toString());
						params.put("password", mPasswordEditText.getText().toString());

						LoginRequest loginRequest = new LoginRequest(SignInActivity.this, params, new Listener<LoginResponse>() {

							@Override
							public void onResponse(LoginResponse loginResponse) {

								if (loginResponse.response != null && loginResponse.response.equals("success")) {
									Settings.setAccessToken(SignInActivity.this, loginResponse.accesstoken);
//									Intent i = new Intent(SignInActivity.this, RestaurantPreviewActivity.class);
//									i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//									startActivity(i);
									Intent intent = new Intent();
									setResult(RESULT_OK,intent);
									dismissProgressDialog();
									finish();
								} else {
									dismissProgressDialog();

									if (loginResponse.response != null && loginResponse.response.equals("nouser")) {
										showAlertDialog(R.string.dialog_title_error, R.string.dialog_no_user);
									} else if (loginResponse.response.equals("passwordfalse")) {
										showAlertDialog(R.string.dialog_title_error, R.string.dialog_wrong_password);
									}

								}
							}
						});

						VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(loginRequest);
					}

				} else {
					showAlertDialog(R.string.dialog_title_error, R.string.dialog_no_internet_connection);
				}
			}
		});

	}
}
