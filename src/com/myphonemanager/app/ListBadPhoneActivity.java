package com.myphonemanager.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myphonemanager.R;
import com.myphonemanager.data.BadPhone;
import com.myphonemanager.data.MySQLiteHelper;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class ListBadPhoneActivity extends Activity {

	final static String TAG = "ListBadPhoneActivity"; 
	
	private Context context = this;
	private MySQLiteHelper database = new MySQLiteHelper(context);	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_bad_phone);
		
		final ListView phonesList = (ListView) findViewById(R.id.bad_phone_list);
		List<BadPhone> badPhones = database.getAllBadPhones();
		phonesList.setAdapter(new SimpleAdapter(context, getData(badPhones), R.layout.list_bad_phone,
				new String[] {"name", "number"},
				new int[] {R.id.name, R.id.number}));
		
		phonesList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				final int position = arg2;
				final BadPhone phone = database.getBadPhoneByPosition(position);
				
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("È·ÈÏ");
				builder.setMessage("ÉŸ³ý " + phone.getNumber() + " ?");
				builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						database.delBadPhone(phone);
						Toast.makeText(context, "ÒÑÉŸ³ýŸÜŸøºÅÂë "+phone.getNumber(), Toast.LENGTH_SHORT).show();
						List<BadPhone> badPhones = database.getAllBadPhones();
						phonesList.setAdapter(new SimpleAdapter(context, getData(badPhones), R.layout.list_bad_phone,
								new String[] {"name", "number"},
								new int[] {R.id.name, R.id.number}));
					}
					
				});
				builder.setNegativeButton(R.string.cancel, null);
				builder.show();
				
				return false;
			}
			
		});
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
