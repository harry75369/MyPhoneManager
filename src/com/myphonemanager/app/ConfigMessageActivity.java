package com.myphonemanager.app;

import com.myphonemanager.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ConfigMessageActivity extends Activity {
	
	final static String TAG = "ConfigMessageActivity";
	
	static String[] menuItems = {"同黑白则拦截", "重置统计数据"};
	private Context context = this;
	
	class ConfigMessageActivityAdapter extends BaseAdapter {

		private Context context;
		private LayoutInflater inflater;
		
		ConfigMessageActivityAdapter(Context context) {
			this.context = context;
			this.inflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			return menuItems.length;
		}

		@Override
		public String getItem(int position) {
			return menuItems[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			if ( position == 0 ) { // 拦截同时出现在黑白名单中的号码
				view = inflater.inflate(R.layout.toggle_config, null);
				TextView text = (TextView) view.findViewById(R.id.config_item);
				ToggleButton toggle = (ToggleButton) view.findViewById(R.id.toggle_item);
				text.setText(menuItems[position]);
				toggle.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						
					}
				});
			} else if ( position == 1 ) { // 重置统计数据

				view = inflater.inflate(R.layout.button_config, null);
				TextView text = (TextView) view.findViewById(R.id.config_item);
				Button clear = (Button) view.findViewById(R.id.button_item);
				text.setText(menuItems[position]);
				clear.setText(R.string.clear);
				clear.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Toast.makeText(context, "已清空统计数据", Toast.LENGTH_SHORT).show();
					}
					
				});
			}
			return view;
		}
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config_message);

		final ListView configList = (ListView) findViewById(R.id.config_list);
		configList.setAdapter(new ConfigMessageActivityAdapter(context));
	}

}
