package com.hys.news1.newsfragment;


import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;

import com.hys.news1.MyApplication;
import com.hys.news1.NewsActivity;
import com.hys.news1.R;
import com.hys.news1.adapter.MyPagerAdapter;
import com.hys.news1.adapter.NewsItemListViewAdapter;
import com.hys.news1.bean.ContentNewsItemBean;
import com.hys.news1.bean.ContentTopNewsBean;
import com.hys.news1.bean.JsonNewsBean;
import com.hys.news1.bean.JsonNewsChildrenBean;
import com.hys.news1.bean.ListViewNewsItem;
import com.hys.news1.bean.NewsbjContentBean;
import com.hys.news1.view.RollViewPager;
import com.hys.news1.view.RollViewPager.MyPagerClickListener;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;//兼容性更好，注意别选错包
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//
//  小bug，  换页时，tab依然处在第二页中


//各个Tab间的viewpager,
//右边的界面布局,
//解析gson数据到实际新闻内容

public class ViewpagerFrm1 extends Fragment{

	private PullToRefreshListView mPullRefreshListView;
	private ListAdapter mAdapter;
	private View topView;
	private MyPagerAdapter myPagerAdapter;
	private NewsItemListViewAdapter	mNewsItemAdapter;
	
	
	//网络请求有关
	private RequestQueue mQueue;
	private ImageLoader imageLoader;
	private String newsUrl;
	private String jsChildUrl;
	//总体新闻内容，其中包括title,topnews,newsitem,more
	private NewsbjContentBean newsContentBean;
	private Gson gson;
	
	
	//北京总data下的分支 news数组
	private ContentNewsItemBean[] news;
	//北京总data下的分支 topnews数组
	private ContentTopNewsBean[] topnews;
	
	//right_topnews控件定义
	private TextView tv_topnewstitle;
	private LinearLayout ll_dot;
	private RollViewPager rollviewpager;
	
	private LinearLayout ll_topdown;
	
	private String text;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//(resource, root, attachToRoot)
		//topView=inflater.inflate(R.layout.right_topnews, container, false);
		topView=View.inflate(getActivity(), R.layout.right_topnews, null);
		tv_topnewstitle=(TextView) topView.findViewById(R.id.tv_topnewstitle);
		ll_dot=(LinearLayout) topView.findViewById(R.id.ll_dot);
		ll_topdown=(LinearLayout) topView.findViewById(R.id.ll_topdown);
		rollviewpager=new RollViewPager(getActivity());
		
	//	Log.i("hys", "ViewpagerFrm1_onCreateView");
		
		initPagerAdapter();
		initPullRefresh();
		
