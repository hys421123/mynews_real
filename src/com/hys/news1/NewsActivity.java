package com.hys.news1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsActivity extends Activity {
	private String url;
	private View loading;
	private WebView wb_explorer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
		loading=findViewById(R.id.loading);
		url=getIntent().getStringExtra("url");
		wb_explorer=(WebView) findViewById(R.id.wb_explorer);
		Log.i("hys", url+";");
		initWebView();
		
	}//onCreate
	private void initWebView(){
		//让JS代码正常执行
		wb_explorer.getSettings().setJavaScriptEnabled(true);
		//webview加载需要显示的网页url
		wb_explorer.loadUrl(url);
		//利用webviewClient根据url显示网页//处理一些html的页面内容
		wb_explorer.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				loading.setVisibility(View.INVISIBLE);
			}
		});
	}//initWebView
}//Act_cls
