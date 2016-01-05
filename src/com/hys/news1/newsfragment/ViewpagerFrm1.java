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
import android.support.v4.app.Fragment;//�����Ը��ã�ע���ѡ���
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
//  Сbug��  ��ҳʱ��tab��Ȼ���ڵڶ�ҳ��


//����Tab���viewpager,
//�ұߵĽ��沼��,
//����gson���ݵ�ʵ����������

public class ViewpagerFrm1 extends Fragment{

	private PullToRefreshListView mPullRefreshListView;
	private ListAdapter mAdapter;
	private View topView;
	private MyPagerAdapter myPagerAdapter;
	private NewsItemListViewAdapter	mNewsItemAdapter;
	
	
	//���������й�
	private RequestQueue mQueue;
	private ImageLoader imageLoader;
	private String newsUrl;
	private String jsChildUrl;
	//�����������ݣ����а���title,topnews,newsitem,more
	private NewsbjContentBean newsContentBean;
	private Gson gson;
	
	
	//������data�µķ�֧ news����
	private ContentNewsItemBean[] news;
	//������data�µķ�֧ topnews����
	private ContentTopNewsBean[] topnews;
	
	//right_topnews�ؼ�����
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
		//���õ���jsChild��Url���ݽ��������url�а��������NewsContent������
		this.jsChildUrl=jsChildUrl;
	}
	
	private void initPagerAdapter(){
		
		
		/**
		 * �������磬�õ�TopͼƬ  start
		 */
		
//		ImageListener listener=imageLoader.getImageListener(view, defaultImageResId, errorImageResId);
//		Log.i("hys", "initNewsTop��  ");
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
					
					//��ȡnews[]�������
					JSONArray jsAryNews= dataObj.getJSONArray("news");
					String newsAry=jsAryNews.toString();
					news=gson.fromJson(newsAry, ContentNewsItemBean[].class);	
					
					//��ȡtopnews�������
					JSONArray jsAryTopNews= dataObj.getJSONArray("topnews");
					String topNewsAry=jsAryTopNews.toString();
					topnews=gson.fromJson(topNewsAry, ContentTopNewsBean[].class);	
					rollviewpager.setTopNewsLength(topnews.length);
					
					String[] imgUrlAry=new String[topnews.length];
					//����topnews���������ͼƬ��ַ����
					for(int i=0;i<topnews.length;i++){
						imgUrlAry[i]=topnews[i].getTopimage();
					}
					
					ImageLoader imgLoader=((MyApplication) getActivity().getApplication()).getImageLoader();
					
					myPagerAdapter=new MyPagerAdapter(imgUrlAry, getActivity(),imgLoader);
					
					//һ�����д���Ӧ��д������Jason����֮��ֻ����������õ�֮�󣬲�����ʾͼƬ������
					initNewsTop();
					//��ʼ�������listview��������
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
				Toast.makeText(getActivity(), "��������ʧ��", 0).show();
			}
		};
		//Listener<JSONObject> listener, ErrorListener errorListener
		JsonObjectRequest jsrequest=new JsonObjectRequest(jsChildUrl, listener, errorListener);
		mQueue.add(jsrequest);
		
		/**
		 * �������磬�õ�TopͼƬ  end
		 */
		
	}//initPagerAdapter
	/**
	 * ��ʼ���·��� listView���news[]����
	 */
	private void initListView(){
		ListView listview=mPullRefreshListView.getRefreshableView();
		//Arrayת��ΪList
		List<ContentNewsItemBean> listnews = Arrays.asList(news);

		
		mNewsItemAdapter=new NewsItemListViewAdapter(listnews,imageLoader, getActivity());
			
		//ֻ����ʾ��mAdapter�󣬲���һ����ʾitemView������
		listview.setAdapter(mNewsItemAdapter);
	}
	/**
	 * ��ʼ���ұ����� ����ͷ��ͼƬ����������
	 */
	@SuppressWarnings("deprecation")
	private void initNewsTop(){
		//tv_topnewstitle.setText("woqunima");
	//	Log.i("hys", "initNewsTop��PageChange��1");
		String str1=topnews[0].getTitle();
		tv_topnewstitle.setText(str1);
		initDot(0);
	//	Log.i("hys", "��; "+str1);
		
//		text=tv_topnewstitle.getText().toString();
//		Log.i("hys", "initNewsTop��PageChange��2;  "+text);
//		rollviewpager=new RollViewPager(getActivity());
		rollviewpager.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
	
		
		//����ҳ��ı��¼�
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
	
		//��viewpager��ӵ��ؼ�viewpager_container��ȥ
		ViewGroup viewpager_container=(ViewGroup) topView.findViewById(R.id.viewpager_container);
		
		//��Ӧ��right_topnews.xml�е���˼�ǣ�  rollviewpager�������FrameLayout��ȥ��
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
	
	//��ʼ����ҳԲ��
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
	 *��ʼ����������listview,�����е�NewsItem����
	 */
	private void initPullRefresh(){
		mPullRefreshListView=new PullToRefreshListView(getActivity());
		mPullRefreshListView.setLayoutParams(new ViewGroup.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		mPullRefreshListView.setMode(Mode.BOTH);//������������
		
		//��ȡ��ʾ���ݵ�listview
		ListView listview=mPullRefreshListView.getRefreshableView();
		//Get the Wrapped Refreshable View
		
		//	Add a fixed view to appear at the top of the list. 
		listview.addHeaderView(topView);
		//��listview ��item���õ���¼�
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
	
	}//initPullRefresh()����
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
//		Log.i("hys", "ViewpagerFrm1__onDestroyView");
		super.onDestroyView();
		rollviewpager.closeTimer();
	}
	
}//Frm_cls
