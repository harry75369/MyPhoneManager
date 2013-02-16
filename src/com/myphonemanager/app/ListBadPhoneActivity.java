package com.myphonemanager.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myphonemanager.R;
import com.myphonemanager.data.BadPhone;
import com.myphonemanager.data.MySQLiteHelper;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.app.Activity;
import android.content.Context;

public class ListBadPhoneActivity extends Activity {

	final static String TAG = "ListBadPhoneActivity"; 
	
	private Context context = this;
	private MySQLiteHelper database = new MySQLiteHelper(context);	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_bad_phone);
		
		ListView phonesList = (ListView) findViewById(R.id.bad_phone_list);
		List<BadPhone> badPhones = database.getAllBadPhones();
		SimpleAdapter adapter = new SimpleAdapter(context, getData(badPhones), R.layout.list_bad_phone,
				new String[] {"name", "number"},
				new int[] {R.id.name, R.id.number});
		
		phonesList.setAdapter(adapter);
	}

	private List<Map<String, Object>> getData(List<BadPhone> badPhones) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		for ( BadPhone phone : badPhones ) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", phone.getName());
			map.put("number", phone.getNumber());
			list.add(map);
		}
		return list;
	}

}
