package com.s7design.menu.activities;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.s7design.menu.R;
import com.s7design.menu.app.Menu;
import com.s7design.menu.dataclasses.Item;
import com.s7design.menu.views.CircleButtonView;

public class CheckoutActivity extends BaseActivity {

	private ArrayList<Item> checkoutList;
	private ListView listView;
	private Adapter adapter;
	private CircleButtonView circleButtonAdd;
	private SeekBar seekBar;
	private TextView textViewTipPercent;
	private Button buttonCheckout;
	private TextView textViewDesc;
	private LinearLayout layoutAddMore;
	private LinearLayout layoutSeekBar;
	private LinearLayout layoutDiscount;
	private TextView textViewDiscount;
	private View viewDivider;
	private TextView textViewTotalLabel;
	private TextView textViewTotal;
	private TextView textViewTaxPercent;
	private TextView textViewTipTotalPercent;
	private TextView textViewDiscountPercent;
	private TextView textViewSubtotal;
	private TextView textViewTax;
	private TextView textViewTip;

	private double total;
	private double tax = 4.23;
	private double tip = 0;
	private double discount = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkout);
		checkoutList = Menu.getInstance().getDataManager().getTestCheckoutList();
		for (Item item : checkoutList) {
			total += item.quantityLarge * item.largeprice;
			total += item.quantitySmall * item.smallprice;
		}
		initViews();
	}

	private void initViews() {

		setActionBarForwardArrowVisibility(null);
		setActionBarForwardButtonTextColor(getResources().getColor(R.color.menu_main_orange));
		setActionBarForwardButtonText(R.string.action_bar_order_summary);

		setActionBarBackButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		setActionBarForwardButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Will intent to order summary activity", Toast.LENGTH_SHORT).show();
			}
		});

		listView = (ListView) findViewById(R.id.listView);
		circleButtonAdd = (CircleButtonView) findViewById(R.id.circleButtonAdd);
		seekBar = (SeekBar) findViewById(R.id.seekBar);
		textViewTipPercent = (TextView) findViewById(R.id.textViewTipPercent);
		buttonCheckout = (Button) findViewById(R.id.buttonCheckout);
		textViewDesc = (TextView) findViewById(R.id.textViewDesc);
		layoutAddMore = (LinearLayout) findViewById(R.id.layoutAddMore);
		layoutSeekBar = (LinearLayout) findViewById(R.id.layoutSeekBar);
		layoutDiscount = (LinearLayout) findViewById(R.id.layoutDiscount);
		textViewDiscount = (TextView) findViewById(R.id.textViewDiscount);
		viewDivider = (View) findViewById(R.id.viewDivider);
		textViewTotalLabel = (TextView) findViewById(R.id.textViewTotalLabel);
		textViewTotal = (TextView) findViewById(R.id.textViewTotal);

		textViewTaxPercent = (TextView) findViewById(R.id.textViewTaxPercent);
		textViewTipTotalPercent = (TextView) findViewById(R.id.textViewTipTotalPercent);
		textViewDiscountPercent = (TextView) findViewById(R.id.textViewDiscountPercent);
		textViewSubtotal = (TextView) findViewById(R.id.textViewSubtotal);
		textViewTax = (TextView) findViewById(R.id.textViewTax);
		textViewTip = (TextView) findViewById(R.id.textViewTip);

		circleButtonAdd.setAsAdd();

		adapter = new Adapter(this, checkoutList);
		listView.setAdapter(adapter);

		seekBar.setMax(25);

		textViewTaxPercent.setText(String.valueOf(tax) + "%");
		textViewDiscountPercent.setText(String.valueOf(discount) + "%");

		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				tip = progress;
				setData();
			}
		});

		buttonCheckout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				checkout();
			}
		});

		setData();

	}

	private void setData() {

		double totalTax = total * tax / 100;
		double totalTip = (total + totalTax) * tip / 100;
		double disc = (total + totalTax + totalTip) * discount / 100;
		double totalAmount = total + totalTax + totalTip - disc;

		textViewTipPercent.setText(tip + "% - " + getString(R.string.misc_euro) + String.format("%.2f", totalTip));

		textViewTipTotalPercent.setText(tip + "%");

		textViewSubtotal.setText(String.format("%.2f", total));
		textViewTax.setText(String.format("%.2f", totalTax));
		textViewTip.setText(String.format("%.2f", totalTip));
		textViewDiscount.setText(String.format("%.2f", disc));
		textViewTotal.setText(getString(R.string.misc_euro) + String.format("%.2f", totalAmount));

	}

	private void checkout() {

		textViewDesc.setVisibility(View.VISIBLE);
		layoutAddMore.setVisibility(View.GONE);
		layoutSeekBar.setVisibility(View.GONE);
		layoutDiscount.setVisibility(View.GONE);
		textViewDiscount.setVisibility(View.GONE);
		viewDivider.setBackgroundColor(getResources().getColor(R.color.menu_main_orange));
		textViewTotalLabel.setTextColor(getResources().getColor(R.color.menu_main_gray));
		textViewTotal.setTextColor(getResources().getColor(R.color.menu_main_gray));
		setActionBarForwardButtonText(R.string.action_bar_receipt);
		buttonCheckout.setText(R.string.checkout_enjoy);

		String thankYou = getString(R.string.checkout_thank_you_for_order);
		SpannableStringBuilder ssb = new SpannableStringBuilder(thankYou + " " + getString(R.string.checkout_receipt_sent));

		final StyleSpan bss1 = new StyleSpan(android.graphics.Typeface.BOLD);
		ssb.setSpan(bss1, 0, thankYou.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

		textViewDesc.setText(ssb);
	}

	class Adapter extends BaseAdapter {

		private LayoutInflater layoutInflater;
		private ArrayList<Item> checkoutList;

		public Adapter(Context context, ArrayList<Item> checkoutList) {
			this.checkoutList = checkoutList;
			layoutInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return checkoutList.size();
		}

		@Override
		public Item getItem(int position) {
			return checkoutList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;

			if (convertView == null) {
				convertView = layoutInflater.inflate(R.layout.row_checkout_list, parent, false);
				holder = new ViewHolder();

				holder.circleButtonViewQty = (CircleButtonView) convertView.findViewById(R.id.circleButtonViewQty);
				holder.textViewName = (TextView) convertView.findViewById(R.id.textViewName);
				holder.textViewPrice = (TextView) convertView.findViewById(R.id.textViewPrice);
				holder.circleButtonViewDel = (CircleButtonView) convertView.findViewById(R.id.circleButtonViewDel);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			Item item = getItem(position);

			//TODO: promeniti quantity
			holder.circleButtonViewQty.setAsQty(item.quantityLarge);
			holder.textViewName.setText(item.name);
			holder.textViewPrice.setText(String.valueOf(item.largeprice));
			holder.circleButtonViewDel.setAsDel();

			return convertView;
		}

		class ViewHolder {
			CircleButtonView circleButtonViewQty;
			TextView textViewName;
			TextView textViewPrice;
			CircleButtonView circleButtonViewDel;
		}

	}

}
