package com.s7design.menu.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.s7design.menu.R;
import com.s7design.menu.activities.MealDetailsActivity;

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

		final Button subCategoryButton = (Button) view.findViewById(R.id.buttonOrderMealActivitySubItem);
		final LinearLayout mealsListView = (LinearLayout) view.findViewById(R.id.listviewOrderMealActivitySubItemsList);

		subCategoryButton.setText(mSubclassTitle);

		for (int i = 0; i < mMeals.length; i++) {
			Integer smallMealCounter = 0;
			Integer largeMealCounter = 0;
			LinearLayout vi = (LinearLayout) LayoutInflater.from(mGlobalContext).inflate(R.layout.column_meal_subitem, null);
			Button large = (Button) vi.findViewById(R.id.buttonSubMealOrderLarge);
			Button small = (Button) vi.findViewById(R.id.buttonSubMealOrderSmall);
			final TextView bigOrderPriceAndQuantity = (TextView) vi.findViewById(R.id.textviewSubMealBigPrice);
			final TextView smallOrderPriceAndQuantity = (TextView) vi.findViewById(R.id.textviewSubMealSmallPrice);
			RelativeLayout imageContainer = (RelativeLayout) vi.findViewById(R.id.linearlayoutSubMealImageContainer);

			bigOrderPriceAndQuantity.setText("12.50" + getResources().getString(R.string.misc_euro));
			smallOrderPriceAndQuantity.setText(" / 7.00" + getResources().getString(R.string.misc_euro));
			
			large.setTag(largeMealCounter);
			small.setTag(smallMealCounter);
			large.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int counter = (Integer) v.getTag();
					counter++;
					if(counter > 0){
						bigOrderPriceAndQuantity.setTextColor(getResources().getColor(R.color.menu_main_orange));
						bigOrderPriceAndQuantity.setAlpha(1.0f);
					}else{
						bigOrderPriceAndQuantity.setTextColor(getResources().getColor(R.color.menu_main_gray));
						bigOrderPriceAndQuantity.setAlpha(0.5f);
					}
					
					bigOrderPriceAndQuantity.setText("12.50" + getResources().getString(R.string.misc_euro) + " (" + String.valueOf(counter) + ")");
					v.setTag(counter);
				}
			});
			
			small.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int counter = (Integer) v.getTag();
					counter++;
					smallOrderPriceAndQuantity.setText(" / 7.00" + getResources().getString(R.string.misc_euro) + " (" + String.valueOf(counter) + ")");
					v.setTag(counter);
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
