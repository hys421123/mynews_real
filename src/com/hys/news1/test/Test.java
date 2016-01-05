package com.hys.news1.test;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.test.AndroidTestCase;
import android.util.Log;

public class Test extends AndroidTestCase {
	
	
/*
	public void test1(){		
		String str="{"
				+ "\"name\":\"xiaomin\","
				+ "\"age\":18,"
				+ " \"school\":\"华科\","
				+ "\"gender\":true}";
		try {
			JSONObject obj=new JSONObject(str);
			
			Log.d("hys", obj.getString("name"));
			Log.d("hys", obj.getInt("age")+"" );
			Log.d("hys", obj.getString("school"));
			Log.d("hys", obj.getBoolean("gender")+"");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}//test1
	
	*/
	public void test2(){
		String str="{ \"name\": \"xiaomin\",\"age\": 18,"
				+ "\"wife\": [ \"大乔\", \"小乔\",\"貂蝉\"],"
				+ "\"father\": {\"name\": \"五哥\",\"age\": 50} }";
		Bean b= new Gson().fromJson(str, Bean.class);
		Log.d("hys", b.name+"\\"+b.age);
		Log.d("hys", b.wife[0]);
		Log.d("hys", b.father.name+"//"+b.father.age);
	}//test2
	
	
	class Bean{
		private String name;
		private int age;
		private String[] wife;
		private Father father;
	}
	class Father{
		
		private String name;
		private String age;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAge() {
			return age;
		}
		public void setAge(String age) {
			this.age = age;
		}
		
	}
	
	
}//Test_cls
