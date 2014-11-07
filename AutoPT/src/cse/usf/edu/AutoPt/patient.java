package cse.usf.edu.AutoPt;
import android.os.Bundle;
import android.R.string;

public class patient {

	public String firstName;
	public String lastName;
	public int perscription, drId, pId;
	
	public patient(){}
	public patient(String firstName, String lastName, int i, int drId, int pId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.perscription = i;
        this.drId = drId;
        this.pId = pId;
    }
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
