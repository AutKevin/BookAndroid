package com.autumn.tools; 
 
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.autumn.pojo.MsgPojo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
/**
 * json转换工具
 * @param <T> 实体类型
 */


public class JsonUtil<T> {

	
	private Gson gson=new Gson();
	
	/**
	 * 实体转json字符�???
	 * @param t
	 * @return
	 */
	public  String objectToJSON(T t){
		return this.gson.toJson(t);
	}
	
	/**
	 * 实体集合转json字符�???
	 * @param objs
	 * @return
	 */
	public String objectsToJSON(List<T> objs){
		return this.gson.toJson(objs);
	}
	
	
	/**
	 * json字符串转实体
	 * @param cla 实体原型
	 * {"name":"lidebao","age":32}
	 */
	public  T jsonToObject(String json,Class<T> cla){
		T t=(T)this.gson.fromJson(json, cla);
		return  t;
	}
	
	
	
	/**
	 * json数组[{"age":32}]
	 * @param json
	 * @return
	 */
	public  List<T> jsonToObjects(String json){
		Type listType=new TypeToken<LinkedList<T>>(){}.getType();
		LinkedList<T> objs=this.gson.fromJson(json, listType);
		return objs;
	}
	
	
	
	public List<Map<String,String>> jsonArrToMap(String jsonArr){
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		try{ 
			JsonReader reader = new JsonReader(new StringReader(jsonArr)); 
			reader.beginArray(); 
			while(reader.hasNext()){
				reader.beginObject();
				Map<String,String> map=new HashMap<String,String>();
				while(reader.hasNext()){
					String tagName = reader.nextName();
					String val=reader.nextString();
					map.put(tagName, val);
				}
				list.add(map);
				reader.endObject();
			}
			reader.endArray();
	    }catch(Exception e){
		    e.printStackTrace();
		}
        return list;
	}
	
	public static void main(String[] args) {
		MsgPojo msgPojo = new MsgPojo();
		msgPojo.setUserName("autumn");
		msgPojo.setAppName("支付宝");
		msgPojo.setTitle("提示");
		msgPojo.setContext("内容");
		Gson gson = new Gson();
		String resuString = gson.toJson(msgPojo);
		System.out.println(resuString);
	}
}