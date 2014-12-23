package com.s7design.menu.activities;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.braintreegateway.encryption.Braintree;
import com.s7design.menu.R;
import com.s7design.menu.app.Menu;
import com.s7design.menu.dataclasses.Item;
import com.s7design.menu.dataclasses.Rate;
import com.s7design.menu.views.CircleButtonView;

public class CheckoutActivity extends BaseActivity {

	private static final String TAG = CheckoutActivity.class.getSimpleName();

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
	private TextView textViewSendEmail;
	private TextView textViewMinTip;
	private TextView textViewMaxTip;

	private double total;
	private double tax;
	private double minTip;
	private double maxTip;
	private double tip = 0;
	private double discount;
	private String currency;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkout);
		checkoutList = Menu.getInstance().getDataManager().getCheckoutList();

		initData();
		initViews();
		refreshList();
	}

	private void refreshList() {
		total = 0;
		for (Item item : checkoutList) {
			total += item.quantitySmall > 0 ? (item.quantitySmall * item.smallprice) : (item.quantityLarge * item.largeprice);
		}

		setData();
	}

	private void initData() {

		Rate rate = Menu.getInstance().getDataManager().getRate();
		tax = rate.tax;
		minTip = rate.mintip;
		maxTip = rate.maxtip;
		discount = Menu.getInstance().getDataManager().getDiscount();
		currency = Menu.getInstance().getDataManager().getCurrency();
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
		textViewSendEmail = (TextView) findViewById(R.id.textViewSendEmail);
		textViewMinTip = (TextView) findViewById(R.id.textViewMinTip);
		textViewMaxTip = (TextView) findViewById(R.id.textViewMaxTip);

		circleButtonAdd.setAsAdd();
		circleButtonAdd.setAsOrange();

		adapter = new Adapter(this, checkoutList);
		listView.setAdapter(adapter);

		seekBar.setMax(25);

		textViewTaxPercent.setText(String.valueOf(tax) + "%");
		textViewDiscountPercent.setText(String.valueOf(discount) + "%");
		textViewMinTip.setText(minTip + "%");
		textViewMaxTip.setText(maxTip + "%");

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

	}

	private void setData() {

		double totalTax = total * tax / 100;
		double totalTip = (total + totalTax) * tip / 100;
		double disc = (total + totalTax + totalTip) * discount / 100;
		double totalAmount = total + totalTax + totalTip - disc;
		
		textViewTipPercent.setText(tip + "% - " + currency + String.format("%.2f", totalTip));

		textViewTipTotalPercent.setText(tip + "%");

		textViewSubtotal.setText(String.format("%.2f", total));
		textViewTax.setText(String.format("%.2f", totalTax));
		textViewTip.setText(String.format("%.2f", totalTip));
		textViewDiscount.setText(String.format("%.2f", disc));
		textViewTotal.setText(currency + String.format("%.2f", totalAmount));

		adapter.notifyDataSetChanged();

	}

	private void checkout() {

		textViewDesc.setVisibility(View.VISIBLE);
		textViewSendEmail.setVisibility(View.VISIBLE);
		layoutAddMore.setVisibility(View.GONE);
		layoutSeekBar.setVisibility(View.GONE);
		layoutDiscount.setVisibility(View.GONE);
		textViewDiscount.setVisibility(View.GONE);
		viewDivider.setBackgroundColor(getResources().getColor(R.color.menu_main_gray_light));
		textViewTotalLabel.setTextColor(getResources().getColor(R.color.menu_main_gray));
		textViewTotal.setTextColor(getResources().getColor(R.color.menu_main_gray));
		setActionBarForwardButtonText(R.string.action_bar_receipt);
		buttonCheckout.setText(R.string.checkout_enjoy);

		String thankYou = getString(R.string.checkout_thank_you_for_order);
		SpannableStringBuilder ssb = new SpannableStringBuilder(thankYou + " " + getString(R.string.checkout_receipt_sent));

		final StyleSpan bss1 = new StyleSpan(android.graphics.Typeface.BOLD);
		ssb.setSpan(bss1, 0, thankYou.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

		textViewDesc.setText(ssb);

		textViewSendEmail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "abc@gmail.com", null));
				emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Menu Restaurant receipt");
				startActivity(Intent.createChooser(emailIntent, "Send email..."));
			}
		});
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
				holder.circleButtonViewMinus = (CircleButtonView) convertView.findViewById(R.id.circleButtonViewMinus);
				holder.circleButtonViewPlus = (CircleButtonView) convertView.findViewById(R.id.circleButtonViewPlus);
				holder.textViewQty = (TextView) convertView.findViewById(R.id.textViewQty);
				holder.buttonDone = (Button) convertView.findViewById(R.id.buttonDone);
				holder.layoutName = (LinearLayout) convertView.findViewById(R.id.layoutName);
				holder.layoutMinusPlus = (LinearLayout) convertView.findViewById(R.id.layoutMinusPlus);
				holder.textViewIsSmall = (TextView) convertView.findViewById(R.id.textViewIsSmall);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			Item item = getItem(position);

			holder.position = position;

			boolean isSmall = item.quantitySmall > 0;
			holder.isSmall = isSmall;

			holder.circleButtonViewQty.setAsQty(isSmall ? item.quantitySmall : item.quantityLarge);
			holder.circleButtonViewQty.setAsLight();
			holder.textViewName.setText(item.name);
			holder.textViewPrice.setText(String.valueOf(isSmall ? (item.smallprice * item.quantitySmall) : (item.largeprice * item.quantityLarge)));
			holder.circleButtonViewDel.setAsDel();
			holder.circleButtonViewDel.setAsLight();
			holder.circleButtonViewMinus.setAsRemove();
			holder.circleButtonViewPlus.setAsAdd();
			holder.textViewQty.setText(String.valueOf(isSmall ? item.quantitySmall : item.quantityLarge));
			holder.textViewIsSmall.setVisibility(isSmall ? View.VISIBLE : View.GONE);

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					ViewHolder holder = (ViewHolder) v.getTag();
					if (holder.layoutName.getVisibility() == View.VISIBLE) {
						holder.layoutName.setVisibility(View.GONE);
						holder.layoutMinusPlus.setVisibility(View.VISIBLE);
					}
				}
			});

			holder.buttonDone.setTag(holder);
			holder.buttonDone.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					ViewHolder holder = (ViewHolder) v.getTag();
					holder.layoutMinusPlus.setVisibility(View.GONE);
					holder.layoutName.setVisibility(View.VISIBLE);
					holder.circleButtonViewQty.setAsQty(holder.isSmall ? getItem(holder.position).quantitySmall : getItem(holder.position).quantityLarge);
				}
			});

			holder.circleButtonViewMinus.setTag(holder);
			holder.circleButtonViewMinus.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					ViewHolder holder = (ViewHolder) v.getTag();
					int qty = holder.isSmall ? getItem(holder.position).quantitySmall : getItem(holder.position).quantityLarge;
					if (qty > 1) {
						holder.textViewQty.setText(String.valueOf(holder.isSmall ? --getItem(holder.position).quantitySmall : --getItem(holder.position).quantityLarge));
						refreshList();
					}
				}
			});

			holder.circleButtonViewPlus.setTag(holder);
			holder.circleButtonViewPlus.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					ViewHolder holder = (ViewHolder) v.getTag();
					int qty = holder.isSmall ? getItem(holder.position).quantitySmall : getItem(holder.position).quantityLarge;
					if (qty < 99) {
						holder.textViewQty.setText(String.valueOf(holder.isSmall ? ++getItem(holder.position).quantitySmall : ++getItem(holder.position).quantityLarge));
						refreshList();
					}
				}
			});

			holder.circleButtonViewDel.setTag(holder);
			holder.circleButtonViewDel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					ViewHolder holder = (ViewHolder) v.getTag();
					checkoutList.remove(holder.position);
					refreshList();
				}
			});

			return convertView;
		}

		class ViewHolder {
			CircleButtonView circleButtonViewQty;
			TextView textViewName;
			TextView textViewPrice;
			CircleButtonView circleButtonViewDel;
			LinearLayout layoutName;
			LinearLayout layoutMinusPlus;
			CircleButtonView circleButtonViewMinus;
			CircleButtonView circleButtonViewPlus;
			TextView textViewQty;
			TextView textViewIsSmall;
			Button buttonDone;
			int position;
			boolean isSmall;
		}

	}

}
