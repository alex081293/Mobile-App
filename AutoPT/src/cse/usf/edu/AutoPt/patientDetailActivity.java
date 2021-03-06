package cse.usf.edu.AutoPt;

import java.sql.Time;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.MenuItem;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;

import cse.usf.edu.AutoPt.patient;
import cse.usf.edu.AutoPt.MainActivity;

/**
 * An activity representing a single patient detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link MainActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link patientDetailFragment}.
 */
public class patientDetailActivity extends Activity {
	public static String results;
	private static Calendar c = Calendar.getInstance();
	public static patient user = new patient("","",0,0,0,0,0, "0:0:0");
	public static int month = c.get(Calendar.MONTH)+1;
	public static int day = c.get(Calendar.DAY_OF_MONTH);
	public static int year = c.get(Calendar.YEAR);
	
	public static boolean loadComplete = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_patient_detail);

        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
        	Fragment fragment = null;
            Bundle arguments = new Bundle();
            
            Menu.MenuItem mItem = Menu.ITEM_MAP.get(getIntent().getStringExtra(patientDetailFragment.ARG_ITEM_ID));
 
   
            arguments.putString(patientDetailFragment.ARG_ITEM_ID, getIntent().getStringExtra(patientDetailFragment.ARG_ITEM_ID));
            
            switch(Integer.parseInt(mItem.id)){
	            case 1:
	            	fragment = new HomeFragment();
	            	break;
	            case 2:
	            	fragment = new SessionsListFragment();
	            	break;
	            case 3:
	            	fragment = new SettingsFragment();
	            	break;
	            case 4:
	            	fragment = new MessagesFragment();
	            	break;
	           	case 5:
		            fragment = new CalendarFragment();
		            break;
	           	case 6:
	            	fragment = new AboutFragment();
	            	break;
	           	default:fragment = new patientDetailFragment();
	           	break;
            }
            
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .add(R.id.patient_detail_container, fragment)
                    .commit();
        }
    }

    @SuppressLint("NewApi") @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, MainActivity.class));
            return true;
        }
        
        
        return super.onOptionsItemSelected(item);
    }
    public void changeFragment(MenuItem item){
    	Fragment fragment = new SessionsListFragment();
    	getFragmentManager().beginTransaction()
        .replace(R.id.patient_detail_container, fragment, fragment.toString())
        .commit();
    	
    }
    
}
