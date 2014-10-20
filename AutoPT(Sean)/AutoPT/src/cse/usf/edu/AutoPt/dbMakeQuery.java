package cse.usf.edu.AutoPt;

import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;


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
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
    	patientDetailActivity.results = result;
    	
    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected void onProgressUpdate(Void... values) {}

	
}