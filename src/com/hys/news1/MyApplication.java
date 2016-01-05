package com.hys.news1;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.hys.news1.cache.BitmapLruCache;

import android.app.Application;
import android.content.Context;
import android.view.WindowManager;

public class MyApplication extends Application {
//准备好队列
	

	//本地缓存空间
	private static final int maxDiskCacheBytes=40*1024*1024;
	//内存缓存空间
	private static final int maxMemCacheBytes=10*1024*1024;
	
	private RequestQueue mQueue;
	private ImageLoader imageLoader;
	private Gson gson=new Gson();
	// 屏幕宽度
	public static int screenWidth;
	@Override
	public void onCreate() {//mQueue  Volley初始化 
		// TODO Auto-generated method stub
		mQueue=Volley.newRequestQueue(getApplicationContext(), maxDiskCacheBytes);
		imageLoader=new ImageLoader(mQueue, new BitmapLruCache(maxMemCacheBytes));
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		screenWidth = wm.getDefaultDisplay().getWidth();
	}//onCreate
	public ImageLoader getImageLoader() {
		return imageLoader;
	}

	public RequestQueue getRequestQueue() {
		return mQueue;
	}
	
	public Gson getGson(){
		return gson;
	}
}//MyApplication_cls
