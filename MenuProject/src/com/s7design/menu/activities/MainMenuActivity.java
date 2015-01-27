package com.s7design.menu.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.s7design.menu.R;
import com.s7design.menu.app.Menu;
import com.s7design.menu.utils.Settings;

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
	private Button mAboutTheAppButton;

	private View mSeparatorPastReceipts;
	private View mSeparatorManageProfile;

	private boolean isLoggedIn;
	private boolean isOrderListEmpty;

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

		isLoggedIn = !Settings.getAccessToken(getApplicationContext()).equals("") ? true : false;
		isOrderListEmpty = Menu.getInstance().getDataManager().getCheckoutList().size() == 0 ? true : false;

		mVenueMenuButton = (Button) findViewById(R.id.buttonMainMenuActivityViewVenue);
		mTutorialsButton = (Button) findViewById(R.id.buttonMainMenuActivityViewTutorial);
		mMenageYourProfileButton = (Button) findViewById(R.id.buttonMainMenuActivityManageYourAccount);
		mViewPastReceiptsButton = (Button) findViewById(R.id.buttonMainMenuActivityViewPastReceipts);
		mReviewCurrentOrderButton = (Button) findViewById(R.id.buttonMainMenuActivityCurrentOrderPreview);
		mAboutTheAppButton = (Button) findViewById(R.id.buttonMainMenuActivityAboutTheApp);

		mSeparatorManageProfile = (View) findViewById(R.id.separator4);
		mSeparatorPastReceipts = (View) findViewById(R.id.separator5);

		setActionBarForwardButtonvisibility(View.INVISIBLE);
		setActionBarMenuButtonVisibility(View.INVISIBLE);

		if (isLoggedIn) {
			mMenageYourProfileButton.setVisibility(View.VISIBLE);
			mViewPastReceiptsButton.setVisibility(View.VISIBLE);
			mSeparatorManageProfile.setVisibility(View.VISIBLE);
			mSeparatorPastReceipts.setVisibility(View.VISIBLE);
		} else {
			mMenageYourProfileButton.setVisibility(View.GONE);
			mViewPastReceiptsButton.setVisibility(View.GONE);
			mSeparatorManageProfile.setVisibility(View.GONE);
			mSeparatorPastReceipts.setVisibility(View.GONE);
		}

		if (isOrderListEmpty) {
			mReviewCurrentOrderButton.setTextColor(getResources().getColor(R.color.menu_main_gray_light));
		} else {
			mReviewCurrentOrderButton.setTextColor(getResources().getColor(R.color.menu_main_orange));
		}

		if (Menu.getInstance().getDataManager().getMajor() != null && Menu.getInstance().getDataManager().getMinor() != null) {
		} else {
			mVenueMenuButton.setTextSize(convertDpToPixels(4));
			mVenueMenuButton.setTextColor(getResources().getColor(R.color.menu_main_gray_light));
			mVenueMenuButton.setText(getResources().getString(R.string.main_menu_category_not_available));
		}

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
				if (!isOrderListEmpty)
					reviewCurrentOrderButtonAction();
			}

		});

		mAboutTheAppButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				aboutTheAppButtonAction();
			}
		});
	}

	private void venuMenuButtonAction() {
		if (Menu.getInstance().getDataManager().getMajor() != null && Menu.getInstance().getDataManager().getMinor() != null)
			startActivity(new Intent(getApplicationContext(), CategoryMealsActivity.class));

	}

	private void tutorialsButtonAction() {
		startActivity(new Intent(getApplicationContext(), TutorialFirstActivity.class));
	}

	private void manageYourProfileButtonAction() {
		startActivity(new Intent(getApplicationContext(), ManageAccountActivity.class));
	}

	private void viewPastReceiptsButtonAction() {
		startActivity(new Intent(getApplicationContext(), ReceiptListActivity.class));
	}

	private void reviewCurrentOrderButtonAction() {
		if (Menu.getInstance().getDataManager().getCheckoutList() != null)
			startActivity(new Intent(getApplicationContext(), CheckoutActivity.class));
		else
			showAlertDialog("Alert", "Your checkout list is empty. Add some things to Your chart.");
	}

	private void aboutTheAppButtonAction() {
		startActivity(new Intent(getApplicationContext(), AboutTheAppActivity.class));
	}

	private float convertDpToPixels(int dp) {
		Resources r = getResources();
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, r.getDisplayMetrics());
	}

}
