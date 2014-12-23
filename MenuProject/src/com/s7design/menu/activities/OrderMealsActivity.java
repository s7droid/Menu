package com.s7design.menu.activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.android.volley.Response.Listener;
import com.s7design.menu.R;
import com.s7design.menu.app.Menu;
import com.s7design.menu.dataclasses.Category;
import com.s7design.menu.dataclasses.Item;
import com.s7design.menu.utils.CustomMenuMealCategorySubTypeExpandable;
import com.s7design.menu.volley.VolleySingleton;
import com.s7design.menu.volley.requests.GetAllItemsInCategoryRequest;
import com.s7design.menu.volley.responses.GetAllItemsInCategoryResponse;

public class OrderMealsActivity extends BaseActivity {

	// VIEWS
	private LinearLayout mContainer;

	private ArrayList<Item> items;

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

	private void initViews() {

		mContainer = (LinearLayout) findViewById(R.id.scrollviewOrderMealsActivityLinearContainer);

	}

	private void initActionBar() {
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
				if (Menu.getInstance().getDataManager().getCheckoutList() != null)
					startActivity(new Intent(getApplicationContext(), CheckoutActivity.class));
				else
					showAlertDialog("Alert", "Your checkout list is empty. Add some things to Your chart.");
			}
		});

	}

	private void initData() {

		Map<String, CustomMenuMealCategorySubTypeExpandable> viewMap = new HashMap<String, CustomMenuMealCategorySubTypeExpandable>();
		ArrayList<String> catList = new ArrayList<String>();

		for (Item item : items) {
			if (!viewMap.containsKey(item.category)) {
				viewMap.put(item.category, new CustomMenuMealCategorySubTypeExpandable(this));
			}

			if (item.image.equals("subcat")) {
				viewMap.get(item.category).setTitle(item.name);
				catList.add(item.category);
			} else {
				viewMap.get(item.category).addItem(item);
			}
		}

		for (String cat : catList) {
			mContainer.addView(viewMap.get(cat).init());
		}

	}

}
