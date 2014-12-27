package com.s7design.menu.activities;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.s7design.menu.R;
import com.s7design.menu.app.Menu;
import com.s7design.menu.dataclasses.Receipt;

public class ReceiptListActivity extends BaseActivity {

	// VIEWS
	private ListView mListViewReceipts;

	// DATA
	private ArrayList<Receipt> receipts;
	public static final String RECEIPT_LIST_ITEM_SELECTED = "receipt_selected";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_receipt_list);

		initActionBar();
		initViews();
		initData();
	}

	private void initActionBar() {

		setActionBarForwardArrowVisibility(null);
		setActionBarForwardButtonText("RECEIPTS");
		setActionBarForwardButtonTextColor(getResources().getColor(R.color.menu_main_orange));

		setActionBarForwardButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		setActionBarBackButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	private void initViews() {
		mListViewReceipts = (ListView) findViewById(R.id.listviewReceiptListActivityList);
	}

	private void initData() {
		receipts = new ArrayList<Receipt>();
		for (int i = 0; i < 5; i++) {
			Receipt receipt = new Receipt();
			receipt.setAmmount("14.50");
			receipt.setDate("22.03.2014");
			receipt.setReceiptId("sadsadsaaYUDT5361bjdhsaj321bjdsa84131==");
			receipt.setRestaurantName("Example restaurant name");

			receipts.add(receipt);
		}
		mListViewReceipts.setAdapter(new ReceiptListAdapter(receipts));
	}

	class ReceiptListAdapter extends BaseAdapter {

		private ArrayList<Receipt> items;

		public ReceiptListAdapter(ArrayList<Receipt> receipts) {
			items = receipts;
		}

		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public Receipt getItem(int position) {
			return items.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				ViewHolder holder = new ViewHolder();

				convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.column_receipt_list_item, null);

				holder.date = (TextView) convertView.findViewById(R.id.textviewReceiptListItemDate);
				holder.price = (TextView) convertView.findViewById(R.id.textviewReceiptListItemPrice);
				holder.restaurantName = (TextView) convertView.findViewById(R.id.textviewReceiptListItemRestaurantName);
				holder.sendEmail = (Button) convertView.findViewById(R.id.buttonReceiptListItemSendMessage);
				holder.separator = (View) convertView.findViewById(R.id.viewReceiptListItemSeparator);

				convertView.setTag(holder);
			}

			ViewHolder holder = (ViewHolder) convertView.getTag();
			final Receipt item = getItem(position);

			holder.date.setText(item.getDate());
			holder.price.setText(Menu.getInstance().getDataManager().getCurrency() + item.getAmmount());
			holder.restaurantName.setText(item.getRestaurantName());

			if (position == (getCount() - 1)) {
				holder.separator.setVisibility(View.INVISIBLE);
			} else {
				holder.separator.setVisibility(View.VISIBLE);
			}

			holder.sendEmail.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getApplicationContext(),ReceiptDetailsActivity.class);
					intent.putExtra(RECEIPT_LIST_ITEM_SELECTED, item);
					startActivity(intent);
				}
			});

			return convertView;
		}

		class ViewHolder {
			TextView date;
			TextView price;
			TextView restaurantName;
			Button sendEmail;
			View separator;

		}

	}

}
