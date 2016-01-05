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
//Top图片翻播的 Adapter
	
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
	 private void addImgs(){//添加图片imgView到list中去
		 list_imgview=new ArrayList<ImageView>();
//		 ImageView iv=new ImageView(context);
//		 iv.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		 for(String url:urls){
			 ImageView iv=new ImageView(context);
			 iv.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		
			 
			 //加载imgUrlAry中的图片到iv(ImageView)中
			 ImageListener imageListener = ImageLoader.getImageListener(iv,
						R.drawable.ic_launcher, android.R.drawable.ic_delete);
			int maxHeight = context.getResources().getDimensionPixelOffset(
						R.dimen.view_pager_height);
			int maxWidth=MyApplication.screenWidth;
			
			//使 viewpager填满父体
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
	 * 创建item时调用的方法
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
	
		ImageView imgv=list_imgview.get(position);
	/**
	 * 		开始出现 IllegalStateException: The specified child already has a parent问题
	 * 是因为在addImgs()中将 ImageView iv=new ImageView(context);写在了for外面，
	 * 导致list_imgv中只有一个parent的若干个imgv
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
	
	//销毁item时调用的方法
	//viewpage一般都会缓冲3个item，即一开始就会调用3次instantiateItem, 当向右滑动，
	//到第3页时，第1页的item会被调用到destroyitem进行销毁，否则会出现错误。
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(list_imgview.get(position));

	}
	
	//将将要显示的图片数组传入其中
	public void setImageUrls(String[] imgUrlAry){
		this.imgUrlAry=imgUrlAry;
	}
}//MyPagerAdapter_cls
