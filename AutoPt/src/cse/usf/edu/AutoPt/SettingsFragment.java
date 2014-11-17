package cse.usf.edu.AutoPt;

import java.util.Calendar;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;



public class SettingsFragment extends Fragment {

	public static final String MY_APP_PREFS = "MyAppPrefs";
	/**
	 * @param args
	 */
	public SettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
    }

    @SuppressWarnings("deprecation")
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings, container, false);

        // Show the dummy content as text in a TextView.
        
        final CompoundButton soundBtn = (CompoundButton) rootView.findViewById(R.id.soundNotificationBtn);
        final CompoundButton vibrateBtn = (CompoundButton) rootView.findViewById(R.id.vibrateNotificationBtn);
        final TimePicker timePicker = (TimePicker) rootView.findViewById(R.id.alertTimePicker);
        if(patientDetailActivity.user.soundSettings == 1) soundBtn.setChecked(true);
        if(patientDetailActivity.user.vibrateSettings == 1) vibrateBtn.setChecked(true);
        String[] time = patientDetailActivity.user.alertTime.split(":");
        timePicker.setCurrentHour(Integer.parseInt(time[0]));
        timePicker.setCurrentMinute(Integer.parseInt(time[1]));
        soundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	if (((CompoundButton) soundBtn).isChecked())
            		patientDetailActivity.user.soundSettings = 1;
            	else patientDetailActivity.user.soundSettings = 0;
            	String query = "update patients set soundSetting ="+patientDetailActivity.user.soundSettings+ " where id="+patientDetailActivity.user.pId;
        		new dbMakeQuery().execute(query, "u"); 
            }
        });
        vibrateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	if (((CompoundButton) soundBtn).isChecked())
            		patientDetailActivity.user.vibrateSettings = 1;
            	else patientDetailActivity.user.vibrateSettings = 0;
            	String query = "update patients set vibrateSetting ="+patientDetailActivity.user.vibrateSettings+ " where id="+patientDetailActivity.user.pId;
        		new dbMakeQuery().execute(query, "u"); 
            }
        });
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
            	
            		
            	String query = "update patients set alertTime ='"+hourOfDay+":"+minute+":00' where id="+patientDetailActivity.user.pId;
            	String time = Integer.toString(hourOfDay) + ":" +Integer.toString(minute)+":00";
            	patientDetailActivity.user.alertTime = time;

        		new dbMakeQuery().execute(query, "u"); 
        		
            }

			
        });
        return rootView;
    }

}
