package com.hys.news1.cache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class BitmapLruCache implements ImageCache {

	//LruCache中实际上存在一个map集合，缓存就存在这里面
	//Lru算法，近期最少使用算法
	private LruCache<String, Bitmap> mCache;
	
	 public BitmapLruCache(int maxSize) {
		// TODO Auto-generated constructor stub
		 // 大小 10M
		
		 //LruCache的构造函数，maxSize最大存储空间
		 mCache=new LruCache<String, Bitmap>(maxSize){
			 // 通过key返回相应的item(bitmap)
			 //计算传入图片的大小
			@Override
			protected int sizeOf(String key, Bitmap value) {
				// TODO Auto-generated method stub
				//得到图片大小
				return value.getRowBytes()*value.getHeight();
			}
			 
		 };
		 
	}//Constructor
	
	 //从缓存取
	@Override
	public Bitmap getBitmap(String key) {
		// TODO Auto-generated method stub
		return mCache.get(key);
	}

	//存到 缓存
	@Override
	public void putBitmap(String key, Bitmap value) {
		// TODO Auto-generated method stub
		mCache.put(key, value);
	}

}
