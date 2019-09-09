package com.zhangku.qukandian.utils.SharedPreferencesUtil;

import android.content.Context;


import com.zhangku.qukandian.application.QuKanDianApplication;

import java.util.ArrayList;
import java.util.List;

public class CacheManage {

	private static final String NAME = "Cache";
	public static SimpleXmlAccessor accessor;
	
	public static SimpleXmlAccessor getAccessor(){
		if(accessor == null){
			accessor = new SimpleXmlAccessor(QuKanDianApplication.getmContext(), NAME, Context.MODE_APPEND);
		}
		return accessor;
	}
	
	public static <T> void put(String key, List<T> datalist){
		
		if(datalist == null || datalist.size() == 0){
			return;
		}
		List<T> list = new ArrayList<T>();
		list.addAll(datalist);
		if(list.size()>50){
			for(int i = list.size()-1;i>=50;i--){
				list.remove(i);
			}
		}
		String json = JsonUtil.toJson(list);
		if(json != null && !json.equals("")){
			getAccessor().remove(key);
		}
		getAccessor().putString(key, json);
	}
	
	public static <T> List<T> get(String key, Class<T> cls){
		List<T> list = null;
		String tmp = getAccessor().getString(key);
		if(tmp == null || tmp.equals("")){
			return new ArrayList<T>();
		}
		list = JsonUtil.getList(tmp, cls);
		if(list == null){
			return new ArrayList<T>();
		}
		return list;
	}
	
	public static void remove(String key){
		getAccessor().remove(key);
		
	}
	
	public static void removeAll(){
		getAccessor().removeAll();
	}
	
	

}
