package com.s7design.menu.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.s7design.menu.R;
import com.s7design.menu.activities.MealDetailsActivity;

public class CustomMenuMealCategorySubTypeExpandable extends LinearLayout implements OnClickListener{

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
		
		View view = inflate(mGlobalContext, R.layout.activity_meal_sub_item, this);
		
		final Button subCategoryButton = (Button) view.findViewById(R.id.buttonOrderMealActivitySubItem);
		final LinearLayout mealsListView = (LinearLayout) view.findViewById(R.id.listviewOrderMealActivitySubItemsList);

		subCategoryButton.setText(mSubclassTitle);
		
		for (int i = 0; i< mMeals.length; i++) {
			  LinearLayout vi = (LinearLayout) LayoutInflater.from(mGlobalContext).inflate(R.layout.column_meal_subitem, null);
			  Button large = (Button) vi.findViewById(R.id.buttonSubMealOrderLarge);
			  Button small = (Button) vi.findViewById(R.id.buttonSubMealOrderSmall);
			  RelativeLayout imageContainer = (RelativeLayout) vi.findViewById(R.id.linearlayoutSubMealImageContainer);
			  
			  final int ii = i;
			  
			  large.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Toast.makeText(mGlobalContext, "Clicked large " + ii, Toast.LENGTH_SHORT).show();
				}
			});
			  small.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Toast.makeText(mGlobalContext, "Clicked small " + ii, Toast.LENGTH_SHORT).show();

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
				if(!isMealsListOpen)
					mealsListView.setVisibility(View.VISIBLE);
				else
					mealsListView.setVisibility(View.GONE);
				
				isMealsListOpen = !isMealsListOpen;
			}
		});
		invalidate();
		requestLayout();
	}

//	private class MealsAdapter extends BaseAdapter {
//
//		private String[] mItems;
//		private LayoutInflater mInflater;
//
//		public MealsAdapter(String[] meals) {
//			mItems = meals;
//			mInflater = LayoutInflater.from(mGlobalContext);
//		}
//
//		@Override
//		public int getCount() {
//			return mItems.length;
//		}
//
//		@Override
//		public Object getItem(int position) {
//			return null;
//		}
//
//		@Override
//		public long getItemId(int position) {
//			return 0;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//
//			if (convertView == null) {
//
//				convertView = mInflater.inflate(R.layout.column_meal_subitem, null);
//
//				ViewHolder holder = new ViewHolder();
//
//				holder.mealImage = (ImageView) convertView.findViewById(R.id.imageviewSubMealImage);
//				holder.mealName = (TextView) convertView.findViewById(R.id.textviewSubMealTitle);
//				holder.mealPrice = (TextView) convertView.findViewById(R.id.textviewSubMealPrice);
//				holder.orderLarge = (Button) convertView.findViewById(R.id.buttonSubMealOrderLarge);
//				holder.orderSmall = (Button) convertView.findViewById(R.id.buttonSubMealOrderSmall);
//
//				convertView.setTag(holder);
//			}
//
//			ViewHolder holder = (ViewHolder) convertView.getTag();
//
//			holder.mealName.setText(mItems[position]);
//			holder.mealImage.setImageResource(R.drawable.meal_category_01);
//
//			return convertView;
//		}
//
//		class ViewHolder {
//			ImageView mealImage;
//			TextView mealName;
//			TextView mealPrice;
//			Button orderLarge;
//			Button orderSmall;
//		}
//
//	}

	@Override
	public void onClick(View v) {}

}
