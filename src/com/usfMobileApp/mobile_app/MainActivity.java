package com.usfMobileApp.mobile_app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final TextView display = (TextView)findViewById(R.id.display);
		
		Button connectButton = (Button)findViewById(R.id.connectButton);
		Button resetButton = (Button)findViewById(R.id.resetButton);
		
		connectButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					dbMakeQuery("SELECT * FROM users");
				} catch (Exception e) {
					display.setText("It Failed");
				}
			}
		});
		resetButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				display.setText("Hello World");
			}
		});			
	}
	
	// Sends a request to our web server with the requery in the param, 
	// hopefully will one day return what we want
	public void dbMakeQuery(String query) throws Exception {
		final TextView display = (TextView)findViewById(R.id.display);
		String url = "http://usfandroidapp.net63.net/mysqlConnect.php?q=" + query;		
		
		HttpClient httpclient = new DefaultHttpClient();
	    HttpGet httpget = new HttpGet(url); 

	    HttpResponse response = null;
		try {
			response = httpclient.execute(httpget);
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		BufferedReader rd;
		try {
			rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			try {
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}	    
		    display.setText(result.toString());
		} catch (IllegalStateException | IOException e1) {
			e1.printStackTrace();
		}	    
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
