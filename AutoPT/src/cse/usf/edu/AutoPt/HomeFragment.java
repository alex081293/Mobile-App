package cse.usf.edu.AutoPt;

import org.json.JSONObject;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import cse.usf.edu.AutoPt.HelperFunctions;
import cse.usf.edu.AutoPt.dbMakeQuery;
import cse.usf.edu.AutoPt.dummy.Menu;

public class HomeFragment extends Fragment implements View.OnClickListener {

	public static patient user = new patient("","",0,0,0);
	public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
    }
    HelperFunctions load = new HelperFunctions();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	user = patientDetailActivity.user;
        View rootView = inflater.inflate(R.layout.home, container, false);
        
        TextView tv1 = (TextView)rootView.findViewById(R.id.textView1);
        tv1.setText(patientDetailActivity.user.firstName + " " + patientDetailActivity.user.lastName);
        Button upButton = (Button) rootView.findViewById(R.id.homeBtn);
        upButton.setOnClickListener(this);

        Button btnExercise = (Button) rootView.findViewById(R.id.btnExercise);
        btnExercise.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
            	Intent exerciseIntent = new Intent(getActivity(), ExerciseActivity.class);
	        	startActivity(exerciseIntent);
            }

        });
		    

        return rootView;
    }
    
    public void updateResults(){
    	
    }
    
    @Override
    public void onClick(View v) {
    	switch(v.getId()){
	        case R.id.homeBtn:
	        	TextView tv1 = (TextView)getView().findViewById(R.id.textView1);
	            tv1.setText(user.firstName);
	        break;
	    } 
        
    }

}
