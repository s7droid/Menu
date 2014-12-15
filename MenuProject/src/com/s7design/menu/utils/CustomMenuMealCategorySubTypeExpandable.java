package com.s7design.menu.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.s7design.menu.R;

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

//		Button title = new Button(mGlobalContext);
//		final ListView listview = new ListView(mGlobalContext);
//
//		title.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//		title.setText(mSubclassTitle);
//		title.setBackgroundColor(getResources().getColor(R.color.menu_main_orange));
//		title.setTypeface(null, Typeface.BOLD);
//		listview.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//		listview.setAdapter(new MealsAdapter(mMeals));
//		listview.setVisibility(View.GONE);
//
//		addView(title);
		
		View view = inflate(mGlobalContext, R.layout.column_meal_subitem, this);
		
//		Button subCategoryButton = (Button) view.findViewById(R.id.);
//		ListView mealsListView = (ListView) view.findViewById(R.id.);
//
//		subCategoryButton.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if(!mealsListView)
//					mealsListView.setVisibility(View.VISIBLE);
//				else
//					mealsListView.setVisibility(View.GONE);
//			}
//		});
		invalidate();
		requestLayout();
	}

	private class MealsAdapter extends BaseAdapter {

		private String[] mItems;
		private LayoutInflater mInflater;

		public MealsAdapter(String[] meals) {
			mItems = meals;
			mInflater = LayoutInflater.from(mGlobalContext);
		}

		@Override
		public int getCount() {
			return mItems.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {

				convertView = mInflater.inflate(R.layout.column_meal_subitem, null);

				ViewHolder holder = new ViewHolder();

				holder.mealImage = (ImageView) convertView.findViewById(R.id.imageviewSubMealImage);
				holder.mealName = (TextView) convertView.findViewById(R.id.textviewSubMealTitle);
				holder.mealPrice = (TextView) convertView.findViewById(R.id.textviewSubMealPrice);
				holder.orderLarge = (Button) convertView.findViewById(R.id.buttonSubMealOrderLarge);
				holder.orderSmall = (Button) convertView.findViewById(R.id.buttonSubMealOrderSmall);

				convertView.setTag(holder);
			}

			ViewHolder holder = (ViewHolder) convertView.getTag();

			holder.mealName.setText(mItems[position]);
			holder.mealImage.setImageResource(R.drawable.meal_category_01);

			return convertView;
		}

		class ViewHolder {
			ImageView mealImage;
			TextView mealName;
			TextView mealPrice;
			Button orderLarge;
			Button orderSmall;
		}

	}

}
