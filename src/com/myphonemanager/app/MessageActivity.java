package com.myphonemanager.app;

import com.myphonemanager.R;
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

public class MessageActivity extends Activity {
	
	final static String TAG = "MessageActivity"; 
	
	static String[] menuItems = {"设置", "添加过滤关键字", "过滤关键字列表", "已拦截短信"};
	private Context context = this;
	private MySQLiteHelper database = new MySQLiteHelper(context);

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
					Intent intent = new Intent(context, ConfigMessageActivity.class);
					startActivity(intent);
					break;
				}
				case 1: { // 添加过滤关键字
					AlertDialog.Builder builder = new AlertDialog.Builder(context);

					final EditText keyword_name = new EditText(context);
					keyword_name.setHint("过滤关键字");
					keyword_name.setInputType(InputType.TYPE_CLASS_TEXT);
					
					LinearLayout view = new LinearLayout(context);
					view.setOrientation(LinearLayout.VERTICAL);
					view.addView(keyword_name);
					builder.setView(view);
					
					builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
						
						@SuppressLint("NewApi")
						@Override
						public void onClick(DialogInterface dialog, int which) {
							String keyword = keyword_name.getText().toString();
							Log.i(TAG, "Get keyword=" + keyword);

							if ( keyword != null && !keyword.isEmpty() ) {
								database.addKeyword(keyword);
								Toast.makeText(context, "过滤关键字已添加："+keyword, Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(context, "请输入过滤关键字", Toast.LENGTH_SHORT).show();
							}
						}
					});
					builder.setNegativeButton(R.string.cancel, null);
					builder.show();
					break;
				}
				case 2: { // 过滤关键字列表
					Intent intent = new Intent(context, ListKeywordActivity.class);
					startActivity(intent);
					break;
				}
				case 3: { // 已拦截短信
					Intent intent = new Intent(context, ListMessageActivity.class);
					startActivity(intent);
					break;
				}
				}
			}
			
		});
	}

}
