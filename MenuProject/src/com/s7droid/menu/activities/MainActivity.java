package com.s7droid.menu.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.s7design.menu.R;

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar actionBar = (Toolbar) findViewById(R.id.actionBar);
		setSupportActionBar(actionBar);
	}

}