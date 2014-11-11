package cse.usf.edu.AutoPt;

import android.app.Activity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import zephyr.android.BioHarnessBT.*;

public class NewConnectedListener extends ConnectListenerImpl
{
	private Handler _OldHandler;
	private Handler _aNewHandler; 
	final int GP_MSG_ID = 0x20;
	final int BREATHING_MSG_ID = 0x21;
	final int ECG_MSG_ID = 0x22;
	final int RtoR_MSG_ID = 0x24;
	final int ACCEL_100mg_MSG_ID = 0x2A;
	final int SUMMARY_MSG_ID = 0x2B;
	
	
	private int GP_HANDLER_ID = 0x20;
	
	private final int HEART_RATE = 0x100;
	private final int RESPIRATION_RATE = 0x101;
	private final int SKIN_TEMPERATURE = 0x102;
	private final int POSTURE = 0x103;
	private final int PEAK_ACCLERATION = 0x104;
	private final int ACTIVITY_LEVEL = 0x105; // should be +/- 16
	
	/*Creating the different Objects for different types of Packets*/
	private GeneralPacketInfo GPInfo = new GeneralPacketInfo();
	private ECGPacketInfo ECGInfoPacket = new ECGPacketInfo();
	private BreathingPacketInfo BreathingInfoPacket = new  BreathingPacketInfo();
	private RtoRPacketInfo RtoRInfoPacket = new RtoRPacketInfo();
	private AccelerometerPacketInfo AccInfoPacket = new AccelerometerPacketInfo();
	private SummaryPacketInfo SummaryInfoPacket = new SummaryPacketInfo();
	
	private PacketTypeRequest RqPacketType = new PacketTypeRequest();
	public NewConnectedListener(Handler handler,Handler _NewHandler) {
		super(handler, null);
		_OldHandler= handler;
		_aNewHandler = _NewHandler;

		// TODO Auto-generated constructor stub

	}
	public void Connected(ConnectedEvent<BTClient> eventArgs) {
		System.out.println(String.format("Connected to BioHarness %s.", eventArgs.getSource().getDevice().getName()));
		/*Use this object to enable or disable the different Packet types*/
		RqPacketType.GP_ENABLE = true;
		RqPacketType.BREATHING_ENABLE = true;
		RqPacketType.LOGGING_ENABLE = true;
		
		
		//Creates a new ZephyrProtocol object and passes it the BTComms object
		ZephyrProtocol _protocol = new ZephyrProtocol(eventArgs.getSource().getComms(), RqPacketType);
		//ZephyrProtocol _protocol = new ZephyrProtocol(eventArgs.getSource().getComms(), );
		_protocol.addZephyrPacketEventListener(new ZephyrPacketListener() {
			public void ReceivedPacket(ZephyrPacketEvent eventArgs) {
				ZephyrPacketArgs msg = eventArgs.getPacket();
				byte CRCFailStatus;
				byte RcvdBytes;
				
				
				
				CRCFailStatus = msg.getCRCStatus();
				RcvdBytes = msg.getNumRvcdBytes() ;
				int MsgID = msg.getMsgID();
				byte [] DataArray = msg.getBytes();	
				switch (MsgID)
				{

				case GP_MSG_ID:

					
					Log.d("Packet Rec'd", "****************************");
					
					
					//Heart Rate
					
					int HRate =  GPInfo.GetHeartRate(DataArray);
					Message text1 = _aNewHandler.obtainMessage(HEART_RATE);
					Bundle b1 = new Bundle();
					b1.putString("HeartRate", String.valueOf(HRate));
					text1.setData(b1);
					_aNewHandler.sendMessage(text1);
					System.out.println("Heart Rate is "+ HRate);

					Log.d("Respiration", "****************************");
					//Respiration Rate
					
					double RespRate = GPInfo.GetRespirationRate(DataArray);
					
					text1 = _aNewHandler.obtainMessage(RESPIRATION_RATE);
					b1.putString("RespirationRate", String.valueOf(RespRate));
					text1.setData(b1);
					_aNewHandler.sendMessage(text1);
					System.out.println("Respiration Rate is "+ RespRate);
					
					Log.d("Temperature", "****************************");
					//Temperature
		
					/*double SkinTempDbl = GPInfo.GetSkinTemperature(DataArray);
					 text1 = _aNewHandler.obtainMessage(SKIN_TEMPERATURE);
					b1.putString("SkinTemperature", String.valueOf(SkinTempDbl));
					text1.setData(b1);
					_aNewHandler.sendMessage(text1);
					System.out.println("Skin Temperature is "+ SkinTempDbl);*/
					
					//Activity Level
					
					/*double activityLevel = GPInfo.GetVMU(DataArray);
					text1 = _aNewHandler.obtainMessage(ACTIVITY_LEVEL);
					b1.putString("ActivityLevel", String.valueOf(activityLevel));
					text1.setData(b1);
					_aNewHandler.sendMessage(text1);
					System.out.println("Activity Level is "+ activityLevel);*/
					
					Log.d("Posture", "****************************");
					//Posture					

					int PostureInt = GPInfo.GetPosture(DataArray);
					text1 = _aNewHandler.obtainMessage(POSTURE);
					b1.putString("Posture", String.valueOf(PostureInt));
					text1.setData(b1);
					_aNewHandler.sendMessage(text1);
					System.out.println("Posture is "+ PostureInt);
					
					Log.d("Acceleration", "****************************");
					//Peak Acceleration

					double PeakAccDbl = GPInfo.GetPeakAcceleration(DataArray);
					text1 = _aNewHandler.obtainMessage(PEAK_ACCLERATION);
					b1.putString("PeakAcceleration", String.valueOf(PeakAccDbl));
					text1.setData(b1);
					_aNewHandler.sendMessage(text1);
					System.out.println("Peak Acceleration is "+ PeakAccDbl);	
				
				/*byte ROGStatus = GPInfo.GetROGStatus(DataArray);
				System.out.println("ROG Status is "+ ROGStatus);
				
					break;
				*/
					
				}
			}
		});
	}
	
}
