package com.s7design.menu.activities;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.android.volley.Response.Listener;
import com.s7design.menu.R;
import com.s7design.menu.app.Menu;
import com.s7design.menu.volley.VolleySingleton;
import com.s7design.menu.volley.requests.GetRestaurantInfoRequest;
import com.s7design.menu.volley.responses.GetRestaurantInfoResponse;

/**
 * Activity for presenting main menu of the <i><b>Menu</b></i> application. <br>
 * In <code>MainMenuActivity</code> you can take actions such are: <br>
 * 
 * <pre>
 * 1)View venue menu
 * </pre>
 * 
 * <pre>
 * 2)View Tutorials
 * </pre>
 * 
 * <pre>
 * 3)View past receipts
 * </pre>
 * 
 * <pre>
 * 4)Manage your account
 * </pre>
 * 
 * <pre>
 * 5)Review current order
 * </pre>
 * 
 * @author s7Design
 *
 */
public class MainMenuActivity extends BaseActivity {

	// VIEWS
	private Button mVenueMenuButton;
	private Button mTutorialsButton;
	private Button mMenageYourProfileButton;
	private Button mViewPastReceiptsButton;
	private Button mReviewCurrentOrderButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		initViews();
	}

	/**
	 * Method for initalizing all views in class.
	 */
	private void initViews() {

		mVenueMenuButton = (Button) findViewById(R.id.buttonMainMenuActivityViewVenue);
		mTutorialsButton = (Button) findViewById(R.id.buttonMainMenuActivityViewTutorial);
		mMenageYourProfileButton = (Button) findViewById(R.id.buttonMainMenuActivityManageYourAccount);
		mViewPastReceiptsButton = (Button) findViewById(R.id.buttonMainMenuActivityViewPastReceipts);
		mReviewCurrentOrderButton = (Button) findViewById(R.id.buttonMainMenuActivityCurrentOrderPreview);

		setActionBarForwardButtonvisibility(View.INVISIBLE);
		setActionBarMenuButtonVisibility(View.INVISIBLE);
		
		setActionBarBackButtonText(R.string.action_bar_back);
		setActionBarBackButtonOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		setActionBarMenuButtonOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		
		mVenueMenuButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				venuMenuButtonAction();
			}

		});

		mTutorialsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tutorialsButtonAction();
			}

		});

		mMenageYourProfileButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				manageYourProfileButtonAction();
			}

		});

		mViewPastReceiptsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				viewPastReceiptsButtonAction();
			}

		});

		mReviewCurrentOrderButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				reviewCurrentOrderButtonAction();
			}

		});
	}

	private void venuMenuButtonAction() {
		startActivity(new Intent(getApplicationContext(), SignInActivity.class));
	}

	private void tutorialsButtonAction() {
		startActivity(new Intent(getApplicationContext(), TutorialFirstActivity.class));
	}

	private void manageYourProfileButtonAction() {
		startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
	}

	private void viewPastReceiptsButtonAction() {
		startActivity(new Intent(getApplicationContext(), ReceiptListActivity.class));
	}

	private void reviewCurrentOrderButtonAction() {
		if(Menu.getInstance().getDataManager().getCheckoutList() != null)
			startActivity(new Intent(getApplicationContext(), CheckoutActivity.class));
		else
			showAlertDialog("Alert", "Your checkout list is empty. Add some things to Your chart.");
	}

}
