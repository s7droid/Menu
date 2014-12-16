package com.s7design.menu.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.s7design.menu.R;

public class CircleButtonView extends RelativeLayout {

	private TextView textView;

	public CircleButtonView(Context context) {
		super(context);

		init();
	}

	public CircleButtonView(Context context, AttributeSet attrs) {
		super(context, attrs);

		init();
	}

	private void init() {

		inflate(getContext(), R.layout.circle_button_view, this);

		textView = (TextView) findViewById(R.id.textView);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		textView.setTextSize(heightMeasureSpec * 2 / 3);
	}

	public void setAsQty(int qty) {
		textView.setText(String.valueOf(qty));
		textView.setTextColor(getResources().getColor(R.color.menu_main_gray));
		setBackgroundResource(R.drawable.border_round_gray);
	}

	public void setAsDel() {
		textView.setText("x");
		textView.setTextColor(getResources().getColor(R.color.menu_main_gray));
		setBackgroundResource(R.drawable.border_round_gray);
	}

	public void setAsAdd() {
		textView.setText("+");
		textView.setTextColor(getResources().getColor(R.color.menu_main_orange));
		setBackgroundResource(R.drawable.border_round_orange);
	}

}
