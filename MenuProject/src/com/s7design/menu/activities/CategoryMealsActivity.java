package com.s7design.menu.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.s7design.menu.R;
import com.s7design.menu.app.Menu;

/**
 * Activity for presenting all meal categories from one restaurant. <br>
 * Categories are sorted, and implemented into grid view list.
 * 
 * @author s7Design
 *
 */
public class CategoryMealsActivity extends BaseActivity{

	// VIEWS
	private GridView mCategoryGridView;
	// DATA
	private String[] mCategoryTitles = { "Meal1", "Meal2", "Meal3", "Meal4", "Meal5", "Meal6", "Meal7", "Meal8", "Meal9", "Meal10", "Meal11", "Meal12", "Meal13", "Meal14", "Meal15", "Meal16",
			"Meal17", "Meal18", "Meal19", "Meal20" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_meals);
		
		initViews();
	}

	/**
	 * Method for initializing all views in {@link CategoryMealsActivity}
	 */
	private void initViews() {
		
		
		mCategoryGridView = (GridView) findViewById(R.id.gridviewCategoryMealsActivity);
		mCategoryGridView.setAdapter(new CategoriesAdapter(CategoryMealsActivity.this, mCategoryTitles));

		setActionBarBackButtonVisibility(View.INVISIBLE);
		setActionBarForwardButtonText(R.string.action_bar_checkout);
		
		setActionBarForwardButtonOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(Menu.getInstance().getDataManager().getTestCheckoutList() != null)
					startActivity(new Intent(getApplicationContext(), CheckoutActivity.class));
				else
					showAlertDialog("Alert", "Your checkout list is empty. Add some things to Your chart.");
			}
		});
		
		setActionBarBackButtonOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		
		mCategoryGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startActivity(new Intent(Menu.getContext(), OrderMealsActivity.class));
			}
		});
		
	}

	class CategoriesAdapter extends BaseAdapter {

		private Context mContext;
		private String[] mItems;
		private LayoutInflater mInflater;

		public CategoriesAdapter(Context context, String[] items) {
			mContext = context;
			mItems = items;
			mInflater = LayoutInflater.from(context);
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

				convertView = mInflater.inflate(R.layout.grid_item_meals_category, null);

				ViewHolder holder = new ViewHolder();

				holder.categoryImage = (ImageView) convertView.findViewById(R.id.imageviewCategoryMealsGridViewItemImage);
				holder.categoryTitle = (TextView) convertView.findViewById(R.id.textviewCategoryMealsGridViewItemTitle);

				convertView.setTag(holder);
			}

			ViewHolder holder = (ViewHolder) convertView.getTag();

			if (position % 2 == 0) {
				System.out.println("Meal category 1");
				holder.categoryImage.setImageDrawable(getResources().getDrawable(R.drawable.meal_category_01));
			} else {
				System.out.println("Meal category 2");
				holder.categoryImage.setImageDrawable(getResources().getDrawable(R.drawable.meal_category_02));
			}

			holder.categoryTitle.setText(mItems[position]);

			return convertView;
		}

		class ViewHolder {
			TextView categoryTitle;
			ImageView categoryImage;
		}

	}

}
