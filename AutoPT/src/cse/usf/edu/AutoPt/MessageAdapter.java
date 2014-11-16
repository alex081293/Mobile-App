package cse.usf.edu.AutoPt;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MessageAdapter extends ArrayAdapter<message>  {
	static int pId = patientDetailActivity.user.pId;
	static int drId = patientDetailActivity.user.drId;
	
	private final Context context;
	private final int layoutResourceId;
	private final ArrayList<message> messages;


	public MessageAdapter(Context context, int layoutResourceId, ArrayList<message> messages) {
        super(context, layoutResourceId, messages);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.messages = messages;
	}

	public int getSize() {
		return messages.size();
	}
	
	public message getItem(int i) {
		return messages.get(i);
	}
	
	@Override
	public View getView(int i, View convertView, ViewGroup viewGroup) {

		int direction = messages.get(i).userType;
		//show message on left or right, depending on if
		//it's incoming or outgoing
		if (convertView == null) {
			LayoutInflater inflater = ((Activity)context).getLayoutInflater(); 
			
			int res = 0;
			if (direction == 1) {
				res = R.layout.message_right;
			} else {
				res = R.layout.message_left;
			} 
			convertView = inflater.inflate(res, viewGroup, false);
		}
		 TextView txtMessage = (TextView) convertView.findViewById(R.id.txtMessage);
		 TextView txtDate = (TextView) convertView.findViewById(R.id.txtDate);

		 txtMessage.setText(messages.get(i).message);
		 txtDate.setText(messages.get(i).time);
		 
		 return convertView;
	}
}
