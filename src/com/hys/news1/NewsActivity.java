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
		//��JS��������ִ��
		wb_explorer.getSettings().setJavaScriptEnabled(true);
		//webview������Ҫ��ʾ����ҳurl
		wb_explorer.loadUrl(url);
		//����webviewClient����url��ʾ��ҳ//����һЩhtml��ҳ������
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
