package cse.usf.edu.AutoPt;

import android.app.Activity;
import android.os.Bundle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import android.content.SharedPreferences;
import android.R.*;
import android.app.Activity;
import android.bluetooth.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import zephyr.android.BioHarnessBT.*;
import zephyr.android.BioHarnessBT.BTClient;
import zephyr.android.BioHarnessBT.BTReceiver;

public class ExerciseActivity extends Activity {
	
	BluetoothAdapter adapter = null;
	BTClient btClient;
	ZephyrProtocol _protocol;
	NewConnectedListener cxnListener;
	private final int HEART_RATE = 0x100;
	private final int RESPIRATION_RATE = 0x101;
	private final int SKIN_TEMPERATURE = 0x102;
	private final int POSTURE = 0x103;
	private final int PEAK_ACCLERATION = 0x104;
	private final int ACTIVITY_LEVEL = 0x105;
	
	// get data from Prefs
	/*SharedPreferences prefs = getSharedPreferences("MyAppPrefs", 0);
	String firstName = prefs.getString("firstName", null);
	String lastName = prefs.getString("lastName", null);
	int prescription = prefs.getInt("prescription", 0);*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise);
		
		//System.out.println("---------------------------------" + firstName + " " + lastName + " " + prescription);
		
		/*Sending a message to android that we are going to initiate a pairing request*/
        IntentFilter filter = new IntentFilter("android.bluetooth.device.action.PAIRING_REQUEST");
        /*Registering a new BTBroadcast receiver from the Main Activity context with pairing request event*/
       this.getApplicationContext().registerReceiver(new BTBroadcastReceiver(), filter);
        // Registering the BTBondReceiver in the application that the status of the receiver has changed to Paired
        IntentFilter filter2 = new IntentFilter("android.bluetooth.device.action.BOND_STATE_CHANGED");
       this.getApplicationContext().registerReceiver(new BTBondReceiver(), filter2);
        
      //Obtaining the handle to act on the CONNECT button
        TextView tv = (TextView) findViewById(R.id.txtStatusMsg);
		String ErrorText  = "No Connection";
		 tv.setText(ErrorText);

        Button btnConnect = (Button) findViewById(R.id.btnConnect);
        if (btnConnect != null) {
        	btnConnect.setOnClickListener(new OnClickListener() {
        		public void onClick(View v) {
        			String BhMacID = "00:07:80:9D:8A:E8";
        			//String BhMacID = "00:07:80:88:F6:BF";
        			adapter = BluetoothAdapter.getDefaultAdapter();
        			
        			Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
        			
        			if (pairedDevices.size() > 0) {
                        for (BluetoothDevice device : pairedDevices) {
                        	if (device.getName().startsWith("BH")) {
                        		BluetoothDevice btDevice = device;
                        		BhMacID = btDevice.getAddress();
                                break;
                        	}
                        }
        			}
        			
        			//BhMacID = btDevice.getAddress();
        			BluetoothDevice Device = adapter.getRemoteDevice(BhMacID);
        			String DeviceName = Device.getName();
        			btClient = new BTClient(adapter, BhMacID);
        			cxnListener = new NewConnectedListener(Newhandler, Newhandler);
        			btClient.addConnectedEventListener(cxnListener);
        			
        			TextView tv1 = (TextView) findViewById(R.id.txtHeartRate);
        			tv1.setText("000");
        			
        			 tv1 = (TextView) findViewById(R.id.txtBreathing);
        			 tv1.setText("0.0");
        			 
        			 /*tv1 = (TextView) findViewById(R.id.txtTemp);
        			 tv1.setText("0.0");*/
        			 
        			 /*tv1 = (TextView) findViewById(R.id.txtActivityLevel);
        			 tv1.setText("0.0");*/
        			 
        			 tv1 = (TextView) findViewById(R.id.txtPosture);
        			 tv1.setText("000");
        			 
        			 tv1 = (TextView) findViewById(R.id.txtPeakAccel);
        			 tv1.setText("0.0");
        			 
        			if (btClient.IsConnected()) {
        				btClient.start();
        				TextView tv = (TextView) findViewById(R.id.txtStatusMsg);
        				String ErrorText  = "Connected to " + DeviceName;
						 tv.setText(ErrorText);
						 
						 //Reset all the values to 0s

        			}
        			else {
        				TextView tv = (TextView) findViewById(R.id.txtStatusMsg);
        				String ErrorText  = "Cannot Connect";
						 tv.setText(ErrorText);
        			}
        		}
        	});
        }
        
