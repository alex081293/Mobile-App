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
import android.widget.EditText;
import android.widget.TextView;
import android.*;

public class MainActivity extends ActionBarActivity {
	public static String results;
	
	helperFunctions help = new helperFunctions();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final TextView display = (TextView)findViewById(R.id.display);
		
		Button connectButton = (Button)findViewById(R.id.connectButton);
		Button resetButton = (Button)findViewById(R.id.resetButton);
		final EditText inputQuery = (EditText)findViewById(R.id.query);
		
		connectButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					String query = inputQuery.getText().toString();
					new dbMakeQuery().execute(query, "f");
					display.setText(results);
				} catch (Exception e) {
					display.setText("Query failed.");
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
