package com.myphonemanager.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myphonemanager.R;
import com.myphonemanager.data.Message;
import com.myphonemanager.data.MySQLiteHelper;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ListMessageActivity extends Activity {
	
	final static String TAG = "ListMessageActivity"; 
	
	private Context context = this;
	private MySQLiteHelper database = new MySQLiteHelper(context);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_message);
		
		final ListView msgsList = (ListView) findViewById(R.id.message_list);
		List<Message> msgs = database.getAllMessages();
		msgsList.setAdapter(new SimpleAdapter(context, getData(msgs), R.layout.list_message,
				new String[] {"from", "date", "body"},
				new int[] {R.id.from, R.id.date, R.id.body}));
		
	}

	private List<Map<String, Object>> getData(List<Message> msgs) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		for ( Message msg : msgs ) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("from", msg.getFrom());
			map.put("date", msg.getDate());
			map.put("body", msg.getBody());
			list.add(map);
		}
		
		return list;
	}
}
