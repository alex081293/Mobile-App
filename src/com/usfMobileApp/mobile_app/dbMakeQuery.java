package com.usfMobileApp.mobile_app;

import android.os.AsyncTask;

class dbMakeQuery extends AsyncTask<String, Void, String> {
	helperFunctions help = new helperFunctions();
    @Override
    protected String doInBackground(String... params) {
    	String result = "";
    	try {
    		MainActivity.results = help.httpRequest(params[0], params[1]);
    		result = MainActivity.results;
		} catch (Exception e) {
			e.printStackTrace();
		}
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        // TextView txt = (TextView) findViewById(R.id.output);
        // txt.setText("Executed"); // txt.setText(result);
        // might want to change "executed" for the returned string passed
        // into onPostExecute() but that is upto you
    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected void onProgressUpdate(Void... values) {}
}	
