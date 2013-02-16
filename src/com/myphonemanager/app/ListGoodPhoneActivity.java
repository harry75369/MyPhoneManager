package com.myphonemanager.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myphonemanager.R;
import com.myphonemanager.data.GoodPhone;
import com.myphonemanager.data.MySQLiteHelper;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.app.Activity;
import android.content.Context;

public class ListGoodPhoneActivity extends Activity {
	
	final static String TAG = "ListGoodPhoneActivity"; 
	
	private Context context = this;
	private MySQLiteHelper database = new MySQLiteHelper(context);	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_good_phone);

		ListView phonesList = (ListView) findViewById(R.id.good_phone_list);
		List<GoodPhone> goodPhones = database.getAllGoodPhones();
		SimpleAdapter adapter = new SimpleAdapter(context, getData(goodPhones), R.layout.list_good_phone,
				new String[] {"name", "number", "msg"},
				new int[] {R.id.name, R.id.number, R.id.msg});
		
		phonesList.setAdapter(adapter);
	}

	private List<Map<String, Object>> getData(List<GoodPhone> goodPhones) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		for ( GoodPhone phone : goodPhones ) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", phone.getName());
			map.put("number", phone.getNumber());
			map.put("msg", phone.getMsg());
			list.add(map);
		}
		return list;
	}

}
