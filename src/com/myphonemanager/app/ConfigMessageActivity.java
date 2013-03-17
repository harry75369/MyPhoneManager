package com.myphonemanager.app;

import com.myphonemanager.R;
import com.myphonemanager.data.MySQLiteHelper;

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
	
	static String[] menuItems = {"启用拦截功能", "同黑白则拦截", "无关键字也是垃圾短信的概率", "清空所有已拦截短信"};
	private Context context = this;
	private MySQLiteHelper database = new MySQLiteHelper(context);
	
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
			if ( position == 0 ) { // 启用拦截功能
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
			}
			else if ( position == 1 ) { // 拦截同时出现在黑白名单中的号码
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
			} else if ( position == 2 ) { // 重置统计数据
				view = inflater.inflate(R.layout.probability_config, null);
				TextView text = (TextView) view.findViewById(R.id.config_item);
				final TextView prob = (TextView) view.findViewById(R.id.probability);
				Button clear = (Button) view.findViewById(R.id.button_item);
				text.setText(menuItems[position]);
				double probability = database.getProbability(false);
				prob.setText("" + probability*100 + "%");
				clear.setText(R.string.reset);
				clear.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						database.resetProbability();
						double probability = database.getProbability(false);
						prob.setText("" + probability*100 + "%");
						Toast.makeText(context, "已重置概率", Toast.LENGTH_SHORT).show();
					}
					
				});
			} else if ( position == 3 ) { // 清空所有已拦截短信
				view = inflater.inflate(R.layout.button_config, null);
				TextView text = (TextView) view.findViewById(R.id.config_item);
				Button clear = (Button) view.findViewById(R.id.button_item);
				text.setText(menuItems[position]);
				clear.setText(R.string.clear);
				clear.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						database.cleanAllMessages();
						Toast.makeText(context, "已清空垃圾短信", Toast.LENGTH_SHORT).show();
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
