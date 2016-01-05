package com.hys.news1.cache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class BitmapLruCache implements ImageCache {

	//LruCache��ʵ���ϴ���һ��map���ϣ�����ʹ���������
	//Lru�㷨����������ʹ���㷨
	private LruCache<String, Bitmap> mCache;
	
	 public BitmapLruCache(int maxSize) {
		// TODO Auto-generated constructor stub
		 // ��С 10M
		
		 //LruCache�Ĺ��캯����maxSize���洢�ռ�
		 mCache=new LruCache<String, Bitmap>(maxSize){
			 // ͨ��key������Ӧ��item(bitmap)
			 //���㴫��ͼƬ�Ĵ�С
			@Override
			protected int sizeOf(String key, Bitmap value) {
				// TODO Auto-generated method stub
				//�õ�ͼƬ��С
				return value.getRowBytes()*value.getHeight();
			}
			 
		 };
		 
	}//Constructor
	
	 //�ӻ���ȡ
	@Override
	public Bitmap getBitmap(String key) {
		// TODO Auto-generated method stub
		return mCache.get(key);
	}

	//�浽 ����
	@Override
	public void putBitmap(String key, Bitmap value) {
		// TODO Auto-generated method stub
		mCache.put(key, value);
	}

}
