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
	//������ͼƬ��С��������Ӱ���
	sm.setShadowWidthRes(R.dimen.shadow_width);
	sm.setShadowDrawable(R.drawable.shadow);
	//����չ��,��ҳ��ʣ���� 
	sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
	sm.setFadeDegree(0.35f);
	sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

	
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
