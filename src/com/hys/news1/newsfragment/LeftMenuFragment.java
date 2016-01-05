  package com.hys.news1.newsfragment;

import com.hys.news1.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LeftMenuFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		String[] menu_names=getActivity().getResources().getStringArray(R.array.menu_names);
		LinearLayout ll=(LinearLayout) LinearLayout.inflate(getActivity(), R.layout.layout_left_menu, null);

		
		
		for(int i=0,j=0;i<ll.getChildCount();i=i+2,j++){
			LinearLayout child=(LinearLayout) ll.getChildAt(i);
			TextView tv_title=(TextView) child.findViewById(R.id.tv_title);
			tv_title.setText(menu_names[j]);
		}
		
		return ll;
	
	}//onCreateView
}//Fragment_cls
