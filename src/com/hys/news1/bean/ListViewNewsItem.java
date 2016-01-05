package com.hys.news1.bean;

public class ListViewNewsItem {

	
	String icon;
	String title;
	String time;
	public ListViewNewsItem(String icon,String title,String time){
		this.icon=icon;
		this.title=title;
		this.time=time;
	}
	public ListViewNewsItem(){
		
	}
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	
	
}
