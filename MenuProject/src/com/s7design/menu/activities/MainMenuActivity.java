package com.s7design.menu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.s7design.menu.R;

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
		Toast.makeText(getApplicationContext(), "manageYourProfileButtonAction", Toast.LENGTH_SHORT).show();
	}

	private void viewPastReceiptsButtonAction() {
		Toast.makeText(getApplicationContext(), "viewPastReceiptsButtonAction", Toast.LENGTH_SHORT).show();
	}

	private void reviewCurrentOrderButtonAction() {
		Toast.makeText(getApplicationContext(), "reviewCurrentOrderButtonAction", Toast.LENGTH_SHORT).show();
	}

}
