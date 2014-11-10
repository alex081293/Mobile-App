package cse.usf.edu.AutoPt;

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
	static int pId = 63;
	static int drId = 1;
	
	private final Context context;
	private final int layoutResourceId;
	private final ArrayList<message> messages;


	public MessageAdapter(Context context, int layoutResourceId, ArrayList<message> messages) {
        super(context, layoutResourceId);
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
		View row = convertView;
		ViewHolder  holder = null;
		int direction = messages.get(i).userType;
		//show message on left or right, depending on if
		//it's incoming or outgoing
		System.out.println("try this");
		if (row == null) {
			LayoutInflater inflater = ((Activity)context).getLayoutInflater(); 
			
			int res = 0;
			if (direction == 0) {
				res = R.layout.message_right;
			} else if (direction == 1) {
				res = R.layout.message_left;
			}
			
			row = inflater.inflate(res, viewGroup, false);
			holder = new ViewHolder();
			
			holder.txtMessage = (TextView) row.findViewById(R.id.txtMessage);
			holder.txtDate = (TextView) row.findViewById(R.id.txtDate);
			row.setTag(holder);
		}

		 System.out.println(messages.get(i).message);
		 holder.txtMessage.setText(messages.get(i).message);
		 holder.txtDate.setText(messages.get(i).time);

		 return row;
	}
	
    static class ViewHolder {
        TextView txtMessage;
        TextView txtDate;
    }
}
