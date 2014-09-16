package com.usfMobileApp.mobile_app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.widget.TextView;

public class helperFunctions {

	public String getJSONFromReturn(String line) {
		String result = "[";
		int flag = 0;
		for(int i=0; i< line.length(); i++) {
			char c = line.charAt(i);
			if (c == '[') {
				flag = 1;
			} else if (c == ']') {
				return result+c;
			} else if (flag == 1 ) {
				result += c;
			}
		}
		return result;
	}
	
	// To make network calls, needs to be read in a seperate thread	
	// query - the sql query
	// type - Type of query that needs to be made
	//	f - fetch
	//  i - insert
	//  d - delete
	//  u - update
	public String dbMakeQuery(final String query, final String type) {

		Thread thread = new Thread(new Runnable(){
			  @Override
			  public void run(){
			    try {
					String result = httpRequest(query, type);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
			});
		thread.start();
		return "temp";
	}
	
	// Sends a request to our web server with the query in the param, 
	// hopefully will one day return what we want
	public String httpRequest(String query, String type) throws Exception {
		String url = "http://usfandroidapp.net63.net/mysqlConnect.php?t=" + type + "&q=" +  URLEncoder.encode(query, "UTF-8");		
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet request = new HttpGet();
		URI website = new URI(url);
		request.setURI(website);
	    HttpResponse response = null;
		
	    try {
			response = httpclient.execute(request);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Here failed!");
			return "Database call failed at execute";
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
			
			System.out.println(getJSONFromReturn(result.toString()));
		    return result.toString();
		    
		} catch (IllegalStateException | IOException e1) {
			System.out.println("Database call failed at line read");
		}	    
		return "";
	}
}


