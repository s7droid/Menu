package com.s7design.menu.activities;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.android.volley.toolbox.NetworkImageView;
import com.s7design.menu.R;
import com.s7design.menu.dataclasses.Item;
import com.s7design.menu.volley.VolleySingleton;
import com.s7design.menu.volley.requests.GetItemInfoRequest;
import com.s7design.menu.volley.responses.GetItemInfoResponse;

public class MealDetailsActivity extends BaseActivity {

	private static final String TAG = MealDetailsActivity.class.getSimpleName();

	public static final String INTENT_EXTRA_TAG = "tag";

	// VIEWS
	private NetworkImageView mMealImageImageView;
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

	private Item item;

	// DATA

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meal_details);

		initActionBar();
		initViews();
		initData();
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

		mMealImageImageView = (NetworkImageView) findViewById(R.id.imageviewMealsDetailsActivity);
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
			}
		});

		mOrderSmallPlusImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			}
		});

		mOrderBigMinusImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			}
		});

		mOrderBigPlusImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			}
		});

		mGetReceiptImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			}
		});

	}

	private void initData() {

		Map<String, String> params = new HashMap<String, String>();
		params.put("major", "1");
		params.put("minor", "1");
		params.put("itemtag", String.valueOf(getIntent().getIntExtra(INTENT_EXTRA_TAG, 0)));
		params.put("lang", "en");

		GetItemInfoRequest request = new GetItemInfoRequest(this, params, new Listener<GetItemInfoResponse>() {

			@Override
			public void onResponse(GetItemInfoResponse response) {

				if (response != null && response.item.length > 0) {
					item = response.item[0];
					setData();
				}

				dismissProgressDialog();

			}
		});

		showProgressDialogLoading();
		VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
	}

	private void setData() {

		// "item":[{"category":"BEVERAGES","image":"http:\/\/usemenu.com\/images\/image.png","name":"Mineral
		// Water without Gas","description":"Fresh mineral water from the swiss
		// alps.","ingredients":"SWISS ALPINE
		// WATER","smalltag":"3","largetag":"4","smallprice":"3.50","largeprice":"7.00","smalllabel":"GLASS","largelabel":"BOTTLE","currency":"\u20ac"}],"errordata":"none"}

		mMealImageImageView.setImageUrl(item.image, VolleySingleton.getInstance(getApplicationContext()).getImageLoader());
		mMealDescriptionTextView.setText(item.description);
		mMealIngridientsTextView.setText(item.ingredients);
		mOrderSmallPriceTextView.setText(String.valueOf(item.smallprice));
		mOrderLargePriceTextView.setText(String.valueOf(item.largeprice));

	}

}
