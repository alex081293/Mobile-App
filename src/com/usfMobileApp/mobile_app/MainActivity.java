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
	helperFunctions help = new helperFunctions();
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
					String response = help.dbMakeQuery("SELECT * FROM users");
					display.setText(response);
				} catch (Exception e) {
					display.setText("It failed.");
				}
			}
		});

		resetButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				display.setText("Hello World");
			}
		});			
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
