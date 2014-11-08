package cse.usf.edu.AutoPt;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;
import cse.usf.edu.AutoPt.MainFragment;
import cse.usf.edu.AutoPt.patientDetailActivity;
import cse.usf.edu.AutoPt.patient;
import android.content.Context; 
import android.app.Application;


class dbMakeQuery extends AsyncTask<String, Void, String> {
	HelperFunctions help = new HelperFunctions();
	
    @Override
    protected String doInBackground(String... params) {
    	String result = "";
    	
    	onPreExecute();
    	
    	try {
    		result = help.httpRequest(params[0], params[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	onPostExecute(result);
        return result;
    }

    @Override
    protected void onPostExecute(String result) {    	
    	patientDetailActivity.results = result;
    	patientDetailActivity.loadComplete=true;
    }
    
    @Override
    protected void onPreExecute() {
    	patientDetailActivity.loadComplete=false;
    }

    @Override
    protected void onProgressUpdate(Void... values) {}

	
}