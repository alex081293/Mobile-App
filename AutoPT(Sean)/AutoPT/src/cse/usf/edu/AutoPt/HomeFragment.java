package cse.usf.edu.AutoPt;

import org.json.JSONObject;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cse.usf.edu.AutoPt.HelperFunctions;
import cse.usf.edu.AutoPt.dummy.Menu;

public class HomeFragment extends Fragment {

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
        String query = "select * from patients";
		new dbMakeQuery().execute(query, "f");
        //String data = load.getJSON("http://usfandroidapp.net63.net/mysqlConnect.php?q=select * from patients&t=f");
        try{
        	JSONObject jsonObject = new JSONObject(patientDetailActivity.results);
        	((TextView) rootView.findViewById(R.id.patient_detail)).setText(jsonObject.getString("firstName"));
        	Log.i(MainActivity.class.getName(), jsonObject.getString("firstName"));
        } catch(Exception e){e.printStackTrace();}

			

        

        return rootView;
    }

}
