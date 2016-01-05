package com.hys.news1.newsfragment;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.hys.news1.HttpUrl;
import com.hys.news1.MyApplication;
import com.hys.news1.R;
import com.hys.news1.adapter.NewsTabAdapter;
import com.hys.news1.bean.JsonNewsBean;
import com.hys.news1.bean.JsonNewsChildrenBean;
import com.hys.news1.newsfragment.ViewpagerFrm1;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.viewpagerindicator.TabPageIndicator;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

//右边整体的fragment  ????????包括各项Tab的确定
public class RightContentFragment extends Fragment implements OnClickListener{
	private ViewPager viewPager ;  //tab的viewpager
	private TabPageIndicator tabIndicator;
	private ImageView left_menu_spread;
	
	private RequestQueue mQueue;
	private JsonNewsBean jsNewsBean;
	private Gson gson=new Gson();
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	
		
		LinearLayout l=(LinearLayout) LinearLayout.inflate(getActivity(), R.layout.layout_right_content, null);
		viewPager = (ViewPager)l.findViewById(R.id.viewPager);
		left_menu_spread=(ImageView) l.findViewById(R.id.left_menu_spread);
		 tabIndicator = (TabPageIndicator)l.findViewById(R.id.tabIndicator);
		
		 left_menu_spread.setOnClickListener(this);
		 //注意与直接定义static 方法的区别
		 mQueue=((MyApplication) getActivity().getApplication()).getRequestQueue();
		initIndicator();
		
		return l;
	}//onCreateView
	
	private void initIndicator(){
		//准备listener   和 errorlistener
		Listener<JSONObject> listener=new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject jsonObject) {
				// jsonObject.toString 是全部的json字符串，需要我们自己去解析 
				//当然jsonObject是 JSONObject类型  表示的是整个JSON对象
		//		Log.i("hys", jsonObject.toString());
				
				
//				Log.i("hys", jsonObject.toString());
				
				try {
					JSONArray dataJsArray= jsonObject.getJSONArray("data");
					//注：  data 下面的标号[0]  是 新的jsonObject而不是jsonArray,
					//因此，应该getJSONObject而不是getJSONArry
					String newsJsStr=dataJsArray.getJSONObject(0).toString();
					jsNewsBean=gson.fromJson(newsJsStr, JsonNewsBean.class);
					
					JsonNewsChildrenBean[] jsChildBean=  jsNewsBean.getChildren();
					
					
					//设置 Tab各项 分类名称
					 FragmentPagerAdapter adapter = new NewsTabAdapter(getFragmentManager(),jsChildBean);
					 
					 
				     viewPager.setAdapter(adapter);	  	        
				     tabIndicator.setViewPager(viewPager);
				     tabIndicator.setVisibility(View.VISIBLE);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Log.e("hys", "error JSONException!");
					e.printStackTrace();
				}
	
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
		//(String url, Listener<JSONObject> listener, ErrorListener errorListener)
		JsonObjectRequest jsonObject=new JsonObjectRequest(HttpUrl.url, listener, errorListener);
		mQueue.add(jsonObject);		
		

	        
//	        if(jsNewsBean==null)
//	        	Log.i("hys", "null");
//	        else
//	        	Log.i("hys", jsNewsBean.getTitle());
	        
	}//initIndicator

	
	@Override
	public void onClick(View v) {//左侧菜单是否展开按钮
		// TODO Auto-generated method stub
		SlidingFragmentActivity slidingAct=(SlidingFragmentActivity) getActivity();
		boolean isSliding=slidingAct.getSlidingMenu().isMenuShowing();
		slidingAct.getSlidingMenu().showMenu(!isSliding);
	}//Spread按钮展开方法
}//ContentFrm_cls
