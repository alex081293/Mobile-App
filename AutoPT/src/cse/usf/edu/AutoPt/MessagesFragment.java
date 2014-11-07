package cse.usf.edu.AutoPt;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import cse.usf.edu.AutoPt.dummy.Menu;

public class MessagesFragment extends Fragment {
	HelperFunctions help = new HelperFunctions();
	int drId = patientDetailActivity.user.drId;
	int pId = patientDetailActivity.user.pId;
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
					System.out.println(query);
					String response = help.httpRequest(query, "i");
					System.out.println(response);
				} catch (Exception e) {
					
				}
			}
		});
 
        return messagesView;
    }
    
    private String getMessages() throws Exception {
    	String query = "SELECT * FROM messages WHERE (userId='" + pId + "' and userType='0')" +
    			"OR (userId='" + drId + "' and userType='0' and patient='" + pId + "'" +
    			"ORDER BY time DESC";
    	String results;
		
    	results = help.httpRequest(query, "f");
    	
    	return results;
    }
 
    

}
