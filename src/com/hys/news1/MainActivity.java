package com.hys.news1;

import com.hys.news1.*;
import com.hys.news1.newsfragment.LeftMenuFragment;
import com.hys.news1.newsfragment.RightContentFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.os.Bundle;



public class MainActivity extends SlidingFragmentActivity{

@Override
public void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);

	setContentView(R.layout.content_frame);
	setBehindContentView(R.layout.menu_frame);
	
	// customize the SlidingMenu
	SlidingMenu sm = getSlidingMenu();
	//侧栏中图片大小，侧栏阴影宽度
	sm.setShadowWidthRes(R.dimen.shadow_width);
	sm.setShadowDrawable(R.drawable.shadow);
	//侧栏展开,主页面剩余宽度 
	sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
	sm.setFadeDegree(0.35f);
	//SlidingMenu.TOUCHMODE_FULLSCREEN表示屏幕的任何区域都可以滑动菜单
	//设置成margin  只能从边缘滑动左侧菜单，而在屏幕其他位置，滑动响应会传给viewpager
	sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

	
	// set the Above View

	getSupportFragmentManager()
	.beginTransaction()
	.replace(R.id.content_frame, new RightContentFragment())
	.commit();
	
	// set the Behind View

	getSupportFragmentManager()
	.beginTransaction()
	.replace(R.id.menu_frame, new LeftMenuFragment())
	.commit();
	
	// customize the SlidingMenu
	getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	
}//onCreate

}//MainAct_cls