		return mPullRefreshListView;
	}//onCreateView
	
	
		
	
	

	public ViewpagerFrm1(String jsChildUrl){
		//将得到的jsChild的Url传递进来，这个url中包含具体的NewsContent的内容
		this.jsChildUrl=jsChildUrl;
	}
	
	private void initPagerAdapter(){
		
		
		/**
		 * 请求网络，得到Top图片  start
		 */
		
//		ImageListener listener=imageLoader.getImageListener(view, defaultImageResId, errorImageResId);
//		Log.i("hys", "initNewsTop中  ");
		mQueue=((MyApplication) getActivity().getApplication()).getRequestQueue();
		imageLoader=((MyApplication) getActivity().getApplication()).getImageLoader();
		gson=((MyApplication) getActivity().getApplication()).getGson();
		
		Listener<JSONObject> listener=new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject jsonObject) {
				// TODO Auto-generated method stub
				
				JSONObject dataObj;
				try {
					dataObj = jsonObject.getJSONObject("data");
					
					//抽取news[]数组出来
					JSONArray jsAryNews= dataObj.getJSONArray("news");
					String newsAry=jsAryNews.toString();
					news=gson.fromJson(newsAry, ContentNewsItemBean[].class);	
					
					//抽取topnews数组出来
					JSONArray jsAryTopNews= dataObj.getJSONArray("topnews");
					String topNewsAry=jsAryTopNews.toString();
					topnews=gson.fromJson(topNewsAry, ContentTopNewsBean[].class);	
					rollviewpager.setTopNewsLength(topnews.length);
					
					String[] imgUrlAry=new String[topnews.length];
					//遍历topnews数组里面的图片地址数据
					for(int i=0;i<topnews.length;i++){
						imgUrlAry[i]=topnews[i].getTopimage();
					}
					
					ImageLoader imgLoader=((MyApplication) getActivity().getApplication()).getImageLoader();
					
					myPagerAdapter=new MyPagerAdapter(imgUrlAry, getActivity(),imgLoader);
					
					//一下两行代码应该写在请求Jason队列之后，只有数据申请得到之后，才能显示图片等数据
					initNewsTop();
					//初始化下面的listview新闻内容
					initListView();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
											
//				jsNewsBean=gson.fromJson(newsJsStr, JsonNewsBean.class);			
//				JsonNewsChildrenBean[] jsChildBean=  jsNewsBean.getChildren();
			}
		};
		 ErrorListener errorListener=new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				Log.e("hys", arg0.getMessage());
				Toast.makeText(getActivity(), "访问网络失败", 0).show();
			}
		};
		//Listener<JSONObject> listener, ErrorListener errorListener
		JsonObjectRequest jsrequest=new JsonObjectRequest(jsChildUrl, listener, errorListener);
		mQueue.add(jsrequest);
		
		/**
		 * 请求网络，得到Top图片  end
		 */
		
	}//initPagerAdapter
	/**
	 * 初始化下方的 listView填充news[]数据
	 */
	private void initListView(){
		ListView listview=mPullRefreshListView.getRefreshableView();
		//Array转换为List
		List<ContentNewsItemBean> listnews = Arrays.asList(news);

		
		mNewsItemAdapter=new NewsItemListViewAdapter(listnews,imageLoader, getActivity());
			
		//只有显示了mAdapter后，才能一起显示itemView的内容
		listview.setAdapter(mNewsItemAdapter);
	}
	/**
	 * 初始化右边内容 新闻头部图片翻滚是新闻
	 */
	@SuppressWarnings("deprecation")
	private void initNewsTop(){
		//tv_topnewstitle.setText("woqunima");
	//	Log.i("hys", "initNewsTop中PageChange外1");
		String str1=topnews[0].getTitle();
		tv_topnewstitle.setText(str1);
		initDot(0);
	//	Log.i("hys", "外; "+str1);
		
//		text=tv_topnewstitle.getText().toString();
//		Log.i("hys", "initNewsTop中PageChange外2;  "+text);
//		rollviewpager=new RollViewPager(getActivity());
		rollviewpager.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
	
		
		//设置页面改变事件
		rollviewpager.addOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int pageNum) {
				// TODO Auto-generated method stub
	
				tv_topnewstitle.setText(topnews[pageNum].getTitle());
				initDot(pageNum);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});//addOnPageChangeListener
	
		//把viewpager添加到控件viewpager_container中去
		ViewGroup viewpager_container=(ViewGroup) topView.findViewById(R.id.viewpager_container);
		
		//反应在right_topnews.xml中的意思是，  rollviewpager被添加在FrameLayout中去了
		viewpager_container.addView(rollviewpager);
		rollviewpager.setAdapter(myPagerAdapter);
		rollviewpager.openTimer();
		rollviewpager.setOnPagerClickListener(new MyPagerClickListener() {
			
			@Override
			public void onPagerClick(int position) {
				// TODO Auto-generated method stub
				String url=topnews[position].getUrl();
				Intent intent=new Intent(getActivity(), NewsActivity.class);
				intent.putExtra("url", url);
				startActivity(intent);
				
			}
		});
	}//initNewsTop()
	
	//初始化换页圆点
	private void initDot(int position){

		ll_dot.removeAllViews();
		for(int i=0;i<topnews.length;i++){
			ImageView iv=new ImageView(getActivity());
			LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.setMargins(5, 0, 5, 0);
			iv.setLayoutParams(params);
			if (position == i) {
				iv.setImageResource(R.drawable.dot_focus);
			} else {
				iv.setImageResource(R.drawable.dot_normal);
			}
			ll_dot.addView(iv);	
		}
		
		
	}//initDot

	
	/**
	 * 
	 *初始化上下拉的listview,及其中的NewsItem内容
	 */
	private void initPullRefresh(){
		mPullRefreshListView=new PullToRefreshListView(getActivity());
		mPullRefreshListView.setLayoutParams(new ViewGroup.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		mPullRefreshListView.setMode(Mode.BOTH);//上下拉都可以
		
		//获取显示数据的listview
		ListView listview=mPullRefreshListView.getRefreshableView();
		//Get the Wrapped Refreshable View
		
		//	Add a fixed view to appear at the top of the list. 
		listview.addHeaderView(topView);
		//给listview 的item设置点击事件
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				position=position-2;
				ContentNewsItemBean bean= (ContentNewsItemBean) mNewsItemAdapter.getItem(position);
				String url=bean.getUrl();
				Intent intent=new Intent(getActivity(), NewsActivity.class);
				intent.putExtra("url", url);
				startActivity(intent);
			}
		});
	
	}//initPullRefresh()方法
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
//		Log.i("hys", "ViewpagerFrm1__onDestroyView");
		super.onDestroyView();
		rollviewpager.closeTimer();
	}
	
}//Frm_cls
