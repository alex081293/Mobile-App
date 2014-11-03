package cse.usf.edu.AutoPt;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.Color;
import android.widget.PopupWindow;

import cse.usf.edu.AutoPt.patient;

/**
 * An activity representing a list of patients. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link patientDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link MainFragment} and the item details (if present) is a
 * {@link patientDetailFragment}.
 * <p>
 * This activity also implements the required {@link MainFragment.Callbacks}
 * interface to listen for item selections.
 */
public class MainActivity extends Activity implements MainFragment.Callbacks {
	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	public void submitButtonClick(View view) {
		Button submitBtn = (Button) this.findViewById(R.id.submitBtn);
		
		patientDetailActivity.loadComplete=false;
		EditText tempId = (EditText) findViewById(R.id.key);

		String query = "select * from patients where loginToken="
				+ tempId.getText().toString();

		new dbMakeQuery().execute(query, "f");

		while (patientDetailActivity.loadComplete == false) {
		}
		updateUser(user);
		if (patientDetailActivity.user.perscription != 0) {
			try {
				
				SharedPreferences thePrefs = getSharedPreferences(MY_APP_PREFS,
						0);
				SharedPreferences.Editor prefsEd = thePrefs.edit();
				EditText eText = (EditText) findViewById(R.id.key);
				String data = eText.getText().toString();

				prefsEd.putString("fistName",
						patientDetailActivity.user.firstName);
				prefsEd.putString("lastName",
						patientDetailActivity.user.lastName);
				prefsEd.putInt("prescription",
						patientDetailActivity.user.perscription);
				prefsEd.commit();

				setContentView(R.layout.activity_patient_list);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			
			LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
					.getSystemService(LAYOUT_INFLATER_SERVICE);
			View popupView = layoutInflater.inflate(R.layout.loading, null);
			final PopupWindow popupWindow = new PopupWindow(popupView,
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			// popupWindow.showAsDropDown(submitBtn, 50, -30);
			popupWindow.showAtLocation(getCurrentFocus(), Gravity.CENTER, 0, 0);
			Button btnDismiss = (Button) popupView.findViewById(R.id.dismiss);
			btnDismiss.setOnClickListener(new Button.OnClickListener() {

				@Override
				public void onClick(View v) {
			    	switch(v.getId()){
			        case R.id.dismiss:
			        	popupWindow.dismiss();
			        break;
			    } 
					
				}
				
			});
		}

	}

	private boolean mTwoPane;

	public patient user = new patient("", "", 0);
	public static final String MY_APP_PREFS = "MyAppPrefs";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences thePrefs = getSharedPreferences(MY_APP_PREFS, 0);
		user.perscription = thePrefs.getInt("prescription", 0);

		if (user.perscription == 0) {
			setContentView(R.layout.firsttime);

		} else {

			String query = "select * from patients where loginToken="
					+ thePrefs.getInt("prescription", 0);

			new dbMakeQuery().execute(query, "f");

			setContentView(R.layout.activity_patient_list);

		}
		if (findViewById(R.id.patient_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((MainFragment) getFragmentManager().findFragmentById(
					R.id.patient_list)).setActivateOnItemClick(true);
		}

		// TODO: If exposing deep links into your app, handle intents here.
	}

	/**
	 * Callback method from {@link MainFragment.Callbacks} indicating that the
	 * item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(patientDetailFragment.ARG_ITEM_ID, id);
			patientDetailFragment fragment = new patientDetailFragment();
			fragment.setArguments(arguments);
			getFragmentManager().beginTransaction()
					.replace(R.id.patient_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, patientDetailActivity.class);
			detailIntent.putExtra(patientDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}
	public void updateUser(patient user){
		try{
			JSONObject jsonObject = new JSONObject(patientDetailActivity.results);
			user.firstName = jsonObject.getString("firstName");
			user.lastName = jsonObject.getString("lastName");
			user.perscription = jsonObject.getInt("loginToken");
			patientDetailActivity.user = user;
			//patientDetailActivity.loadComplete = true;
		}catch(Exception e){e.printStackTrace();}
    }
}
