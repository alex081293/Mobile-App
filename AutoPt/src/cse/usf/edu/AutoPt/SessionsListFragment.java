package cse.usf.edu.AutoPt;

import android.os.Bundle;
import android.app.ActionBar.LayoutParams;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import cse.usf.edu.AutoPt.SessionsMenu;

public class SessionsListFragment extends Fragment {

	/**
	 * @param args
	 */
	public SessionsListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        View rootView = inflater.inflate(R.layout.sessions_list, container, false);

        

        return rootView;
    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
    	Button myButton = new Button(getActivity());
    	myButton.setText("Push Me");

    	LinearLayout ll = (LinearLayout)getView().findViewById(R.id.listSessions);
    	LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    	//myButton.setBackground(R.drawable.se)
    	ll.addView(myButton, lp);
    }
    

}
