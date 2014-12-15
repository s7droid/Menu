package com.s7design.menu.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.s7design.menu.R;
import com.s7design.menu.dialogs.AlertDialogFragment;
import com.s7design.menu.dialogs.ProgressDialogFragment;

public class BaseActivity extends Activity {

	private ActionBar actionBar;

	private AlertDialogFragment alertDialog;
	private ProgressDialogFragment progressDialog;
	private Button buttonActionBarBack;
	private Button buttonActionBarForward;
	private ImageButton imageButtonActionBarMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayUseLogoEnabled(false);

		LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.action_bar, null);

		actionBar.setCustomView(v);

		alertDialog = new AlertDialogFragment();
		alertDialog.setFragmentManager(getFragmentManager(), this);

		progressDialog = new ProgressDialogFragment();
		progressDialog.setFragmentManager(getFragmentManager(), this);

		buttonActionBarBack = (Button) findViewById(R.id.buttonActionBarBack);
		buttonActionBarForward = (Button) findViewById(R.id.buttonActionBarForward);
		imageButtonActionBarMenu = (ImageButton) findViewById(R.id.imageButtonActionBarMenu);
	}

	public void hideActionBar() {
		actionBar.hide();
	}

	public void setActionBarMenuButtonOnClickListener(OnClickListener listener) {
		if (listener != null)
			imageButtonActionBarMenu.setOnClickListener(listener);
	}

	public void setActionBarBackButtonOnClickListener(OnClickListener listener) {
		if (listener != null)
			buttonActionBarBack.setOnClickListener(listener);
	}

	public void setActionBarForwardButtonOnClickListener(OnClickListener listener) {
		if (listener != null)
			buttonActionBarForward.setOnClickListener(listener);
	}

	public void setActionBarBackButtonText(String text) {
		buttonActionBarBack.setText(text);
	}

	public void setActionBarBackButtonText(int text) {
		buttonActionBarBack.setText(text);
	}

	public void setActionBarForwardButtonText(String text) {
		buttonActionBarForward.setText(text);
	}

	public void setActionBarForwardButtonText(int text) {
		buttonActionBarForward.setText(text);
	}

	public void showAlertDialog(int title, int body) {
		alertDialog.showDialog(getString(title), getString(body), null);
	}

	public void showAlertDialog(String title, String body) {
		alertDialog.showDialog(title, body, null);
	}

	public void showAlertDialog(int title, int body, OnClickListener onClickListener) {
		alertDialog.showDialog(getString(title), getString(body), onClickListener);
	}

	public void showAlertDialog(String title, String body, OnClickListener onClickListener) {
		alertDialog.showDialog(title, body, onClickListener);
	}

	public void showProgressDialog(int body) {
		progressDialog.showDialog(getString(body));
	}

	public void showProgressDialog(String body) {
		progressDialog.showDialog(body);
	}

	public void showProgressDialogLoading() {
		if (!progressDialog.isVisible())
			progressDialog.showDialog();
	}

	public void dismissProgressDialog() {
		try {
			progressDialog.dismissAllowingStateLoss();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
