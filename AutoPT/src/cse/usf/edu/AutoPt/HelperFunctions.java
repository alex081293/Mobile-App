package cse.usf.edu.AutoPt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class HelperFunctions {

	public String getJSONFromReturn(String line) {
		String result = "";
		int flag = 0;
		if(line == "string(2) \"[]\"")
			return "{}";
		for(int i=0; i< line.length(); i++) {
			char c = line.charAt(i);
			if (c == ']') {
				return result;
			} else if (flag == 1 ) {
				result += c;
			} else if (c == '[') {
				flag = 1;
			}
		}
		return result;
	}
	
	public String[] breakJSONIntoArray(String jsonEncodedString) {
		String[] results = new String[100];
		for (int i=0; i<100; i++) results[i] = "";
		String result = "";
		
		int flag = 0;
		
		int index = 0;
		
		for(int i=0; i< jsonEncodedString.length(); i++) {
			char c = jsonEncodedString.charAt(i);
			if (flag == 1) {
				flag = 0;
				index++;
			} else if (c != '}') {
				results[index] += c;
			} else if (c == '}' ) {
				results[index] += c;
				flag = 1;
			}
		}
		
		int newSize = 0;
		for(int i=0; i<100; i++) {
			if (results[i] == "" && newSize == 0) newSize = i;
		}
		
		String[] toBeReturned = new String[newSize];
		System.out.println("New Size: " + newSize);
		for(int i=0; i<newSize; i++) {
			toBeReturned[i] = results[i];
		}		
		
		return toBeReturned;
	}

	// Sends a request to our web server with the query in the param and the type of query
	public String httpRequest(String query, String type) throws Exception {
		String url = "http://usfandroidapp.net63.net/mysqlConnect.php?q=" +  URLEncoder.encode(query, "UTF-8") + "&t=" + type;		
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
			

			return getJSONFromReturn(result.toString());

		    
		} catch (Exception e) {
			System.out.println("Database call failed at line read");
		}	    
		return "";
	}

    public static int getUnseenMessages() {
    	String query = "SELECT COUNT(*) as count FROM messages WHERE userId='" + patientDetailActivity.user.drId 
    			+ "' and userType='0' and patient='" + patientDetailActivity.user.pId + "'" + " and viewed='0'";
    	new dbMakeQuery().execute(query, "f");
    	patientDetailActivity.loadComplete = false;
    	while (patientDetailActivity.loadComplete == false) {};
    	JSONObject countObj;
		try {
			countObj = new JSONObject(patientDetailActivity.results);
			return countObj.getInt("count");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
    }

public String getJSON(String address){
	StringBuilder builder = new StringBuilder();
	HttpClient client = new DefaultHttpClient();
	HttpGet httpGet = new HttpGet(address);
	try{
		HttpResponse response = client.execute(httpGet);
		StatusLine statusLine = response.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		if(statusCode == 200) {
			HttpEntity entity = response.getEntity();
			InputStream content = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(content));
			String line;
			while((line = reader.readLine()) != null){
				builder.append(line);
			}
		} else {
			Log.e(MainActivity.class.toString(),"Failed to get JSON object");
		}
	}catch(ClientProtocolException e){
		e.printStackTrace();
	} catch (IOException e){
		e.printStackTrace();
	}
	return builder.toString();
}

public static void setToSeenMessages() {
	String query = "UPDATE messages SET viewed='1' WHERE userId='" + patientDetailActivity.user.drId + "' and userType='0' and patient='" 
					+ patientDetailActivity.user.pId +"'";
	new dbMakeQuery().execute(query, "u");
	while (patientDetailActivity.loadComplete == false) {};
}

public static int getTodaysWorkouts() {
	String query = "SELECT COUNT(*) as count FROM sessions WHERE patientId='" + patientDetailActivity.user.pId + "'" + " and completed='0' and " +
			"DATE(time) = CURDATE()";
	new dbMakeQuery().execute(query, "f");
	patientDetailActivity.loadComplete = false;
	while (patientDetailActivity.loadComplete == false) {};
	JSONObject countObj;
	try {
		countObj = new JSONObject(patientDetailActivity.results);
		return countObj.getInt("count");
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return 0;
}
}