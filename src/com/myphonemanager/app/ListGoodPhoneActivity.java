package com.myphonemanager.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myphonemanager.R;
import com.myphonemanager.data.GoodPhone;
import com.myphonemanager.data.MySQLiteHelper;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class ListGoodPhoneActivity extends Activity {
	
	final static String TAG = "ListGoodPhoneActivity"; 
	
	private Context context = this;
	private MySQLiteHelper database = new MySQLiteHelper(context);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_good_phone);

		final ListView phonesList = (ListView) findViewById(R.id.good_phone_list);
		List<GoodPhone> goodPhones = database.getAllGoodPhones();
		phonesList.setAdapter(new SimpleAdapter(context, getData(goodPhones), R.layout.list_good_phone,
				new String[] {"name", "number", "msg"},
				new int[] {R.id.name, R.id.number, R.id.msg}));

		phonesList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				final int position = arg2;
				final GoodPhone phone = database.getGoodPhoneByPosition(position);
				
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("确认");
				builder.setMessage("删除 " + phone.getNumber() + " ?");
				builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						database.delGoodPhone(phone);
						Toast.makeText(context, "已删除亲情号码 "+phone.getNumber(), Toast.LENGTH_SHORT).show();
						List<GoodPhone> goodPhones = database.getAllGoodPhones();
						phonesList.setAdapter(new SimpleAdapter(context, getData(goodPhones), R.layout.list_good_phone,
								new String[] {"name", "number", "msg"},
								new int[] {R.id.name, R.id.number, R.id.msg}));
					}
					
				});
				builder.setNegativeButton(R.string.cancel, null);
				builder.show();
				
				return false;
			}
			
		});
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
