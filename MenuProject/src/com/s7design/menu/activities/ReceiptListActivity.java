package com.s7design.menu.activities;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.s7design.menu.R;
import com.s7design.menu.app.Menu;
import com.s7design.menu.dataclasses.Receipt;
import com.s7design.menu.dialogs.ProgressDialogFragment;
import com.s7design.menu.utils.Settings;
import com.s7design.menu.volley.VolleySingleton;
import com.s7design.menu.volley.requests.GetReceiptsRequest;
import com.s7design.menu.volley.requests.SendReceiptByEmailRequest;
import com.s7design.menu.volley.responses.GetReceiptsResponse;
import com.s7design.menu.volley.responses.SendReceiptByEmailResponse;

public class ReceiptListActivity extends BaseActivity {

	// VIEWS
	private ListView mListViewReceipts;

	// DATA
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
		setActionBarMenuButtonVisibility(View.GONE);

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

		showProgressDialogLoading();

		Map<String, String> params = new HashMap<String, String>();
		params.put("minor", "1");
		params.put("major", "1");
		params.put("accesstoken", Settings.getAccessToken(ReceiptListActivity.this));

		GetReceiptsRequest request = new GetReceiptsRequest(ReceiptListActivity.this, params, new Listener<GetReceiptsResponse>() {

			@Override
			public void onResponse(GetReceiptsResponse arg0) {
				dismissProgressDialog();

				if (arg0 != null && arg0.receipts != null && arg0.receipts.length > 0) {
					mListViewReceipts.setAdapter(new ReceiptListAdapter(arg0));
				} else {
					showAlertDialog("", getResources().getString(R.string.receipt_list_empty_body), new OnClickListener() {

						@Override
						public void onClick(View v) {
							finish();
						}
					});
				}
			}
		});

		VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

	}

	class ReceiptListAdapter extends BaseAdapter {

		private GetReceiptsResponse items;

		public ReceiptListAdapter(GetReceiptsResponse receipts) {
			items = receipts;
		}

		@Override
		public int getCount() {
			return items.receipts.length;
		}

		@Override
		public Receipt getItem(int position) {
			return items.receipts[position];
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
				holder.container = (RelativeLayout) convertView.findViewById(R.id.relativelayoutReceiptListItemContainer);

				convertView.setTag(holder);
			}

			ViewHolder holder = (ViewHolder) convertView.getTag();
			final Receipt item = getItem(position);

			holder.date.setText(item.date);
			holder.price.setText(Menu.getInstance().getDataManager().getCurrency() + item.amount);
			holder.restaurantName.setText(item.restaurantname);

			holder.sendEmail.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Map<String, String> params = new HashMap<String, String>();
					params.put("receiptid", String.valueOf(item.receiptid));
					params.put("accesstoken", Settings.getAccessToken(getApplicationContext()));
					
					showProgressDialogLoading();
					
					SendReceiptByEmailRequest request = new SendReceiptByEmailRequest(ReceiptListActivity.this, params, new Response.Listener<SendReceiptByEmailResponse>() {

						@Override
						public void onResponse(SendReceiptByEmailResponse response) {
							dismissProgressDialog();
							
							if(response.response != null && response.response.equals("success"))
								showAlertDialog("", getResources().getString(R.string.receipt_list_message_sent_sucess));
							else
								showAlertDialog("", getResources().getString(R.string.error_message_basic));
						}

					});
					
					VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
				}
			});

			holder.container.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getApplicationContext(), ReceiptDetailsActivity.class);
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
			RelativeLayout container;
		}

	}

}
