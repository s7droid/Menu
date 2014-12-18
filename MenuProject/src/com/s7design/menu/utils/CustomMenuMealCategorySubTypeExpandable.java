package com.s7design.menu.utils;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.s7design.menu.R;
import com.s7design.menu.activities.MealDetailsActivity;
import com.s7design.menu.app.Menu;
import com.s7design.menu.dataclasses.DataManager;
import com.s7design.menu.dataclasses.Item;

public class CustomMenuMealCategorySubTypeExpandable extends LinearLayout {

	private Context mGlobalContext;
	private String mSubclassTitle;
	private String[] mMeals;
	private boolean isMealsListOpen = false;

	public CustomMenuMealCategorySubTypeExpandable(Context context, String subclassTitle, String[] meals) {
		super(context);

		mGlobalContext = context;
		mSubclassTitle = subclassTitle;
		mMeals = meals;

		init();

	}

	/**
	 * Method for initializing all needed parameters and views for this custom
	 * component.
	 */
	private void init() {

		View view = inflate(mGlobalContext, R.layout.activity_meal_sub_item, this);

		ArrayList<Item> items = new ArrayList<Item>();
		
		Item item = new Item();
		item.name = "Tomato soup";
		item.largeprice = 12.00f;
		item.smallprice = 8.00f;
		
		Item item2 = new Item();
		item2.name = "Orange chicken";
		item2.largeprice = 10.50f;
		item2.smallprice = 7.00f;
		
		Item item3 = new Item();
		item3.name = "Pork chops";
		item3.largeprice = 15.00f;
		item3.smallprice = 8.50f;

		items.add(item);
		items.add(item2);
		items.add(item3);
		
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
			bigOrderPriceAndQuantity.setText(items.get(i).largeprice + getResources().getString(R.string.misc_euro));
			smallOrderPriceAndQuantity.setText(items.get(i).smallprice + getResources().getString(R.string.misc_euro));
			
			large.setTag(largeMealCounter);
			small.setTag(smallMealCounter);
			large.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int counter = (Integer) v.getTag();
					counter++;
					if(counter > 0){
						bigOrderPriceAndQuantity.setTextColor(getResources().getColor(R.color.menu_main_orange));
						bigOrderPriceAndQuantity.setTypeface(null, Typeface.BOLD);
						bigOrderPriceAndQuantity.setAlpha(1.0f);
					}else{
						bigOrderPriceAndQuantity.setTextColor(getResources().getColor(R.color.menu_main_gray));
						bigOrderPriceAndQuantity.setAlpha(0.5f);
					}
					
					bigOrderPriceAndQuantity.setText(largePrice + getResources().getString(R.string.misc_euro) + " (" + String.valueOf(counter) + ")");
					v.setTag(counter);
					itemToSend.quantityLarge = 1;
					itemToSend.quantitySmall = 0;
					Menu.getInstance().getDataManager().addCheckoutListItem(itemToSend);
				}
			});
			
			small.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int counter = (Integer) v.getTag();
					counter++;
					
					if(counter > 0){
						smallOrderPriceAndQuantity.setTextColor(getResources().getColor(R.color.menu_main_gray));
						smallOrderPriceAndQuantity.setTypeface(null, Typeface.BOLD);
						smallOrderPriceAndQuantity.setAlpha(1.0f);
					}else{
						smallOrderPriceAndQuantity.setTextColor(getResources().getColor(R.color.menu_main_gray));
						smallOrderPriceAndQuantity.setAlpha(0.5f);
					}
					
					smallOrderPriceAndQuantity.setText(smallPrice + getResources().getString(R.string.misc_euro) + " (" + String.valueOf(counter) + ")");
					v.setTag(counter);
					itemToSend.quantityLarge = 0;
					itemToSend.quantitySmall = 1;
					Menu.getInstance().getDataManager().addCheckoutListItem(itemToSend);
				}
			});

			imageContainer.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					mGlobalContext.startActivity(new Intent(mGlobalContext, MealDetailsActivity.class));
				}
			});

			mealsListView.addView(vi);
		}

		mealsListView.setVisibility(View.GONE);

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
	}

}
