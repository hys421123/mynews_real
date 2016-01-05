package com.hys.news1.bean;

public class JsonNewsBean {
	private int id;	
	private String title;
	private JsonNewsChildrenBean[] children;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public JsonNewsChildrenBean[] getChildren() {
		return children;
	}
	public void setChildren(JsonNewsChildrenBean[] children) {
		this.children = children;
	}
	
}
