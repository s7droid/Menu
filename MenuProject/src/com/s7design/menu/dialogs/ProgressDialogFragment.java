package com.s7design.menu.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.s7design.menu.R;

public class ProgressDialogFragment extends DialogFragment {
	private FragmentManager fm;
	private String tag = "alert_dialog";
	private AlertDialog.Builder builder;
	private String body;
	private Context context;
	private TextView bodyTextView;
	private boolean isVisible = false;

	public ProgressDialogFragment() {
	}

	public void setFragmentManager(FragmentManager fm, Context context) {
		this.fm = fm;
		this.context = context;
	}

	@Override
	public Dialog onCreateDialog(final Bundle savedInstanceState) {
		isVisible = true;
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.dialog_progress, null);
		bodyTextView = (TextView) view.findViewById(R.id.textViewBodyProgressDialog);
		if (body != null)
			bodyTextView.setText(body);
		builder = new AlertDialog.Builder(getActivity());
		builder.setView(view);
		setCancelable(false);
		return builder.create();
	}

	// @Override
	// public void onStart() {
	// super.onStart();
	// try {
	// getDialog().getWindow().setLayout(800, LayoutParams.WRAP_CONTENT);
	// } catch (Exception e) {
	// Log.e("ProgressDialog", "onStart error");
	// }
	// }

	public void showDialog(String body) {
		if (isVisible() || isVisible)
			return;
		this.body = body;
		show(fm, tag);
	}

	public void showDialog() {
		if (isVisible() || isVisible)
			return;
		show(fm, tag);
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
		isVisible = false;
	}
}