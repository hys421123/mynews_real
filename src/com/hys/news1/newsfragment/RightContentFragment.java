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

//�ұ������fragment  ????????��������Tab��ȷ��
public class RightContentFragment extends Fragment implements OnClickListener{
	private ViewPager viewPager ;  //tab��viewpager
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
		 //ע����ֱ�Ӷ���static ����������
		 mQueue=((MyApplication) getActivity().getApplication()).getRequestQueue();
		initIndicator();
		
		return l;
	}//onCreateView
	
	private void initIndicator(){
		//׼��listener   �� errorlistener
		Listener<JSONObject> listener=new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject jsonObject) {
				// jsonObject.toString ��ȫ����json�ַ�������Ҫ�����Լ�ȥ���� 
				//��ȻjsonObject�� JSONObject����  ��ʾ��������JSON����
		//		Log.i("hys", jsonObject.toString());
				
				
//				Log.i("hys", jsonObject.toString());
				
				try {
					JSONArray dataJsArray= jsonObject.getJSONArray("data");
					//ע��  data ����ı��[0]  �� �µ�jsonObject������jsonArray,
					//��ˣ�Ӧ��getJSONObject������getJSONArry
					String newsJsStr=dataJsArray.getJSONObject(0).toString();
					jsNewsBean=gson.fromJson(newsJsStr, JsonNewsBean.class);
					
					JsonNewsChildrenBean[] jsChildBean=  jsNewsBean.getChildren();
					
					
					//���� Tab���� ��������
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
				Toast.makeText(getActivity(), "��������ʧ��", 0).show();
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
	public void onClick(View v) {//���˵��Ƿ�չ����ť
		// TODO Auto-generated method stub
		SlidingFragmentActivity slidingAct=(SlidingFragmentActivity) getActivity();
		boolean isSliding=slidingAct.getSlidingMenu().isMenuShowing();
		slidingAct.getSlidingMenu().showMenu(!isSliding);
	}//Spread��ťչ������
}//ContentFrm_cls
