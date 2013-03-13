package com.myphonemanager.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myphonemanager.R;
import com.myphonemanager.data.GoodPhone;
import com.myphonemanager.data.MySQLiteHelper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


public class ListGoodPhoneActivity extends Activity {
	
	final static String TAG = "ListGoodPhoneActivity"; 
	
	private Context context = this;
	private MySQLiteHelper database = new MySQLiteHelper(context);

	class GoodPhoneAdapter extends BaseAdapter {

		private Context context;
		private List<Map<String, Object>> items;
		private LayoutInflater listContainer;
		public final class ItemsView {
			public TextView name;
			public TextView number;
			public TextView msg;
			public Button del;
			public ToggleButton toggle;
		}
		
		GoodPhoneAdapter(Context context, List<Map<String, Object>> items) {
			this.context = context;
			this.items = items;
			this.listContainer = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			ItemsView itemsView = null;
			if ( view == null ) {
				itemsView = new ItemsView();
				view = listContainer.inflate(R.layout.list_good_phone, null);
				itemsView.name = (TextView)view.findViewById(R.id.name);
				itemsView.number = (TextView)view.findViewById(R.id.number);
				itemsView.msg = (TextView)view.findViewById(R.id.msg);
				itemsView.del = (Button)view.findViewById(R.id.delete);
				itemsView.toggle = (ToggleButton)view.findViewById(R.id.toggle_reply);
				view.setTag(itemsView);
			}
			else {
				itemsView = (ItemsView)view.getTag();
			}
			
			
			/*
			itemsView.name.setText((String)items.get(position).get("name"));
			itemsView.number.setText((String)items.get(position).get("number"));
			itemsView.msg.setText((String)items.get(position).get("msg"));
			itemsView.toggle.setChecked((Boolean)items.get(position).get("toggle"));
			*/
			final GoodPhone phone = database.getGoodPhoneByPosition(position);
			itemsView.name.setText(phone.getName());
			itemsView.number.setText(phone.getNumber());
			itemsView.msg.setText(phone.getMsg());
			itemsView.toggle.setChecked(phone.getToggle());
			
			
			itemsView.del.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Toast.makeText(context, "已删除亲情号码 "+phone.getNumber(), Toast.LENGTH_SHORT).show();
				}
				
			});
			
			
			return view;
		}
		
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_good_phone);

		final ListView phonesList = (ListView) findViewById(R.id.good_phone_list);
		List<GoodPhone> goodPhones = database.getAllGoodPhones();
		phonesList.setAdapter(new GoodPhoneAdapter(context, getData(goodPhones)));

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
						phonesList.setAdapter(new GoodPhoneAdapter(context, getData(goodPhones)));
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
			map.put("toggle", phone.getToggle());
			list.add(map);
		}
		return list;
	}

}
