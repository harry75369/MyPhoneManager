package com.myphonemanager.service;

import com.myphonemanager.data.MySQLiteHelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class MessageReceiver extends BroadcastReceiver {

	final static String TAG = "MessageReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		MySQLiteHelper database = new MySQLiteHelper(context);
		
		Bundle pudsBundle = intent.getExtras();
        Object[] pdus = (Object[]) pudsBundle.get("pdus");
        SmsMessage messages = SmsMessage.createFromPdu((byte[]) pdus[0]);
        Log.i(TAG, "Message from " + messages.getOriginatingAddress() + ": " + messages.getMessageBody());
        
        if(messages.getMessageBody().contains("Hi")) {
        	database.saveMessage(messages);
        	abortBroadcast();
        }
	}


}
