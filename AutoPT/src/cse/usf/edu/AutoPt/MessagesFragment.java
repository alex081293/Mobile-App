package cse.usf.edu.AutoPt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import cse.usf.edu.AutoPt.dummy.Menu;

public class MessagesFragment extends Fragment {
	HelperFunctions help = new HelperFunctions();
	static int pId = 63;
	static int drId = 1;
	/**
	 * @param args
	 */
	public MessagesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {        
    	View messagesView = inflater.inflate(R.layout.messages, container, false);
       
        Boolean exception = false;

        try {
			String[] messages = getMessages();
			
			for(int i=0; i < messages.length; i++) {
				JSONObject jsonObject = new JSONObject(messages[i]);
				String theMess = jsonObject.getString("message");
				String time = jsonObject.getString("time");
				int userType = jsonObject.getInt("userType");	
			}		
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
        
        
        final EditText messageContent = (EditText)messagesView.findViewById(R.id.messageBodyField);
		Button sendMessageBtn = (Button)messagesView.findViewById(R.id.sendButton);

		sendMessageBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					String message = messageContent.getText().toString();
					
					System.out.println(message);
					String query = "INSERT INTO messages " +
							"(userId, userType, patient, message, private, time) " +
							"VALUES('" + drId + "', '0', '" + pId + "', '" + message + "', '0', NOW())";

					new dbMakeQuery().execute(query, "i");
					while (patientDetailActivity.loadComplete == false) {}
		        	Fragment fragment = new MessagesFragment();
		            Bundle arguments = new Bundle();
		            		            
		            fragment.setArguments(arguments);
		            getFragmentManager().beginTransaction()
		                    .add(R.id.patient_detail_container, fragment)
		                    .commit();
					
				} catch (Exception e) {
					
				}
			}
		});
 
        return messagesView;
    }
    
    private String[] getMessages() throws JSONException {
    	String query = "SELECT * FROM messages WHERE (userId='" + pId + "' and userType='1')" +
    			" OR (userId='" + drId + "' and userType='0' and patient='" + pId + "'" +
    			") ORDER BY time DESC";
    	System.out.println("In getMessages: " + query);
    	new dbMakeQuery().execute(query, "f");
    	
    	while (patientDetailActivity.loadComplete == false) {};
    	
    	String[] jsonMessageArray = help.breakJSONIntoArray(patientDetailActivity.results);
    	
    	return jsonMessageArray;
    }
}