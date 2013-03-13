package com.myphonemanager.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.myphonemanager.R;
import com.myphonemanager.data.GoodPhone;
import com.myphonemanager.data.MySQLiteHelper;

public class ListGoodPhoneActivity extends Activity {
	
	final static String TAG = "ListGoodPhoneActivity"; 
	
	private Context context = this;
	private MySQLiteHelper database = new MySQLiteHelper(context);

	class GoodPhoneAdapter extends BaseAdapter {

		private Context context;
		private int count;
		private LayoutInflater listContainer;
		final ListView phonesList;
		public final class ItemsView {
			public TextView name;
			public TextView number;
			public TextView msg;
			public Button del;
			public ToggleButton toggle;
		}
		
		GoodPhoneAdapter(Context context, int count, ListView phonesList) {
			this.context = context;
			this.count = count;
			this.listContainer = LayoutInflater.from(context);
			this.phonesList = phonesList;
		}
		
		@Override
		public int getCount() {
			return count;
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
		
			final GoodPhone phone = database.getGoodPhoneByPosition(position);
			itemsView.name.setText(phone.getName());
			itemsView.number.setText(phone.getNumber());
			itemsView.msg.setText(phone.getMsg());
			itemsView.toggle.setChecked(phone.getToggle());
			
			itemsView.del.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setTitle("确认");
					builder.setMessage("删除 " + phone.getNumber() + " ?");
					builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							database.delGoodPhone(phone);
							Toast.makeText(context, "已删除亲情号码 "+phone.getNumber(), Toast.LENGTH_SHORT).show();
							int goodPhonesCount = database.getGoodPhonesCount();
							phonesList.setAdapter(new GoodPhoneAdapter(context, goodPhonesCount, phonesList));
						}
						
					});
					builder.setNegativeButton(R.string.cancel, null);
					builder.show();
				}
				
			});
			
			final ToggleButton toggle = itemsView.toggle;
			toggle.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Log.i(TAG, "Auto reply set to: " + toggle.isChecked());
					phone.setToggle(toggle.isChecked());
					database.updateGoodPhone(phone);
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
		int goodPhonesCount = database.getGoodPhonesCount();
		phonesList.setAdapter(new GoodPhoneAdapter(context, goodPhonesCount, phonesList));
	}
}
