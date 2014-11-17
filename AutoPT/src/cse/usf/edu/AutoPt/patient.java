package cse.usf.edu.AutoPt;
import java.sql.Time;
import java.util.Calendar;

import android.os.Bundle;
import android.R.string;

public class patient {

	public String firstName, lastName,alertTime;
	public int perscription, drId, pId, soundSettings, vibrateSettings;
	
	
	public patient(String firstName, String lastName, int i, int drId, int pId, int soundSettings, int vibrateSettings, String alertTime) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.perscription = i;
        this.drId = drId;
        this.pId = pId;
        this.soundSettings = soundSettings;
        this.vibrateSettings = vibrateSettings;
        this.alertTime = alertTime;
    }
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
