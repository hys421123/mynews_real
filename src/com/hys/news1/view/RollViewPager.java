package com.hys.news1.view;

import java.util.Timer;
import java.util.TimerTask;

import com.hys.news1.adapter.MyPagerAdapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class RollViewPager extends ViewPager{

	private MyPagerAdapter mAdapter;
	private Context context;
	private int topNewsLength;
	private Timer timer;
	private int downX,downY;
	private MyPagerClickListener pagerClickListener;
	private long downTime;
	
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			int position=getCurrentItem();
			position++;
			if(position>=topNewsLength)
				position=0;
			setCurrentItem(position);
		};
	};
	
	public RollViewPager(Context context) {
		this(context,null);
		timer=new Timer();
		// TODO Auto-generated constructor stub
		
	}
	public void setTopNewsLength(int l){
		this.topNewsLength=l;
	}
	public RollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		// TODO Auto-generated constructor stub
	}

	//给rollviewpager设置页面点击事件
	public void setOnPagerClickListener(MyPagerClickListener pagerClickListener){
		this.pagerClickListener=pagerClickListener;
	}
	public interface MyPagerClickListener{
		public void onPagerClick(int position);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			
			downTime = System.currentTimeMillis();
			downX = x;
			downY = y;
			
			// 通知其父控件，现在进行的是本控件的操作，不允许拦截
			getParent().requestDisallowInterceptTouchEvent(true);
			break;
		case MotionEvent.ACTION_MOVE:
			if (Math.abs(x - downX) < Math.abs(y - downY)) {// 竖滑
				// 通知其父控件，现在进行的是本控件的操作，允许拦截
				//即交给pullfresh操作
				getParent().requestDisallowInterceptTouchEvent(false);
			} else {
				// 通知其父控件，现在进行的是本控件的操作，不允许拦截
				getParent().requestDisallowInterceptTouchEvent(true);
			}
//			// 通知其父控件，现在进行的是本控件的操作，不允许拦截
//			getParent().requestDisallowInterceptTouchEvent(true);
			break;
		case MotionEvent.ACTION_UP:
			long upTime=System.currentTimeMillis();
			long timePeriod=upTime-downTime;
			if(x==downX&&y==downY&&timePeriod<500){
				pagerClickListener.onPagerClick(getCurrentItem());
			}
			break;
		}
		return super.onTouchEvent(event);
	}
	
	public void openTimer(){
		TimerTask task=new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(0);
			}
		};
		timer.schedule(task, 0, 4000);
	}//openTimer

	public void closeTimer(){
		timer.cancel();
	}
}//RollViewPager_cls
