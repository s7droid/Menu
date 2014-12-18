package com.s7design.menu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.s7design.menu.R;

public class MealDetailsActivity extends BaseActivity {

	// VIEWS
	private ImageView mMealImageImageView;
	private TextView mMealDescriptionTextView;
	private TextView mMealReceiptTextView;
	private TextView mMealIngridientsTextView;
	private TextView mOrderLargePriceTextView;
	private TextView mOrderSmallPriceTextView;
	private TextView mOrderLargeQuantityTextView;
	private TextView mOrderSmallQuantityTextView;
	private TextView mReceiptTextView;
	private ImageButton mOrderSmallPlusImageButton;
	private ImageButton mOrderSmallMinusImageButton;
	private ImageButton mOrderBigPlusImageButton;
	private ImageButton mOrderBigMinusImageButton;
	private ImageButton mGetReceiptImageButton;

	// DATA

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meal_details);

		initActionBar();
		initViews();
	}

	private void initActionBar() {
		setActionBarForwardButtonText(getResources().getString(R.string.action_bar_checkout));
		setActionBarBackButtonText(getResources().getString(R.string.action_bar_back));

		setActionBarBackButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		setActionBarForwardButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), CheckoutActivity.class));
			}
		});

	}

	private void initViews() {

		mMealImageImageView = (ImageView) findViewById(R.id.imageviewMealsDetailsActivity);
		mMealDescriptionTextView = (TextView) findViewById(R.id.textviewMealDetailsActivityDescription);
		mMealReceiptTextView = (TextView) findViewById(R.id.textviewMealDetailsActivityReceiptText);
		mMealIngridientsTextView = (TextView) findViewById(R.id.textviewMealDetailsActivityIngridients);
		mOrderLargePriceTextView = (TextView) findViewById(R.id.textviewMealDetailsActivityOrderLargePrice);
		mOrderSmallPriceTextView = (TextView) findViewById(R.id.textviewMealDetailsActivityOrderSmallPrice);
		mOrderLargeQuantityTextView = (TextView) findViewById(R.id.textviewMealDetailsActivityOrderLargeQuantity);
		mOrderSmallQuantityTextView = (TextView) findViewById(R.id.textviewMealDetailsActivityOrderSmallQuantity);
		mOrderSmallPlusImageButton = (ImageButton) findViewById(R.id.imagebuttonMealDetailsActivityOrderSmallAddOrder);
		mOrderSmallMinusImageButton = (ImageButton) findViewById(R.id.imagebuttonMealDetailsActivityOrderSmallRemoveOrder);
		mOrderBigPlusImageButton = (ImageButton) findViewById(R.id.imagebuttonMealDetailsActivityOrderLargeAddOrder);
		mOrderBigMinusImageButton = (ImageButton) findViewById(R.id.imagebuttonMealDetailsActivityOrderLargeRemoveOrder);
		mGetReceiptImageButton = (ImageButton) findViewById(R.id.imagebuttonMealDetailsActivityGetReceipt);

		mOrderSmallMinusImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				removeOneSmallMealOrderAction();
			}
		});

		mOrderSmallPlusImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				addOneSmallMealOrderAction();
			}
		});

		mOrderBigMinusImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				removeOneBigOrderAction();
			}
		});

		mOrderBigPlusImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				addOneBigMealOrderAction();
			}
		});

		mGetReceiptImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getReceiptAction();
			}
		});

	}

	private void removeOneSmallMealOrderAction() {
		Toast.makeText(getApplicationContext(), "removeOneSmallMealOrderAction", Toast.LENGTH_SHORT).show();
	}

	private void addOneSmallMealOrderAction() {
		Toast.makeText(getApplicationContext(), "addOneSmallMealOrderAction", Toast.LENGTH_SHORT).show();
	}

	private void removeOneBigOrderAction() {
		Toast.makeText(getApplicationContext(), "removeOneBigOrderAction", Toast.LENGTH_SHORT).show();
	}

	private void addOneBigMealOrderAction() {
		Toast.makeText(getApplicationContext(), "addOneBigMealOrderAction", Toast.LENGTH_SHORT).show();
	}

	private void getReceiptAction() {
		Toast.makeText(getApplicationContext(), "getReceiptAction", Toast.LENGTH_SHORT).show();
	}

}
