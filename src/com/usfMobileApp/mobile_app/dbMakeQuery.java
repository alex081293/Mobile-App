package com.usfMobileApp.mobile_app;

import android.os.AsyncTask;

class dbMakeQuery extends AsyncTask<String, Void, String> {
	helperFunctions help = new helperFunctions();
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
    	MainActivity.results = result;
    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected void onProgressUpdate(Void... values) {}
}	
