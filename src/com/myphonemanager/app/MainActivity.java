package com.myphonemanager.app;

import com.myphonemanager.R;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class MainActivity extends Activity {
	
	static String[] menuItems = {"来电管理", "短信管理", "帮助"};
	private Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ListView menuList = (ListView) findViewById(R.id.main_menu_list);
		ArrayAdapter<String> adap = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, menuItems);
		menuList.setAdapter(adap);
		menuList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch ( arg2 ) {
				case 0: { // 来电管理 
					Intent intent = new Intent(context, PhoneActivity.class);
					startActivity(intent);
					break;
				}
				case 1: { // 短信管理
					Intent intent = new Intent(context, MessageActivity.class);
					startActivity(intent);
					break;
				}
				case 2: { // 帮助
					Intent intent = new Intent(context, HelpActivity.class);
					startActivity(intent);
					break;
				}
				}
			}
			
		});
	}
	
}
