package com.s7design.menu.activities;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.s7design.menu.R;
import com.s7design.menu.app.Menu;
import com.s7design.menu.dataclasses.Item;
import com.s7design.menu.dataclasses.Receipt;
import com.s7design.menu.dialogs.ProgressDialogFragment;
import com.s7design.menu.utils.Settings;
import com.s7design.menu.views.CircleButtonView;
import com.s7design.menu.volley.VolleySingleton;
import com.s7design.menu.volley.requests.GetReceiptInfoRequest;
import com.s7design.menu.volley.requests.SendReceiptByEmailRequest;
import com.s7design.menu.volley.responses.GetReceiptInfoResponse;
import com.s7design.menu.volley.responses.SendReceiptByEmailResponse;

public class ReceiptDetailsActivity extends BaseActivity {

	// VIEWS
	private TextView mTextViewReceiptTime;
	private TextView mTextViewRestaurantName;
	private TextView mTextViewSubtotal;
	private TextView mTextViewTax;
	private TextView mTextViewTip;
	private TextView mTextViewTotal;
	private ListView mListViewItems;
	private Button mButtonSendEmail;
	private LinearLayout mContainer;
	
	// DATA
	private Receipt mReceiptSelected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_receipt_details);

		mReceiptSelected = (Receipt) getIntent().getExtras().getSerializable(ReceiptListActivity.RECEIPT_LIST_ITEM_SELECTED);

		initActionBar();
		initViews();
		initData();

	}

	private void initActionBar() {
		setActionBarForwardArrowVisibility(null);
		setActionBarForwardButtonTextColor(getResources().getColor(R.color.menu_main_orange));
		setActionBarForwardButtonText("RECEIPT");
		setActionBarMenuButtonVisibility(View.GONE);

		setActionBarBackButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		setActionBarForwardButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

	}

	private void initViews() {
		mTextViewReceiptTime = (TextView) findViewById(R.id.textViewDesc);
		mTextViewRestaurantName = (TextView) findViewById(R.id.textViewRestaurantName);
		mTextViewSubtotal = (TextView) findViewById(R.id.textViewSubtotal);
		mTextViewTax = (TextView) findViewById(R.id.textViewTax);
		mTextViewTip = (TextView) findViewById(R.id.textViewTip);
		mTextViewTotal = (TextView) findViewById(R.id.textViewTotal);
		mListViewItems = (ListView) findViewById(R.id.listViewReceipts);
		mButtonSendEmail = (Button) findViewById(R.id.buttonSendEmail);
		mContainer = (LinearLayout) findViewById(R.id.linearlayoutReceiptDetailsActivityContainer);
		
		mButtonSendEmail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showProgressDialogLoading();
				
				Map<String, String> params = new HashMap<String, String>();
				params.put("receiptid", String.valueOf(mReceiptSelected.receiptid));
				params.put("accesstoken", Settings.getAccessToken(getApplicationContext()));
				
				SendReceiptByEmailRequest request = new SendReceiptByEmailRequest(ReceiptDetailsActivity.this, params, new Response.Listener<SendReceiptByEmailResponse>() {
				
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

		mContainer.setVisibility(View.INVISIBLE);
		
	}

	private void initData() {

		showProgressDialogLoading();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("minor", "1");
		params.put("major", "1");
		params.put("accesstoken", Settings.getAccessToken(ReceiptDetailsActivity.this));
		params.put("receiptid", mReceiptSelected.getReceiptId());

		GetReceiptInfoRequest request = new GetReceiptInfoRequest(ReceiptDetailsActivity.this, params, new Listener<GetReceiptInfoResponse>() {

			@Override
			public void onResponse(GetReceiptInfoResponse arg0) {
				StringBuilder stringBuilder = new StringBuilder();

				stringBuilder.append(arg0.date);
				stringBuilder.append(" at " + arg0.time);
				stringBuilder.append(" / Table 5");

				mTextViewReceiptTime.setText(stringBuilder);

				mTextViewRestaurantName.setText(mReceiptSelected.getRestaurantName());
				mTextViewSubtotal.setText(String.format("%.2f", arg0.orderprice));
				mTextViewTax.setText(String.format("%.2f", arg0.tax));
				mTextViewTip.setText(String.format("%.2f", arg0.tip));
				mTextViewTotal.setText(Menu.getInstance().getDataManager().getCurrency() + mReceiptSelected.getAmmount());

				mListViewItems.setAdapter(new ItemListAdapter(arg0));
				mContainer.setVisibility(View.VISIBLE);
				dismissProgressDialog();
			}
		});
		
		VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
		
	}

	private class ItemListAdapter extends BaseAdapter {

		private GetReceiptInfoResponse items;

		public ItemListAdapter(GetReceiptInfoResponse items) {
			this.items = items;
		}

		@Override
		public int getCount() {
			return items.items.length;
		}

		@Override
		public Item getItem(int position) {
			return items.items[position];
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {

				ViewHolder holder = new ViewHolder();

				convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.row_checkout_list, null);

				holder.name = (TextView) convertView.findViewById(R.id.textViewName);
				holder.quantity = (CircleButtonView) convertView.findViewById(R.id.circleButtonViewQty);
				holder.price = (TextView) convertView.findViewById(R.id.textViewPrice);
				holder.delButton = (CircleButtonView) convertView.findViewById(R.id.circleButtonViewDel);

				convertView.setTag(holder);
			}

			ViewHolder holder = (ViewHolder) convertView.getTag();
			Item item = getItem(position);

			holder.name.setText(item.name);
			holder.price.setText(String.format("%.2f", item.price));
			holder.quantity.setAsQty(1);
			holder.delButton.setVisibility(View.GONE);

			return convertView;
		}

		class ViewHolder {
			TextView name;
			TextView price;
			CircleButtonView quantity;
			CircleButtonView delButton;
		}

	}

}
