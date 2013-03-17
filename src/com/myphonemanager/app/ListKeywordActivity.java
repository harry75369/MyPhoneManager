package com.myphonemanager.app;

import java.util.List;

import com.myphonemanager.R;
import com.myphonemanager.data.MySQLiteHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class ListKeywordActivity extends Activity {
	
	final static String TAG = "ListKeywordActivity"; 
	
	private Context context = this;
	private MySQLiteHelper database = new MySQLiteHelper(context);
	
	class KeywordAdapter extends BaseAdapter {

		private Context context;
		private LayoutInflater listContainer;
		private List<String> keywords;
		private ListView keywordsList;
		
		public final class ItemsView {
			public TextView keyword;
			public Button del;
		}
		
		KeywordAdapter(Context context, ListView keywordsList) {
			this.context = context;
			this.listContainer = LayoutInflater.from(context);
			this.keywords = database.getKeywords();
			this.keywordsList = keywordsList;
		}
		
		@Override
		public int getCount() {
			return keywords.size();
		}

		@Override
		public Object getItem(int arg0) {
			return keywords.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(final int position, View view, ViewGroup parent) {
			ItemsView itemsView = null;
			if ( view == null ) {
				itemsView = new ItemsView();
				view = listContainer.inflate(R.layout.list_keyword, null);
				itemsView.keyword = (TextView)view.findViewById(R.id.keyword);
				itemsView.del = (Button)view.findViewById(R.id.delete);
				view.setTag(itemsView);
			}
			else {
				itemsView = (ItemsView)view.getTag();
			}

			final String keyword = keywords.get(position);
			itemsView.keyword.setText(keyword);
			
			itemsView.del.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setTitle("确认");
					builder.setMessage("删除 " + keyword + " ?");
					builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							database.delKeyword(keyword);
							Toast.makeText(context, "已删过滤关键字 "+keyword, Toast.LENGTH_SHORT).show();
							keywordsList.setAdapter(new KeywordAdapter(context, keywordsList));
						}
						
					});
					builder.setNegativeButton(R.string.cancel, null);
					builder.show();
				}
				
			});
			
			return view;
		}
		
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_keyword);
		
		final ListView keywordsList = (ListView) findViewById(R.id.keyword_list);
		keywordsList.setAdapter(new KeywordAdapter(context, keywordsList));
	}

}
