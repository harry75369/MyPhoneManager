package com.myphonemanager.app;

import com.myphonemanager.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MessageActivity extends Activity {
	
	final static String TAG = "MessageActivity"; 
	
	static String[] menuItems = {"设置", "已拦截短信"};
	private Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		
		ListView menuList = (ListView) findViewById(R.id.message_menu_list);
		ArrayAdapter<String> adap = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, menuItems);
		menuList.setAdapter(adap);
		menuList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch ( arg2 ) {
				case 0: { // 设置
					break;
				}
				case 1: { // 已拦截短息
					Intent intent = new Intent(context, ListMessageActivity.class);
					startActivity(intent);
					break;
				}
				}
				
			}
			
		});
	}

}
