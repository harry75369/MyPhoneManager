package com.myphonemanager.service;

import java.util.List;

import com.myphonemanager.data.MySQLiteHelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

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
        if ( incomingNumber.startsWith("+86") ) {
        	incomingNumber = incomingNumber.substring(3);
        }
        boolean bad = database.hasBadPhone(incomingNumber);
        boolean good = database.hasGoodPhone(incomingNumber);
        boolean def_to_intercept = database.isDefaultToIntercept();
        boolean has_keyword = false;
        
        List<String> keywords = database.getKeywords();
    	String body = messages.getMessageBody();
    	for ( String k : keywords ) {
    		if ( body.contains(k) )
    		{
    			database.addHasKeywordMessageCount();
    			has_keyword = true;
    			break;
    		}
    	}
    	
    	if ( !database.isMessageInterceptionOn() ) // if interception is off
    	{
    		database.addMessageCount();
    		return;
    	}
        
        if ( bad && good ) { // in both list
        	if ( def_to_intercept )
        	{
        		database.saveMessage(messages, has_keyword);
        		database.addMessageCount();
        		abortBroadcast();
        		Toast.makeText(context, "已拦截短信："+incomingNumber, Toast.LENGTH_SHORT).show();
        		return;
        	}
        	else
        	{
        		database.addMessageCount();
        		return;
        	}
        }
        else if ( bad && !good ) {
        	database.saveMessage(messages, has_keyword);
        	database.addMessageCount();
    		abortBroadcast();
    		Toast.makeText(context, "已拦截短信："+incomingNumber, Toast.LENGTH_SHORT).show();
    		return;
        }
        else if ( !bad && good ) {
        	database.addMessageCount();
        	return;
        }
        else if ( !bad && !good ) {
        	if ( has_keyword )
        	{
        		database.saveMessage(messages, has_keyword);
        		database.addMessageCount();
        		abortBroadcast();
        		Toast.makeText(context, "已拦截短信："+incomingNumber, Toast.LENGTH_SHORT).show();
        		return;
        	}
        	double prob = database.getProbability(has_keyword);
        	if ( prob >= 0.5 )
        	{
        		database.saveMessage(messages, has_keyword);
        		database.addMessageCount();
        		abortBroadcast();
        		Toast.makeText(context, "已拦截短信："+incomingNumber, Toast.LENGTH_SHORT).show();
        		return;
        	} else {
        		database.addMessageCount();
        		return;
        	}
        }
	}

}
