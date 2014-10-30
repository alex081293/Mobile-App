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
    	patient user = new patient("","",0);
    	updateUser(user);
    	patientDetailActivity.loadComplete=true;
    	
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
    @Override
    protected void onPreExecute() {
    	
    }

    @Override
    protected void onProgressUpdate(Void... values) {}

	
}