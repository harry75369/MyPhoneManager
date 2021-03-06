package com.myphonemanager.service;

import java.lang.reflect.Method;
import java.util.ArrayList;

import com.myphonemanager.data.GoodPhone;
import com.myphonemanager.data.GoodnessIndicator;
import com.myphonemanager.data.MySQLiteHelper;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class ListeningService extends Service {
	
	final static String TAG = "ListeningService"; 
	private MySQLiteHelper database = null;
	private StateListener listener = null;

	private void createAndActivateListener()
	{
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		listener = new StateListener();
		tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
	}
	
	private void inactivateListener()
	{
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		tm.listen(listener, PhoneStateListener.LISTEN_NONE);
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate()");
        createAndActivateListener();
        
        database = new MySQLiteHelper(getBaseContext());
    }

    class StateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            Log.i(TAG, "onCallStateChanged("+state+", "+incomingNumber+")");
            switch(state){
                case TelephonyManager.CALL_STATE_RINGING:
                	Log.i(TAG, "CALL_STATE_RINGING "+GoodnessIndicator.good);
                	try {
                		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                        Method m = Class.forName(tm.getClass().getName()).getDeclaredMethod("getITelephony");
                        m.setAccessible(true);

                        com.android.internal.telephony.ITelephony  telephonyService = (com.android.internal.telephony.ITelephony) m.invoke(tm);
                        //telephonyService.silenceRinger();
                        boolean unknown = true;
                        
                        if ( database.hasBadPhone(incomingNumber) ) {
                        	Log.i(TAG, incomingNumber + " is bad");
                        	unknown = false;
                        	telephonyService.endCall();
                        }
                        if ( database.hasGoodPhone(incomingNumber) ) {
                        	Log.i(TAG, incomingNumber + " is good");
                        	unknown = false;
                        	GoodnessIndicator.good = true;
                        }
                        if ( unknown ){
                        	Log.i(TAG, incomingNumber + " is unknown");
                        }
                    }
                    catch (Exception e)
                    {
                        Log.d(TAG, e.toString());
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                	Log.i(TAG, "CALL_STATE_OFFHOOK "+GoodnessIndicator.good);
                	GoodnessIndicator.good = false;
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                	Log.i(TAG, "CALL_STATE_IDLE "+GoodnessIndicator.good);
                	if ( GoodnessIndicator.good == true && incomingNumber != null && database.hasGoodPhone(incomingNumber) ) {
                		GoodPhone phone = database.getGoodPhone(incomingNumber);
                		if ( phone.getToggle() ) // auto reply is on
                		{
	                		SmsManager manager = SmsManager.getDefault();
	                		if ( manager != null ) {
	                			String msg = phone.getMsg();
	                			ArrayList<String> list = manager.divideMessage(msg);
	                			if ( list.size() > 1 ) {
	                				manager.sendMultipartTextMessage(incomingNumber, null, list, null, null);
	                			} else {
	                				manager.sendTextMessage(incomingNumber, null, msg, null, null);
	                			}
	                			Toast.makeText(getBaseContext(), "发送亲情短信成功："+incomingNumber, Toast.LENGTH_LONG).show();
	                		} else {
	                			Toast.makeText(getBaseContext(), "发送亲情短信失败："+incomingNumber, Toast.LENGTH_LONG).show();
	                		}
                		}
                	}
                	GoodnessIndicator.good = false;
                    break;
            }
            
        }
    };

    @Override
    public void onDestroy() {
    	Log.i(TAG, "onDestroy()");
    	inactivateListener();
    }
}
