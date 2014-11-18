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
import cse.usf.edu.AutoPt.MonthView;
import cse.usf.edu.AutoPt.CalendarDB;

public class CalendarFragment extends Fragment {

	/**
	 * @param args
	 */
	public CalendarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.calendar, container, false);
    	LinearLayout LL = new LinearLayout(getActivity());
        LL.setOrientation(LinearLayout.VERTICAL);
    	MonthView mv = new MonthView(getActivity());
    	LL.addView(mv);
    	CalendarDB events = new CalendarDB(getActivity());	
    	
    	TextView valueTV = new TextView(getActivity());
        valueTV.setText("Red: Session Not Completed");
        valueTV.setId(5);
        valueTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
        LL.addView(valueTV);
        
        TextView tv = new TextView(getActivity());
        tv.setText("Green: Session Completed");
        tv.setId(7);
        tv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
        LL.addView(tv);
        
        TextView date = new TextView(getActivity());
        date.setText("Yellow: Selected Date");
        date.setId(5);
        date.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
        LL.addView(date);
        
 		Button myButton = new Button(getActivity());
 		myButton.setText("Go to therapy sessions for selected date");
 		myButton.setId(6);
 		myButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.sessionslistbutton));	
 		myButton.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
 		LL.addView(myButton);
 		
 		myButton.setOnClickListener(new View.OnClickListener() {
		 public void onClick(View view) {
			 getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.patient_detail_container)).commit();
			 getFragmentManager().beginTransaction().add(R.id.patient_detail_container, new SessionsListFragment()).commit();
		 }
 		});	
        
        
        return LL;
    }

}
