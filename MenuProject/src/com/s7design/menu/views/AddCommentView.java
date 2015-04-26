package com.s7design.menu.views;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.s7design.menu.R;

public class AddCommentView extends RelativeLayout {

	public AddCommentView(Context context) {
		super(context);
		init();
	}

	public AddCommentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public AddCommentView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {

		View view = inflate(getContext(), R.layout.dialog_edit_text, this);

		final EditText textViewBody = (EditText) view.findViewById(R.id.editetextBodyAlertEditTextDialog);
		final TextView characatersLeft = (TextView) view.findViewById(R.id.textviewCharactersLeft);
		Button buttonOk = (Button) view.findViewById(R.id.buttonEditTextDialogOk);

		buttonOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setVisibility(View.GONE);
			}
		});

		characatersLeft.setText(textViewBody.length() + "/100");

		textViewBody.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				characatersLeft.setText(textViewBody.length() + "/100");
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

	}

}
