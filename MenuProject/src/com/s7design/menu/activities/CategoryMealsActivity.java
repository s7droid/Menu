package com.s7design.menu.activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.s7design.menu.R;
import com.s7design.menu.app.Menu;
import com.s7design.menu.dataclasses.Category;
import com.s7design.menu.volley.VolleySingleton;
import com.s7design.menu.volley.requests.GetCategoriesRequest;
import com.s7design.menu.volley.responses.GetCategoriesResponse;

/**
 * Activity for presenting all meal categories from one restaurant. <br>
 * Categories are sorted, and implemented into grid view list.
 * 
 * @author s7Design
 *
 */
public class CategoryMealsActivity extends BaseActivity {

	public static final String INTENT_EXTRA_CATEGORY_TAG = "category_tag";

	// VIEWS
	private GridView mCategoryGridView;
	// DATA
	private ArrayList<Category> categories;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_meals);

		initActionBar();

		Map<String, String> params = new HashMap<String, String>();
		params.put("major", "1");
		params.put("minor", "1");
		params.put("lang", "en");

		GetCategoriesRequest request = new GetCategoriesRequest(this, params, new Listener<GetCategoriesResponse>() {

			@Override
			public void onResponse(GetCategoriesResponse categories) {

				CategoryMealsActivity.this.categories = new ArrayList<Category>(Arrays.asList(categories.categories));
				Menu.getInstance().getDataManager().setCategoriesList(CategoryMealsActivity.this.categories);

				initViews();
				dismissProgressDialog();

			}
		});

		showProgressDialogLoading();
		VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

	}

	/**
	 * Method for initializing all views in {@link CategoryMealsActivity}
	 */
	private void initViews() {

		mCategoryGridView = (GridView) findViewById(R.id.gridviewCategoryMealsActivity);
		mCategoryGridView.setAdapter(new CategoriesAdapter(CategoryMealsActivity.this));

		mCategoryGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(Menu.getContext(), OrderMealsActivity.class);
				intent.putExtra(INTENT_EXTRA_CATEGORY_TAG, categories.get(position).tag);
				startActivity(intent);
			}
		});

	}

	private void initActionBar() {
		setActionBarBackButtonVisibility(View.INVISIBLE);
		setActionBarForwardButtonText(R.string.action_bar_checkout);

		setActionBarForwardButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Menu.getInstance().getDataManager().getCheckoutList() != null)
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
	}

	class CategoriesAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
		private ImageLoader imageLoader;

		public CategoriesAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
			imageLoader = VolleySingleton.getInstance(getApplicationContext()).getImageLoader();
		}

		@Override
		public int getCount() {
			return categories.size();
		}

		@Override
		public Category getItem(int position) {
			return categories.get(position);
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

				holder.categoryImage = (NetworkImageView) convertView.findViewById(R.id.imageviewCategoryMealsGridViewItemImage);
				holder.categoryTitle = (TextView) convertView.findViewById(R.id.textviewCategoryMealsGridViewItemTitle);

				convertView.setTag(holder);
			}

			ViewHolder holder = (ViewHolder) convertView.getTag();

			holder.categoryImage.setImageUrl(getItem(position).image, imageLoader);
			holder.categoryTitle.setText(getItem(position).name);

			return convertView;
		}

		class ViewHolder {
			TextView categoryTitle;
			NetworkImageView categoryImage;
		}

	}

}
