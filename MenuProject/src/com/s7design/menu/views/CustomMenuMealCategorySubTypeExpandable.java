package com.s7design.menu.views;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.s7design.menu.R;
import com.s7design.menu.activities.BaseActivity;
import com.s7design.menu.activities.MealDetailsActivity;
import com.s7design.menu.app.Menu;
import com.s7design.menu.dataclasses.Item;
import com.s7design.menu.volley.VolleySingleton;

public class CustomMenuMealCategorySubTypeExpandable extends LinearLayout {

	private static final String TAG = CustomMenuMealCategorySubTypeExpandable.class.getSimpleName();

	private Context mGlobalContext;
	private String mSubclassTitle;
	private boolean isMealsListOpen = true;
	private ArrayList<Item> items;
	private String currency;
	private ImageLoader imageLoader;

	public CustomMenuMealCategorySubTypeExpandable(Context context) {
		super(context);

		items = new ArrayList<Item>();

		mGlobalContext = context;
		currency = Menu.getInstance().getDataManager().getCurrency();

		imageLoader = VolleySingleton.getInstance(context).getImageLoader();

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

		final RelativeLayout subCategoryButton = (RelativeLayout) view.findViewById(R.id.buttonOrderMealActivitySubItem);
		final TextView textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
		final ImageView imageViewArrow = (ImageView) view.findViewById(R.id.imageViewArrow);
		final LinearLayout mealsListView = (LinearLayout) view.findViewById(R.id.listviewOrderMealActivitySubItemsList);

		textViewTitle.setText(mSubclassTitle);

		for (int i = 0; i < items.size(); i++) {
			final Item itemToSend = items.get(i);

			LinearLayout vi = (LinearLayout) LayoutInflater.from(mGlobalContext).inflate(R.layout.column_meal_subitem, null);
			NetworkImageView imageView = (NetworkImageView) vi.findViewById(R.id.imageviewSubMealImage);
			Button large = (Button) vi.findViewById(R.id.buttonSubMealOrderLarge);
			Button small = (Button) vi.findViewById(R.id.buttonSubMealOrderSmall);
			final TextView bigOrderPriceAndQuantity = (TextView) vi.findViewById(R.id.textviewSubMealBigPrice);
			TextView textViewDash = (TextView) vi.findViewById(R.id.textViewDash);
			final TextView smallOrderPriceAndQuantity = (TextView) vi.findViewById(R.id.textviewSubMealSmallPrice);
			TextView mealName = (TextView) vi.findViewById(R.id.textviewSubMealTitle);
			RelativeLayout imageContainer = (RelativeLayout) vi.findViewById(R.id.linearlayoutSubMealImageContainer);

			imageView.setImageUrl(itemToSend.image, imageLoader);

			mealName.setText(items.get(i).name);

			Item it = Menu.getInstance().getDataManager().getItemByTag(itemToSend.largetag);

			if (it != null) {

				itemToSend.quantityLarge = it.quantityLarge;
				itemToSend.quantitySmall = it.quantitySmall;

				if (itemToSend.quantityLarge > 0) {
					setQuantity(bigOrderPriceAndQuantity, itemToSend.largeprice, itemToSend.quantityLarge);
				}
				if (itemToSend.quantitySmall > 0) {
					setQuantity(smallOrderPriceAndQuantity, itemToSend.smallprice, itemToSend.quantitySmall);
				}
			}

			bigOrderPriceAndQuantity.setText(currency + String.format("%.2f", items.get(i).largeprice));
			large.setText(mGlobalContext.getString(R.string.category_meals_add) + " " + itemToSend.largelabel);
			large.setTag(itemToSend.quantityLarge);
			large.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if (Menu.getInstance().getDataManager().isCheckoutListEmpty())
						((BaseActivity) getContext()).showRightActionBarButton();

					int counter = (Integer) v.getTag();
					counter++;
					setQuantity(bigOrderPriceAndQuantity, itemToSend.largeprice, counter);
					v.setTag(counter);
					Item item = Menu.getInstance().getDataManager().getItemByTag(itemToSend.largetag);
					if (item == null)
						item = itemToSend;
					Menu.getInstance().getDataManager().addCheckoutListItem(item.getLarge());

					if (!Menu.getInstance().isOrderEnabled())
						((BaseActivity) getContext()).showAlertDialog(R.string.dialog_title_warning, R.string.dialog_unable_to_order);

				}
			});

			if (itemToSend.smalllabel.length() > 0) {
				smallOrderPriceAndQuantity.setText(currency + String.format("%.2f", items.get(i).smallprice));
				small.setText(mGlobalContext.getString(R.string.category_meals_add) + " " + itemToSend.smalllabel);
				small.setTag(itemToSend.quantitySmall);
				small.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						int counter = (Integer) v.getTag();
						counter++;
						setQuantity(smallOrderPriceAndQuantity, itemToSend.smallprice, counter);
						v.setTag(counter);
						Item item = Menu.getInstance().getDataManager().getItemByTag(itemToSend.smalltag);
						if (item == null)
							item = itemToSend;
						Menu.getInstance().getDataManager().addCheckoutListItem(item.getSmall());

						if (!Menu.getInstance().isOrderEnabled())
							((BaseActivity) getContext()).showAlertDialog(R.string.dialog_title_warning, R.string.dialog_unable_to_order);
					}
				});
			} else {
				smallOrderPriceAndQuantity.setVisibility(View.INVISIBLE);
				small.setVisibility(View.INVISIBLE);
				textViewDash.setVisibility(View.INVISIBLE);
			}

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
				if (!isMealsListOpen) {
					mealsListView.setVisibility(View.VISIBLE);
					imageViewArrow.setImageResource(R.drawable.arrow_up);
				} else {
					mealsListView.setVisibility(View.GONE);
					imageViewArrow.setImageResource(R.drawable.arrow_down);
				}

				isMealsListOpen = !isMealsListOpen;
			}
		});
		invalidate();
		requestLayout();

		return this;
	}

	private void setQuantity(TextView tv, double price, int quantity) {

		tv.setTextColor(getResources().getColor(R.color.menu_main_orange));
		tv.setTypeface(null, Typeface.BOLD);
		tv.setAlpha(1.0f);
		tv.setText(String.format("%.2f", price) + currency + " (" + String.valueOf(quantity) + ")");
	}
}
