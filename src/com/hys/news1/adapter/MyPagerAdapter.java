package com.hys.news1.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.hys.news1.MyApplication;
import com.hys.news1.R;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class MyPagerAdapter extends PagerAdapter{
//TopͼƬ������ Adapter
	
	private String[] urls;
	private List<ImageView> list_imgview;
	private Context context;
	private String[] imgUrlAry;
	private ImageLoader imageLoader;
	
	 public MyPagerAdapter(String[] urls,Context context,ImageLoader imageLoader) {
		// TODO Auto-generated constructor stub
		 this.urls=urls;
		 this.context=context;
		 this.imageLoader=imageLoader;
		 addImgs();
		 
	}
//	public void setImgLoader( ImageLoader imageLoader){
//		 this.imageLoader=imageLoader;
//	 }
//	 
	 private void addImgs(){//���ͼƬimgView��list��ȥ
		 list_imgview=new ArrayList<ImageView>();
//		 ImageView iv=new ImageView(context);
//		 iv.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		 for(String url:urls){
			 ImageView iv=new ImageView(context);
			 iv.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		
			 
			 //����imgUrlAry�е�ͼƬ��iv(ImageView)��
			 ImageListener imageListener = ImageLoader.getImageListener(iv,
						R.drawable.ic_launcher, android.R.drawable.ic_delete);
			int maxHeight = context.getResources().getDimensionPixelOffset(
						R.dimen.view_pager_height);
			int maxWidth=MyApplication.screenWidth;
			
			//ʹ viewpager��������
			imageLoader.get(url, imageListener,maxWidth,maxHeight,ScaleType.FIT_XY);
				
				
//			 iv.setImageResource(R.drawable.ic_launcher);
			 list_imgview.add(iv);
		 }
	 }
	 
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return urls.length;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}
	/**
	 * ����itemʱ���õķ���
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
	
		ImageView imgv=list_imgview.get(position);
	/**
	 * 		��ʼ���� IllegalStateException: The specified child already has a parent����
	 * ����Ϊ��addImgs()�н� ImageView iv=new ImageView(context);д����for���棬
	 * ����list_imgv��ֻ��һ��parent�����ɸ�imgv
	 * 
	 * 
		ViewGroup parent = (ViewGroup) imgv.getParent();
		 if (parent != null) { 
		parent.removeAllViews();
		 } 
	 */
		container.addView(imgv);
		return list_imgview.get(position);
	}
	
	//����itemʱ���õķ���
	//viewpageһ�㶼�Ỻ��3��item����һ��ʼ�ͻ����3��instantiateItem, �����һ�����
	//����3ҳʱ����1ҳ��item�ᱻ���õ�destroyitem�������٣��������ִ���
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(list_imgview.get(position));

	}
	
	//����Ҫ��ʾ��ͼƬ���鴫������
	public void setImageUrls(String[] imgUrlAry){
		this.imgUrlAry=imgUrlAry;
	}
}//MyPagerAdapter_cls
