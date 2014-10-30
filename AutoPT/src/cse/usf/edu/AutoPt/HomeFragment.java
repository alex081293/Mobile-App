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

import cse.usf.edu.AutoPt.HelperFunctions;
import cse.usf.edu.AutoPt.dbMakeQuery;
import cse.usf.edu.AutoPt.dummy.Menu;

public class HomeFragment extends Fragment implements View.OnClickListener {

	/**
	 * @param args
	 */
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
    	
        View rootView = inflater.inflate(R.layout.home, container, false);
        
        TextView tv1 = (TextView)rootView.findViewById(R.id.textView1);
        tv1.setText(patientDetailActivity.user.firstName + " " + patientDetailActivity.user.lastName);
        Button upButton = (Button) rootView.findViewById(R.id.homeBtn);
        upButton.setOnClickListener(this);

		    

        return rootView;
    }
    
    public void updateResults(){
    	
    }
    public void submitButtonClick(View view)
    {
    	
    	TextView tv1 = (TextView)getView().findViewById(R.id.textView1);
        tv1.setText("Loading...");
    }
    @Override
    public void onClick(View v) {
    	switch(v.getId()){
	        case R.id.homeBtn:
	        	TextView tv1 = (TextView)getView().findViewById(R.id.textView1);
	            tv1.setText("Loading...");
	        break;
	    } 
        
    }

}