        /*Obtaining the handle to act on the DISCONNECT button*/
        Button btnDisconnect = (Button) findViewById(R.id.btnDisconnect);
        if (btnDisconnect != null) {
        	btnDisconnect.setOnClickListener(new OnClickListener() {
				@Override
				/*Functionality to act if the button DISCONNECT is touched*/
				public void onClick(View v) {
					// TODO Auto-generated method stub
					/*Reset the global variables*/
					TextView tv = (TextView) findViewById(R.id.txtStatusMsg);
    				String ErrorText  = "Disconnected";
					 tv.setText(ErrorText);

					/*This disconnects listener from acting on received messages*/	
					 btClient.removeConnectedEventListener(cxnListener);
					/*Close the communication with the device & throw an exception if failure*/
					 btClient.Close();
				
				}
        	});
        }
    }
    private class BTBondReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle b = intent.getExtras();
			BluetoothDevice device = adapter.getRemoteDevice(b.get("android.bluetooth.device.extra.DEVICE").toString());
			Log.d("Bond state", "BOND_STATED = " + device.getBondState());
		}
    }
    private class BTBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d("BTIntent", intent.getAction());
			Bundle b = intent.getExtras();
			Log.d("BTIntent", b.get("android.bluetooth.device.extra.DEVICE").toString());
			Log.d("BTIntent", b.get("android.bluetooth.device.extra.PAIRING_VARIANT").toString());
			try {
				BluetoothDevice device = adapter.getRemoteDevice(b.get("android.bluetooth.device.extra.DEVICE").toString());
				Method m = BluetoothDevice.class.getMethod("convertPinToBytes", new Class[] {String.class} );
				byte[] pin = (byte[])m.invoke(device, "1234");
				m = device.getClass().getMethod("setPin", new Class [] {pin.getClass()});
				Object result = m.invoke(device, pin);
				Log.d("BTTest", result.toString());
			} catch (SecurityException e1) {
				System.out.println("*** Security Exception ***");
				e1.printStackTrace();
			} catch (NoSuchMethodException e1) {
				System.out.println("*** No Such Method Exception ***");
				e1.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    

    final  Handler Newhandler = new Handler() {
    	public void handleMessage(Message msg) {
    		TextView tv;
    		switch (msg.what) {
    		case HEART_RATE:
    			String HeartRatetext = msg.getData().getString("HeartRate");
    			tv = (TextView) findViewById(R.id.txtHeartRate);
    			System.out.println("Heart Rate Info is "+ HeartRatetext);
    			if (tv != null)
    				tv.setText(HeartRatetext);
    		break;
    		
    		case RESPIRATION_RATE:
    			String RespirationRatetext = msg.getData().getString("RespirationRate");
    			tv = (TextView) findViewById(R.id.txtBreathing);
    			if (tv != null)
    				tv.setText(RespirationRatetext);
    		
    		break;
    		
    		/*case SKIN_TEMPERATURE:
    			String SkinTemperaturetext = msg.getData().getString("SkinTemperature");
    			tv = (TextView) findViewById(R.id.txtTemp);
    			if (tv != null)
    				tv.setText(SkinTemperaturetext);

    		break;*/
    		
    		/*case ACTIVITY_LEVEL:
    			String activityLevel = msg.getData().getString("ActivityLevel");
    			tv = (TextView) findViewById(R.id.txtActivityLevel);
    			if (tv != null)
    				tv.setText(activityLevel);

    		break;*/
    		
    		case POSTURE:
    			String PostureText = msg.getData().getString("Posture");
    			tv = (TextView)findViewById(R.id.txtPosture);
    			if (tv != null)
    				tv.setText(PostureText);

    		
    		break;
    		
    		case PEAK_ACCLERATION:
    			String PeakAccText = msg.getData().getString("PeakAcceleration");
    			tv = (TextView)findViewById(R.id.txtPeakAccel);
    			if (tv != null)
    				tv.setText(PeakAccText);
    			
    		break;
    		
    		
    		}
    	}

    };
    
}

