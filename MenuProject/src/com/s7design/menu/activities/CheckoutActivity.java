package com.s7design.menu.activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
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

import com.android.volley.Response.Listener;
import com.s7design.menu.R;
import com.s7design.menu.app.Menu;
import com.s7design.menu.dataclasses.DataManager;
import com.s7design.menu.dataclasses.Item;
import com.s7design.menu.dataclasses.Rate;
import com.s7design.menu.utils.Settings;
import com.s7design.menu.views.CircleButtonView;
import com.s7design.menu.volley.VolleySingleton;
import com.s7design.menu.volley.requests.OrderRequest;
import com.s7design.menu.volley.responses.OrderResponse;

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
	private double totalPrice;
	private double disc;
	private double totalTip;
	private double totalTax;

	private DataManager data;

	private static final int REQUEST_LOGIN = 123;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkout);
		data = Menu.getInstance().getDataManager();
		checkoutList = data.getCheckoutList();

		initData();
		initViews();

	}

	@Override
	protected void onResume() {
		super.onResume();

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

		Rate rate = data.getRate();
		tax = rate.tax;
		minTip = rate.mintip;
		maxTip = rate.maxtip;
		discount = data.getDiscount();
		currency = data.getCurrency();
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
		textViewSendEmail = (TextView) findViewById(R.id.buttonSendEmail);
		textViewMinTip = (TextView) findViewById(R.id.textViewMinTip);
		textViewMaxTip = (TextView) findViewById(R.id.textViewMaxTip);

		circleButtonAdd.setAsAdd();
		circleButtonAdd.setAsOrange();

		adapter = new Adapter(this, checkoutList);
		listView.setAdapter(adapter);

		int tipRange = (int) ((maxTip - minTip) * 100);

		seekBar.setMax(tipRange);

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
				tip = progress / 100 + minTip;
				setData();
			}
		});

		buttonCheckout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!Settings.getAccessToken(getApplicationContext()).equals("")) {
					System.out.println("accesstoken= " + Settings.getAccessToken(getApplicationContext()));
					checkout();
				} else {
					Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
					startActivityForResult(intent, REQUEST_LOGIN);
				}

			}
		});

		circleButtonAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				startActivity(new Intent(CheckoutActivity.this, CategoryMealsActivity.class));
			}
		});

	}

	private void setData() {

		totalTax = total * tax / 100;
		totalTip = (total + totalTax) * tip / 100;
		disc = (total + totalTax + totalTip) * discount / 100;
		totalPrice = total + totalTax + totalTip - disc;

		textViewTipPercent.setText(tip + "% - " + currency + String.format("%.2f", totalTip));

		textViewTipTotalPercent.setText(tip + "%");

		textViewSubtotal.setText(String.format("%.2f", total));
		textViewTax.setText(String.format("%.2f", totalTax));
		textViewTip.setText(String.format("%.2f", totalTip));
		textViewDiscount.setText(String.format("%.2f", disc));
		textViewTotal.setText(currency + String.format("%.2f", totalPrice));

		adapter.notifyDataSetChanged();

	}

	private void onSuccessfulCheckout() {

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

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == REQUEST_LOGIN) {
				checkout();
			}
		}
	}

	private void checkout() {

		showProgressDialogLoading();

		Calendar cal = Calendar.getInstance();

		Map<String, String> params = new HashMap<String, String>();
		params.put("type", "neworder");
		params.put("major", data.getMajor());
		params.put("minor", data.getMinor());
		params.put("language", data.getLanguage());
		params.put("accesstoken", Settings.getAccessToken(this));
		params.put("itemcount", String.valueOf(checkoutList.size()));
		params.put("orderprice", String.format("%.2f", total));
		params.put("discount", String.format("%.2f", disc));
		params.put("tip", String.format("%.2f", totalTip));
		params.put("tax", String.format("%.2f", totalTax));
		params.put("totalprice", String.format("%.2f", totalPrice));
		params.put("day", String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
		params.put("month", String.valueOf(cal.get(Calendar.MONTH) + 1));
		params.put("year", String.valueOf(cal.get(Calendar.YEAR)));
		params.put("time", String.valueOf(cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE)));

		OrderRequest createOrderRequest = new OrderRequest(this, params, new Listener<OrderResponse>() {

			@Override
			public void onResponse(final OrderResponse response) {

				new AsyncTask<Void, Void, Boolean>() {

					@Override
					protected Boolean doInBackground(Void... params) {

						final CountDownLatch countDownLatch = new CountDownLatch(checkoutList.size());

						for (Item item : checkoutList) {
							Map<String, String> itemParams = new HashMap<String, String>();
							itemParams.put("type", "addtoorder");
							itemParams.put("major", data.getMajor());
							itemParams.put("minor", data.getMinor());
							itemParams.put("language", data.getLanguage());
							itemParams.put("tag", String.valueOf(item.quantitySmall > 0 ? item.smalltag : item.largetag));
							itemParams.put("amount", String.valueOf(item.quantitySmall > 0 ? item.quantitySmall : item.quantityLarge));
							itemParams.put("orderid", response.orderid);
							itemParams.put("accesstoken", Settings.getAccessToken(CheckoutActivity.this));

							System.out.println("tag= " + String.valueOf(item.quantitySmall > 0 ? item.smalltag : item.largetag));

							OrderRequest itemRequest = new OrderRequest(CheckoutActivity.this, itemParams, new Listener<OrderResponse>() {

								@Override
								public void onResponse(OrderResponse response) {

									if (response.response != null && response.response.equals("success"))
										countDownLatch.countDown();
								}
							});

							VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(itemRequest);
						}

						boolean success = false;
						try {
							success = countDownLatch.await(20000, TimeUnit.MILLISECONDS);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						return success;
					}

					@Override
					protected void onPostExecute(Boolean result) {

						if (result) {

							Map<String, String> executeParams = new HashMap<String, String>();
							executeParams.put("type", "execute");
							executeParams.put("major", data.getMajor());
							executeParams.put("minor", data.getMinor());
							executeParams.put("language", data.getLanguage());
							executeParams.put("orderid", response.orderid);
							executeParams.put("accesstoken", Settings.getAccessToken(CheckoutActivity.this));

							OrderRequest executeRequest = new OrderRequest(CheckoutActivity.this, executeParams, new Listener<OrderResponse>() {

								@Override
								public void onResponse(OrderResponse response) {

									dismissProgressDialog();
									showAlertDialog(R.string.dialog_title_success, R.string.dialog_order_successful);
								}
							});

							VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(executeRequest);

						} else {
							Log.d(TAG, "latch said false");
						}
					}
				}.execute();

			}
		});

		VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(createOrderRequest);
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
