package com.hys.news1.adapter;

import com.hys.news1.bean.JsonNewsBean;
import com.hys.news1.bean.JsonNewsChildrenBean;
import com.hys.news1.newsfragment.ViewpagerFrm1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class NewsTabAdapter extends FragmentPagerAdapter {

	private JsonNewsChildrenBean[] jsChildBean;
	
	public NewsTabAdapter(FragmentManager fm,JsonNewsChildrenBean[] jsChildBean) {
		super(fm);
		this.jsChildBean=jsChildBean;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		//详细地描绘右边的界面
//		Log.i("hys", "NewsTabAdapter_cls_getItem");
		return new ViewpagerFrm1(jsChildBean[position].getUrl());
	}

    @Override
    public CharSequence getPageTitle(int position) {//Tab标题
        return jsChildBean[position].getTitle();
    }

	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return jsChildBean.length;
	}

}
