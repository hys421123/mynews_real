package com.hys.news1.adapter;

import java.util.List;
import java.util.zip.Inflater;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.hys.news1.MyApplication;
import com.hys.news1.R;
import com.hys.news1.bean.ContentNewsItemBean;
import com.hys.news1.bean.ListViewNewsItem;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsItemListViewAdapter extends BaseAdapter{

	private ImageLoader imgLoader;
	private List<ContentNewsItemBean> data;
	private Context context;
	public NewsItemListViewAdapter(List<ContentNewsItemBean> data,ImageLoader imgLoader,Context context){
		this.data=data;
		this.context=context;
		this.imgLoader=imgLoader;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v=null;
		HolderView holder=null;
		ContentNewsItemBean newsItemBean=data.get(position);
		if(convertView==null){
			 v=View.inflate(context, R.layout.item_listnews, null);
			 holder=new HolderView();
			 holder.iv_news_icon=(ImageView) v.findViewById(R.id.iv_news_icon);
			 holder.tv_time=(TextView) v.findViewById(R.id.tv_time);
			 holder.tv_title=(TextView) v.findViewById(R.id.tv_title);
			 v.setTag(holder);
		}else{
			v=convertView;
			holder=(HolderView) v.getTag();
			
		}
		 //通过imgLoader向iv_news_icon中添加数据
		if (newsItemBean.getListimage()!= null){
		ImageListener listener= imgLoader.getImageListener(holder.iv_news_icon, R.drawable.ic_launcher,android.R.drawable.ic_delete);
		imgLoader.get(newsItemBean.getListimage(), listener);
		}else{
			holder.iv_news_icon.setImageResource(R.drawable.ic_launcher);
		}
		//向tv_time,tv_title中添加数据
		holder.tv_time.setText(newsItemBean.getPubdate());
		holder.tv_title.setText(newsItemBean.getTitle());
		
		return v;
	}

	private class HolderView{
		private ImageView iv_news_icon;
		private TextView tv_title;
		private TextView tv_time;
	}
}
