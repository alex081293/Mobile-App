package cse.usf.edu.AutoPt;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import cse.usf.edu.AutoPt.dummy.Menu;

public class AboutFragment extends Fragment {

	/**
	 * @param args
	 */
	public AboutFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.about, container, false);

        return rootView;
    }

}
