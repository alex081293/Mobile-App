package cse.usf.edu.AutoPt;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SessionsListFragment extends Fragment {

	/**
	 * @param args
	 */
	public SessionsListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.sessions_list, container,
				false);

		return rootView;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		TextView tv = (TextView)getView().findViewById(R.id.sessionsListTitle);
		tv.setText("Sessions for " + patientDetailActivity.month +"/" + patientDetailActivity.day + "/" + patientDetailActivity.year);
		String time = "'" + patientDetailActivity.year + "-"
				+ patientDetailActivity.month + "-" + patientDetailActivity.day
				+ " 12:00:00'";
		String query = "select * from sessions where patientId = "
				+ patientDetailActivity.user.pId + " and time = " + time;
		String notesText = "";
		final Intent exerciseIntent = new Intent(getActivity(),
				ExerciseActivity.class);

		HelperFunctions help = new HelperFunctions();
		new dbMakeQuery().execute(query, "f");

		while (patientDetailActivity.loadComplete == false) {
		}

		String[] sessions = help
				.breakJSONIntoArray(patientDetailActivity.results);

		if(!sessions[0].equals("")) {
			boolean haveSession = false;
			for (int i = 1; i <= sessions.length; i++) {
				Button myButton = new Button(getActivity());
				JSONObject object = null;
				try {
					object = new JSONObject(sessions[i - 1]);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					if (object.getInt("completed") == 0) {
						try {
							myButton.setText(object.getString("sessionName"));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							myButton.setId(object.getInt("id"));
							exerciseIntent.putExtra("id",
									object.getString("id"));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							exerciseIntent.putExtra("notes",
									object.getString("notes"));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						haveSession = true;
						myButton.setBackgroundDrawable(getResources()
								.getDrawable(R.drawable.sessionslistbutton));
						LinearLayout ll = (LinearLayout) getView()
								.findViewById(R.id.listSessions);
						LayoutParams lp = new LayoutParams(
								LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT);
						final int id_ = myButton.getId();
						// myButton.setBackground(R.drawable.se)
						ll.addView(myButton, lp);
						Button btn1 = ((Button) getView().findViewById(id_));
						btn1.setOnClickListener(new View.OnClickListener() {
							public void onClick(View view) {

								startActivity(exerciseIntent);
								// This is where you should connect the
								// bioHarness. The Buttons have the asscociated
								// session id as their Id
								// Currently I am using lessthan today to
								// populate my the list but that will be change
								// to a specific day
								// If you uncomment the below code the
								// button/session ids will pop up.

								// Toast.makeText(view.getContext(),
								// "Button clicked index = " + id_,
								// Toast.LENGTH_SHORT)
								// .show();
							}
						});
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (!haveSession) {
				Button btn2 = new Button(getActivity());
				btn2.setText("No More Sessions on this Day");
				btn2.setId(4565);
				btn2.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.sessionslistbutton));
				LinearLayout ll = (LinearLayout) getView().findViewById(
						R.id.listSessions);
				LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT);
				Button myButton = new Button(getActivity());
				myButton.setText("Change the Date");
				myButton.setId(4564);
				myButton.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.sessionslistbutton));
				final int id_ = btn2.getId();
				// myButton.setBackground(R.drawable.se)
				ll.addView(btn2);
				ll.addView(myButton, lp);
				
				Button btn1 = ((Button) getView().findViewById(id_));
				btn1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View view) {
						Toast.makeText(view.getContext(),
								"Button clicked index = " + id_,
								Toast.LENGTH_SHORT).show();
					}
				});

			}
		} else{
			Button myButton = new Button(getActivity());
			myButton.setText("No More Sessions on this Day");
			myButton.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.sessionslistbutton));
			LinearLayout ll = (LinearLayout) getView().findViewById(
					R.id.listSessions);
			LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			final int id_ = myButton.getId();
			// myButton.setBackground(R.drawable.se)
			ll.addView(myButton, lp);
			Button btn1 = ((Button) getView().findViewById(id_));
			btn1.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {

				}
			});

		}

	}

}
