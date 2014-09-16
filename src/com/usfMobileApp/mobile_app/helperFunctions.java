package com.usfMobileApp.mobile_app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class helperFunctions {

	public String dbMakeQuery(final String query) {
		String result = "";
		Thread thread = new Thread(new Runnable(){
			  @Override
			  public void run(){
			    try {
					httpRequest(query);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
			});
		thread.start();
		return query;
	}
	
	// Sends a request to our web server with the query in the param, 
	// hopefully will one day return what we want
	public String httpRequest(String query) throws Exception {
		String url = "http://usfandroidapp.net63.net/mysqlConnect.php?q=" +  URLEncoder.encode(query, "UTF-8");		
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
			System.out.println(result.toString());
		    return result.toString();
		} catch (IllegalStateException | IOException e1) {
			System.out.println("Database call failed at line read");
		}	    
		return "";
	}
}


