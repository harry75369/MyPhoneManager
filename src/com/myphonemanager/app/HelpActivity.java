package com.myphonemanager.app;

import com.myphonemanager.R;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.annotation.SuppressLint;
import android.app.Activity;

@SuppressLint("SetJavaScriptEnabled")
public class HelpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		
		WebView webView = (WebView) findViewById(R.id.help_html);
		WebSettings settings = webView.getSettings();
	    settings.setJavaScriptEnabled(true);

	    webView.loadUrl("file:///android_asset/help.html");
	}

}
