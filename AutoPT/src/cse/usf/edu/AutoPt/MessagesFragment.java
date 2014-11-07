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
        
       
        final EditText messageContent = (EditText)getView().findViewById(R.id.messageContent);
		Button sendMessageBtn = (Button)getView().findViewById(R.id.sendMessageBtn);

		sendMessageBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					// Where is this stuff stored
					// Need to get drId
					// Need to get patient ID
					
					String message = messageContent.getText().toString();
					String query = "INSERT INTO messages " +
							"(userId, userType, patient, message, private, time) " +
							"VALUES(" + patientDetailActivity.user.drId + ", '0', '" + patientDetailActivity.user.pId + "', '" + message + ", '0', NOW())";
					String response = help.httpRequest(query, "i");
				} catch (Exception e) {
					
				}
			}
		});
        
        return messagesView;
    }
    
//    private String getMessages() {
//    	String query = "SELECT * FROM messages WHERE (userId='" + pId + "' and userType='0')" +
//    			"OR (userId='" + drId + "' and userType='0' and patient='" + pId + "'" +
//    			"ORDER BY time DESC";
//    	String results = help.httpRequest(query, "f");
//    }
    
    

}
