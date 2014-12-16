package com.s7design.menu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.s7design.menu.R;
import com.s7design.menu.utils.Utils;

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
		hideActionBar();
		initViews();
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
				Toast.makeText(SignInActivity.this, "Go to FORGOT PASSWORD screen.", Toast.LENGTH_SHORT).show();
			}
		});

		mSignInButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (Utils.isNetworkAvailable(getApplicationContext())) {
					if (!mUsernameEditText.getText().toString().equals("") && !mPasswordEditText.getText().toString().equals("")) {
						Toast.makeText(SignInActivity.this, "LoginUser: " + mUsernameEditText.getText().toString(), Toast.LENGTH_SHORT).show();
					} else if (mUsernameEditText.getText().toString().equals("") && !mPasswordEditText.getText().toString().equals("")) {
						showAlertDialog(null, getResources().getString(R.string.dialog_insert_email_address));
					} else if (!mUsernameEditText.getText().toString().equals("") && mPasswordEditText.getText().toString().equals("")) {
						showAlertDialog(null, getResources().getString(R.string.dialog_insert_password));
					} else if (mUsernameEditText.getText().toString().equals("") && mPasswordEditText.getText().toString().equals("")) {
						showAlertDialog(null, getResources().getString(R.string.dialog_insert_emal_address_and_password));
					}

				} else {
					showAlertDialog(R.string.dialog_title_error, R.string.dialog_no_internet_connection);
				}
			}
		});

	}
}
