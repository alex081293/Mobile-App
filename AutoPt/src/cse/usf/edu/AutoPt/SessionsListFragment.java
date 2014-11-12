package cse.usf.edu.AutoPt;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
    	
        View rootView = inflater.inflate(R.layout.sessions_list, container, false);

        

        return rootView;
    }
    
    @SuppressWarnings("deprecation")
	@Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
    	String query = "select * from sessions where patientId = " +patientDetailActivity.user.pId+ " and time <= now()";
				
    	HelperFunctions help = new HelperFunctions();
		new dbMakeQuery().execute(query, "f");
		
		while (patientDetailActivity.loadComplete == false) {}

		String[] sessions = help.breakJSONIntoArray(patientDetailActivity.results);
		
    	for (int i = 1; i <= sessions.length; i++) {
	    	Button myButton = new Button(getActivity());
	    	JSONObject object = null;
	    	try {
				object = new JSONObject(sessions[i-1]);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	try {
				myButton.setText(object.getString("sessionName"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	try {
				myButton.setId(object.getInt("id"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	myButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.sessionslistbutton));
	    	LinearLayout ll = (LinearLayout)getView().findViewById(R.id.listSessions);
	    	LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	    	final int id_ = myButton.getId();
	    	//myButton.setBackground(R.drawable.se)
	    	ll.addView(myButton, lp);
	    	Button btn1 = ((Button) getView().findViewById(id_));
    	    btn1.setOnClickListener(new View.OnClickListener() {
    	        public void onClick(View view) {
    	        	Intent exerciseIntent = new Intent(getActivity(), ExerciseActivity.class);
    	        	startActivity(exerciseIntent);
//    	        	This is where you should connect the bioHarness. The Buttons have the asscociated session id as their Id
//    	        	Currently I am using lessthan today to populate my the list but that will be change to a specific day
//    	        	If you uncomment the below code the button/session ids will pop up.
    	        	
//    	            Toast.makeText(view.getContext(),
//    	                    "Button clicked index = " + id_, Toast.LENGTH_SHORT)
//    	                    .show();
    	        }
    	    });
    	}
    	
  
    	}
    
    

}
