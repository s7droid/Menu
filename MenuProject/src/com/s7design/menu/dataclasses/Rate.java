package com.s7design.menu.dataclasses;

import com.s7design.menu.utils.Settings;

import android.content.Context;

public class Rate {

	public float tax;
	public float mintip;
	public float maxtip;

	public Rate(Context context, float tax) {
		Settings.setTax(context, tax);
	}

	public Rate(Context context, float minTip, float maxTip) {
		Settings.setMinTip(context, minTip);
		Settings.setMaxTip(context, maxTip);
	}

	public Rate(Context context) {
		tax = Settings.getTax(context);
		mintip = Settings.getMinTip(context);
		maxtip = Settings.getMaxTip(context);
	}

}