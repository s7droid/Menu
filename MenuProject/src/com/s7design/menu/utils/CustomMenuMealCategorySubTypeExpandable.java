package com.s7design.menu.utils;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.s7design.menu.R;
import com.s7design.menu.activities.MealDetailsActivity;
import com.s7design.menu.app.Menu;
import com.s7design.menu.dataclasses.Item;

public class CustomMenuMealCategorySubTypeExpandable extends LinearLayout {

	private static final String TAG = CustomMenuMealCategorySubTypeExpandable.class.getSimpleName();

	private Context mGlobalContext;
	private String mSubclassTitle;
	private boolean isMealsListOpen = true;
	private ArrayList<Item> items;
	private String currency;

	public CustomMenuMealCategorySubTypeExpandable(Context context) {
		super(context);

		items = new ArrayList<Item>();

		mGlobalContext = context;
		currency = Menu.getInstance().getDataManager().getCurrency();

		// init();

	}

	public void setTitle(String title) {
		mSubclassTitle = title;
	}

	public void addItem(Item item) {

		items.add(item);
	}

	/**
	 * Method for initializing all needed parameters and views for this custom
	 * component.
	 */
	public CustomMenuMealCategorySubTypeExpandable init() {

		View view = inflate(mGlobalContext, R.layout.activity_meal_sub_item, this);

		final Button subCategoryButton = (Button) view.findViewById(R.id.buttonOrderMealActivitySubItem);
		final LinearLayout mealsListView = (LinearLayout) view.findViewById(R.id.listviewOrderMealActivitySubItemsList);

		subCategoryButton.setText(mSubclassTitle);

		for (int i = 0; i < items.size(); i++) {
			Integer smallMealCounter = 0;
			Integer largeMealCounter = 0;
			final Item itemToSend = items.get(i);
			final double largePrice = items.get(i).largeprice;
			final double smallPrice = items.get(i).smallprice;

			LinearLayout vi = (LinearLayout) LayoutInflater.from(mGlobalContext).inflate(R.layout.column_meal_subitem, null);
			Button large = (Button) vi.findViewById(R.id.buttonSubMealOrderLarge);
			Button small = (Button) vi.findViewById(R.id.buttonSubMealOrderSmall);
			final TextView bigOrderPriceAndQuantity = (TextView) vi.findViewById(R.id.textviewSubMealBigPrice);
			final TextView smallOrderPriceAndQuantity = (TextView) vi.findViewById(R.id.textviewSubMealSmallPrice);
			TextView mealName = (TextView) vi.findViewById(R.id.textviewSubMealTitle);
			RelativeLayout imageContainer = (RelativeLayout) vi.findViewById(R.id.linearlayoutSubMealImageContainer);

			mealName.setText(items.get(i).name);
			bigOrderPriceAndQuantity.setText(String.format("%.2f", items.get(i).largeprice) + currency);
			smallOrderPriceAndQuantity.setText(String.format("%.2f", items.get(i).smallprice) + currency);

			large.setTag(largeMealCounter);
			small.setTag(smallMealCounter);
			large.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int counter = (Integer) v.getTag();
					counter++;
					if (counter > 0) {
						bigOrderPriceAndQuantity.setTextColor(getResources().getColor(R.color.menu_main_orange));
						bigOrderPriceAndQuantity.setTypeface(null, Typeface.BOLD);
						bigOrderPriceAndQuantity.setAlpha(1.0f);
					} else {
						bigOrderPriceAndQuantity.setTextColor(getResources().getColor(R.color.menu_main_gray));
						bigOrderPriceAndQuantity.setAlpha(0.5f);
					}

					bigOrderPriceAndQuantity.setText(String.format("%.2f", largePrice) + currency + " (" + String.valueOf(counter) + ")");
					v.setTag(counter);
					Menu.getInstance().getDataManager().addCheckoutListItem(itemToSend.getLarge());
				}
			});

			small.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int counter = (Integer) v.getTag();
					counter++;

					if (counter > 0) {
						smallOrderPriceAndQuantity.setTextColor(getResources().getColor(R.color.menu_main_gray));
						smallOrderPriceAndQuantity.setTypeface(null, Typeface.BOLD);
						smallOrderPriceAndQuantity.setAlpha(1.0f);
					} else {
						smallOrderPriceAndQuantity.setTextColor(getResources().getColor(R.color.menu_main_gray));
						smallOrderPriceAndQuantity.setAlpha(0.5f);
					}

					smallOrderPriceAndQuantity.setText(String.format("%.2f", smallPrice) + currency + " (" + String.valueOf(counter) + ")");
					v.setTag(counter);
					Menu.getInstance().getDataManager().addCheckoutListItem(itemToSend.getSmall());
				}
			});

			imageContainer.setTag(i);
			imageContainer.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mGlobalContext, MealDetailsActivity.class);
					intent.putExtra(MealDetailsActivity.INTENT_EXTRA_TAG, items.get((Integer) v.getTag()).largetag);
					mGlobalContext.startActivity(intent);
				}
			});

			if (i == items.size() - 1)
				vi.findViewById(R.id.viewDivider).setVisibility(View.GONE);

			mealsListView.addView(vi);
		}

		subCategoryButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!isMealsListOpen)
					mealsListView.setVisibility(View.VISIBLE);
				else
					mealsListView.setVisibility(View.GONE);

				isMealsListOpen = !isMealsListOpen;
			}
		});
		invalidate();
		requestLayout();

		return this;
	}

}
