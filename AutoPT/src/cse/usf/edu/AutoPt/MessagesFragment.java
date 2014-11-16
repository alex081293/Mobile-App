package cse.usf.edu.AutoPt;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


public class MessagesFragment extends Fragment {
	HelperFunctions help = new HelperFunctions();
	
	public MessagesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {        
    	View messagesView = inflater.inflate(R.layout.messages, container, false);
        ListView listView = (ListView)messagesView.findViewById(R.id.listMessages);
        
		
        
    	ArrayList<message> messages = new ArrayList<message>();
		String[] messageArray = getMessages();		
		
		if (getMessageCount() != 0) {
			for(int i=0; i<messageArray.length; i++) {
				String theMess = null, time = null;
				int userType = 0;
	
				JSONObject jsonObject = null;
				try {
					jsonObject = new JSONObject(messageArray[i]);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {theMess = jsonObject.getString("message");} catch (JSONException e) {System.out.println("3");}
				try {time = jsonObject.getString("time");} catch (JSONException e) {System.out.println("4");}
				try {userType = jsonObject.getInt("userType");} catch (JSONException e) {}
				
				message newMessage = new message(theMess, time, userType);
				messages.add(newMessage); 	
			}

	        MessageAdapter adapter = new MessageAdapter(getActivity(), R.id.listMessages, messages);
	        listView.setAdapter(adapter);  
		}
		
        final EditText messageContent = (EditText)messagesView.findViewById(R.id.messageBodyField);
		Button sendMessageBtn = (Button)messagesView.findViewById(R.id.sendButton);

		sendMessageBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					String message = messageContent.getText().toString();
					
					if (message != "") {
						String query = "INSERT INTO messages " +
								"(userId, userType, patient, message, private, time) " +
								"VALUES('" + patientDetailActivity.user.pId + "', '1', '0', '" + message + "', '0', NOW())";
	
						new dbMakeQuery().execute(query, "i");
						while (patientDetailActivity.loadComplete == false) {}

						getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.patient_detail_container)).commit();
						
			            getFragmentManager().beginTransaction().add(R.id.patient_detail_container, new MessagesFragment()).commit();
			            
					}
				} catch (Exception e) {
					
				}
			}
		});
        return messagesView;
    }
    private String[] getMessages() {
    	String query = "SELECT * FROM messages WHERE (userId='" + patientDetailActivity.user.pId + "' and userType='1')" +
    			" OR (userId='" + patientDetailActivity.user.drId + "' and userType='0' and patient='" + patientDetailActivity.user.pId + "'" +
    			") ORDER BY time ASC";
    	new dbMakeQuery().execute(query, "f");
    	while (patientDetailActivity.loadComplete == false) {};
    	String[] jsonMessageArray = help.breakJSONIntoArray(patientDetailActivity.results);
    	return jsonMessageArray;
    }
    
    
    private int getMessageCount() {
    	String query = "SELECT COUNT(*) as count FROM messages WHERE (userId='" + patientDetailActivity.user.pId + "' and userType='1')" +
    			" OR (userId='" + patientDetailActivity.user.drId + "' and userType='0' and patient='" + patientDetailActivity.user.pId + "'" +
    			") ORDER BY time ASC";
    	new dbMakeQuery().execute(query, "f");
    	while (patientDetailActivity.loadComplete == false) {};
    	int count = 0;
    	JSONObject countObj;
		try {
			countObj = new JSONObject(patientDetailActivity.results);
			count = countObj.getInt("count");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return count;
    }
}