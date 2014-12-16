package com.s7design.menu.activities;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.android.volley.Response.Listener;
import com.s7design.menu.R;
import com.s7design.menu.volley.VolleySingleton;
import com.s7design.menu.volley.requests.GetCategoriesRequest;
import com.s7design.menu.volley.responses.GetCategoriesResponse;

/**
 * Activity for showing first screen of the two screens tutorial. <br>
 * This activity doesn't have too much things to handle, except translation to
 * other tutorial screen activity, which is {@linkplain TutorialSecondActivity}.
 * 
 * @author s7Design
 *
 */
public class TutorialFirstActivity extends BaseActivity {

	private static final String TAG = TutorialFirstActivity.class.getSimpleName();

	// VIEWS
	private Button mContinue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tutorial_screen_first);
		hideActionBar();
		initViews();
	}

	/**
	 * Method for initializing all views in {@link TutorialFirstActivity}
	 */
	private void initViews() {
		mContinue = (Button) findViewById(R.id.buttonuTutorialScreenFirstContinueButton);

		mContinue.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// startActivity(new Intent(getApplicationContext(),
				// TutorialSecondActivity.class));

				Map<String, String> params = new HashMap<String, String>();
				params.put("major", "22");
				params.put("minor", "2");
				params.put("lang", "en");

				GetCategoriesRequest request = new GetCategoriesRequest(TutorialFirstActivity.this, params, new Listener<GetCategoriesResponse>() {

					@Override
					public void onResponse(GetCategoriesResponse arg0) {

						Log.d(TAG, "onResponse");
					}
				});

				VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
				
				
				startActivity(new Intent(getApplicationContext(), TutorialSecondActivity.class));
				
			}
		});
	}

}
