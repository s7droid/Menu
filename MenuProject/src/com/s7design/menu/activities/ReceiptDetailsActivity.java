package com.s7design.menu.activities;

import java.util.ArrayList;
import java.util.List;

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
import com.s7design.menu.dataclasses.Item;
import com.s7design.menu.dataclasses.Receipt;
import com.s7design.menu.dataclasses.ReceiptInfo;
import com.s7design.menu.views.CircleButtonView;

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
	// DATA
	private ReceiptInfo mReceiptInfo;
	private Receipt mReceiptSelected;
	private List<Item> mItems;

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

		mButtonSendEmail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

	}

	private void initData() {

		mItems = new ArrayList<Item>();

		Item item = new Item();
		item.name = "Dummy Meal Name";
		item.largeprice = 25;
		item.quantityLarge = 2;

		Item item1 = new Item();
		item1.name = "Dummy Meal Name 2";
		item1.largeprice = 25;
		item1.quantityLarge = 2;

		Item item2 = new Item();
		item2.name = "Dummy Meal Name 3";
		item2.largeprice = 25;
		item2.quantityLarge = 5;

		Item item3 = new Item();
		item3.name = "Dummy Meal Name 4";
		item3.largeprice = 25;
		item3.quantityLarge = 2;

		Item item4 = new Item();
		item4.name = "Dummy Meal Name 5";
		item4.largeprice = 25;
		item4.quantityLarge = 2;

		mItems.add(item);
		mItems.add(item1);
		mItems.add(item2);
		mItems.add(item3);
		mItems.add(item4);

		mReceiptInfo = new ReceiptInfo();
		mReceiptInfo.setDate("25.03.2014");
		mReceiptInfo.setDiscount("5.05");
		mReceiptInfo.setOrderPrice("25.67");
		mReceiptInfo.setTax("4.00");
		mReceiptInfo.setTime("23:00");
		mReceiptInfo.setTip("15.00");
		mReceiptInfo.setItems(mItems);
		
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append(mReceiptInfo.getDate());
		stringBuilder.append(" at " + mReceiptInfo.getTime());
		stringBuilder.append(" / Table 5");
		
		mTextViewReceiptTime.setText(stringBuilder);
		
		mTextViewRestaurantName.setText(mReceiptSelected.getRestaurantName());
		mTextViewSubtotal.setText(mReceiptInfo.getOrderPrice());
		mTextViewTax.setText(mReceiptInfo.getTax());
		mTextViewTip.setText(mReceiptInfo.getTip());
		mTextViewTotal.setText(Menu.getInstance().getDataManager().getCurrency() + mReceiptSelected.getAmmount());
		
		mListViewItems.setAdapter(new ItemListAdapter(mItems));
		
	}

	private class ItemListAdapter extends BaseAdapter{

		private List<Item> items;
		
		public ItemListAdapter(List<Item> items) {
			this.items = items;
		}
		
		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public Item getItem(int position) {
			return items.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if(convertView == null){
				
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
			holder.price.setText(String.format("%.2f",item.largeprice));
			holder.quantity.setAsQty(item.quantityLarge);
			holder.delButton.setVisibility(View.GONE);
			
			return convertView;
		}
		
		class ViewHolder{
			TextView name;
			TextView price;
			CircleButtonView quantity;
			CircleButtonView delButton;
		}
		
	}
	
}
