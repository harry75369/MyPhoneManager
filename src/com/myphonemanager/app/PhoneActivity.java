package com.myphonemanager.app;

import com.myphonemanager.R;
import com.myphonemanager.data.BadPhone;
import com.myphonemanager.data.GoodPhone;
import com.myphonemanager.data.MySQLiteHelper;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("NewApi")
public class PhoneActivity extends Activity {
	
	final static String TAG = "PhoneActivity"; 

	static String[] menuItems = {"增加拒绝号码", "增加亲情号码", "拒绝号码列表", "亲情号码列表"};
	private Context context = this;
	private MySQLiteHelper database = new MySQLiteHelper(context);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone);
		
		ListView menuList = (ListView) findViewById(R.id.phone_menu_list);
		ArrayAdapter<String> adap = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, menuItems);
		menuList.setAdapter(adap);
		menuList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch ( arg2 ) {
				case 0: { // 增加拒绝号码
					AlertDialog.Builder builder = new AlertDialog.Builder(context);

					final EditText phone_name = new EditText(context);
					final EditText phone_number = new EditText(context);
					phone_name.setHint("拒绝电话名字（默认：垃圾）");
					phone_name.setInputType(InputType.TYPE_CLASS_TEXT);
					phone_number.setHint("拒绝电话号码");
					phone_number.setInputType(InputType.TYPE_CLASS_PHONE);
					
					LinearLayout view = new LinearLayout(context);
					view.setOrientation(LinearLayout.VERTICAL);
					view.addView(phone_name);
					view.addView(phone_number);
					builder.setView(view);
					
					builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							String name = phone_name.getText().toString();
							String number = phone_number.getText().toString();
							Log.i(TAG, "Get name=" + name + " number=" + number);
							BadPhone phone = new BadPhone(name, number);
							if (phone.isValid() ) {
								database.addBadPhone(phone);
								Toast.makeText(context, "拒绝号码已添加", Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(context, "请输入全部正确信息后再添加", Toast.LENGTH_SHORT).show();
							}
						}
					});
					builder.setNegativeButton(R.string.cancel, null);
					builder.show();
					break;
				}
				case 1: { // 增加亲情号码
					AlertDialog.Builder builder = new AlertDialog.Builder(context);

					final EditText phone_name = new EditText(context);
					final EditText phone_number = new EditText(context);
					final EditText phone_msg = new EditText(context);
					phone_name.setHint("亲情电话名字（默认：亲）");
					phone_name.setInputType(InputType.TYPE_CLASS_TEXT);
					phone_number.setHint("亲情电话号码");
					phone_number.setInputType(InputType.TYPE_CLASS_PHONE);
					phone_msg.setHint("亲情电话消息（默认：现在有事，待会回你）");
					phone_msg.setInputType(InputType.TYPE_CLASS_TEXT);
					
					LinearLayout view = new LinearLayout(context);
					view.setOrientation(LinearLayout.VERTICAL);
					view.addView(phone_name);
					view.addView(phone_number);
					view.addView(phone_msg);
					builder.setView(view);
					
					builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							String name = phone_name.getText().toString();
							String number = phone_number.getText().toString();
							String msg = phone_msg.getText().toString();
							Log.i(TAG, "Get name=" + name + " number=" + number + " msg=" + msg);
							GoodPhone phone = new GoodPhone(name, number, msg);
							if ( phone.isValid() ) {
								database.addGoodPhone(phone);
								Toast.makeText(context, "亲情号码已添加", Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(context, "请输入全部正确信息后再添加", Toast.LENGTH_SHORT).show();
							}
						}
					});
					builder.setNegativeButton(R.string.cancel, null);
					builder.show();
					break;
				}
				case 2: { // 拒绝号码列表
					Intent intent = new Intent(context, ListBadPhoneActivity.class);
					startActivity(intent);
					break;
				}
				case 3: { // 亲情号码列表
					Intent intent = new Intent(context, ListGoodPhoneActivity.class);
					startActivity(intent);
					break;
				}
				}
			}
			
		});
	}

}
