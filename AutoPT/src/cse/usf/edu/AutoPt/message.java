package cse.usf.edu.AutoPt;

import org.json.JSONException;
import org.json.JSONObject;

public class message {
	public String message;
	public String time;
	public int userType;
	
	public message(String m, String tim, int t) {
        this.message = m;
        this.time = tim;
        this.userType = t;
    }
	
}
