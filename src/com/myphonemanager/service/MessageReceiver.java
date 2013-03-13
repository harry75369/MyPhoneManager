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
        String incomingNumber = messages.getOriginatingAddress();
        boolean bad = database.hasBadPhone(incomingNumber);
        boolean good = database.hasGoodPhone(incomingNumber);
        boolean def_to_intercept = true;//database.isDefaultToIntercept();
        
        if ( bad && good ) { // in both list
        	if ( def_to_intercept )
        	{
        		database.saveMessage(messages);
        		abortBroadcast();
        		return;
        	}
        	else
        	{
        		// do nothing
        		//return;
        	}
        }
        else if ( bad && !good ) {
        	database.saveMessage(messages);
    		abortBroadcast();
    		return;
        }
        else if ( !bad && good ) {
        	// do nothing
        	return;
        }
        else if ( !bad && !good ) { // apply bayesian rule
        	
        }
	}

}
