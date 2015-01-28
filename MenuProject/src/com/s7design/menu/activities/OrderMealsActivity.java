package com.s7design.menu.activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.android.volley.Response.Listener;
import com.s7design.menu.R;
import com.s7design.menu.app.Menu;
import com.s7design.menu.dataclasses.Item;
import com.s7design.menu.views.CustomMenuMealCategorySubTypeExpandable;
import com.s7design.menu.volley.VolleySingleton;
import com.s7design.menu.volley.requests.GetAllItemsInCategoryRequest;
import com.s7design.menu.volley.responses.GetAllItemsInCategoryResponse;

public class OrderMealsActivity extends BaseActivity {

	private static final String TAG = OrderMealsActivity.class.getSimpleName();

	// VIEWS
	private LinearLayout mContainer;

	private ArrayList<Item> items;

	private boolean isOrderListEmpty;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_meals);

		initActionBar();

		int tag = getIntent().getIntExtra(CategoryMealsActivity.INTENT_EXTRA_CATEGORY_TAG, 0);

		Map<String, String> params = new HashMap<String, String>();
		params.put("major", "1");
		params.put("minor", "1");
		params.put("lang", "en");
		params.put("cat", String.valueOf(tag));

		GetAllItemsInCategoryRequest request = new GetAllItemsInCategoryRequest(this, params, new Listener<GetAllItemsInCategoryResponse>() {

			@Override
			public void onResponse(GetAllItemsInCategoryResponse items) {

				OrderMealsActivity.this.items = new ArrayList<Item>(Arrays.asList(items.items));
				Menu.getInstance().getDataManager().setItemsList(OrderMealsActivity.this.items);

				initViews();
				initData();
				dismissProgressDialog();

			}
		});

		showProgressDialogLoading();
		VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (mContainer != null) {
			mContainer.removeAllViews();
			initData();
		}
	}

	private void initViews() {

		mContainer = (LinearLayout) findViewById(R.id.scrollviewOrderMealsActivityLinearContainer);

	}

	private void initActionBar() {
		isOrderListEmpty = Menu.getInstance().getDataManager().getCheckoutList().size() == 0 ? true : false;

		setActionBarForwardButtonText(R.string.action_bar_checkout);

		setActionBarBackButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		setActionBarForwardButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				isOrderListEmpty = Menu.getInstance().getDataManager().getCheckoutList().size() == 0 ? true : false;

				if (isOrderListEmpty) {
					showAlertDialog("", getResources().getString(R.string.error_checkout_list_empty));
					return;
				}

				if (Menu.getInstance().getDataManager().getCheckoutList() != null)
					startActivity(new Intent(getApplicationContext(), CheckoutActivity.class));
			}
		});

	}

	private void initData() {

		ArrayList<CustomMenuMealCategorySubTypeExpandable> viewList = new ArrayList<CustomMenuMealCategorySubTypeExpandable>();

		CustomMenuMealCategorySubTypeExpandable view = null;

		for (Item item : items) {
			if (item.image.equals("subcat")) {
				view = new CustomMenuMealCategorySubTypeExpandable(this);
				view.setTitle(item.name);
				viewList.add(view);
				Log.d(TAG, "subcat " + item.name);
			} else {
				view.addItem(item);
				Log.d(TAG, "item " + item.name);
			}
		}

		for (CustomMenuMealCategorySubTypeExpandable v : viewList) {
			mContainer.addView(v.init());
		}

	}

}
