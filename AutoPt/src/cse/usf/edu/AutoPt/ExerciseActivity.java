package cse.usf.edu.AutoPt;

import android.os.Bundle;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

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
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import zephyr.android.BioHarnessBT.*;

public class ExerciseActivity extends Activity {
	
	BluetoothAdapter adapter = null;
	BTClient btClient;
	ZephyrProtocol _protocol;
	NewConnectedListener cxnListener;
	private final int HEART_RATE = 0x100;
	private final int RESPIRATION_RATE = 0x101;
	private final int POSTURE = 0x103;
	private final int PEAK_ACCLERATION = 0x104;
	String strHeartRate = "";
	String strRespirationRate = "";
	String strPosture = "";
	String strPeakAccel = "";
	String pId = Integer.toString(patientDetailActivity.user.pId);
	String id = "";
	boolean ctd = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise);
	
		Bundle extras = getIntent().getExtras();
		  
		id = extras.getString("id");
		String notes = extras.getString("notes");
		
		TextView notesText = (TextView) findViewById(R.id.txtNotes);
		notesText.setText(notes);
		
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
        			 
        			 tv1 = (TextView) findViewById(R.id.txtPosture);
        			 tv1.setText("000");
        			 
        			 tv1 = (TextView) findViewById(R.id.txtPeakAccel);
        			 tv1.setText("0.0");
        			 
        			if (btClient.IsConnected()) {
        				btClient.start();
        				TextView tv = (TextView) findViewById(R.id.txtStatusMsg);
        				String ErrorText  = "Connected to " + DeviceName;
						tv.setText(ErrorText);
						ctd = true;

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
					
					if (!ctd)
						return;
					
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
        
        Button btnSend = (Button) findViewById(R.id.btnSendData);
        btnSend.setOnClickListener(new OnClickListener() {

		    @Override
		    public void onClick(View arg0) { 
		    	String link = "http://usfandroidapp.net63.net/sessionUpdate.php";
		    	URL url;
				try {
					url = new URL(link);
					new SessionUpdater().execute(url);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
		    }
		});
        
        
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
    			if (tv != null) {
    				tv.setText(HeartRatetext);
    				strHeartRate = HeartRatetext;
    			}
    		break;
    		
    		case RESPIRATION_RATE:
    			String RespirationRatetext = msg.getData().getString("RespirationRate");
    			tv = (TextView) findViewById(R.id.txtBreathing);
    			if (tv != null) {
    				tv.setText(RespirationRatetext);
    				strRespirationRate = RespirationRatetext;
    			}
    		
    		break;
    		
    		case POSTURE:
    			String PostureText = msg.getData().getString("Posture");
    			tv = (TextView)findViewById(R.id.txtPosture);
    			if (tv != null) {
    				tv.setText(PostureText);
    				strPosture = PostureText;
    			}
    		break;
    		
    		case PEAK_ACCLERATION:
    			String PeakAccText = msg.getData().getString("PeakAcceleration");
    			tv = (TextView)findViewById(R.id.txtPeakAccel);
    			if (tv != null) {
    				tv.setText(PeakAccText);
    				strPeakAccel = PeakAccText;
    			}
    			
    		break;
    		}
    	}
    };
    
    class SessionUpdater extends AsyncTask<URL, Integer, Long> {
		
	    protected Long doInBackground(URL... urls) {
	    	
		    try {
		    	final ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
		    	nameValuePairs.add(new BasicNameValuePair("id", id));
			    nameValuePairs.add(new BasicNameValuePair("patientId", pId));
			    nameValuePairs.add(new BasicNameValuePair("heartRate", strHeartRate));
			    nameValuePairs.add(new BasicNameValuePair("breathingRate", strRespirationRate));
			    nameValuePairs.add(new BasicNameValuePair("posture", strPosture));
			    nameValuePairs.add(new BasicNameValuePair("peakAccel", strPeakAccel));
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://usfandroidapp.net63.net/sessionUpdate.php");
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        
		        Log.i("postData", response.getStatusLine().toString());
		    }

		    catch(Exception e) {
		        Log.e("error msg:", "Error in http connection " + e.toString());
		    }  
	    	
	        return null;
	    }

	    protected void onProgressUpdate(Integer... progress) {
	        
	    }

	    protected void onPostExecute(Long result) {
	    	Toast.makeText(getApplicationContext(), "Update complete.", Toast.LENGTH_SHORT).show();
	    }
	}
    
}
