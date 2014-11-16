package cse.usf.edu.AutoPt;

import android.os.Bundle;
import android.app.Fragment;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class SettingsFragment extends Fragment {

	/**
	 * @param args
	 */
	public SettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings, container, false);

        // Show the dummy content as text in a TextView.
        
//        Button soundBtn = (Button) rootView.findViewById(R.id.soundNotificationBtn);
//        soundBtn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//            	Intent exerciseIntent = new Intent(getActivity(), ExerciseActivity.class);
//	        	startActivity(exerciseIntent);
//            }
//
//        });
        return rootView;
    }

}
