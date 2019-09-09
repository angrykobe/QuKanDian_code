package com.zhangku.qukandian.utils.SharedPreferencesUtil;

import com.google.gson.Gson;
import com.zhangku.qukandian.utils.GsonUtil;

import java.util.List;

public class JsonUtil {

	public static String toJson(Object object) {

		return new Gson().toJson(object);
	}

	public static <T> List<T> getList(String jsonString, Class<T> cls) {
		return GsonUtil.fromJson2List(jsonString, cls);
	}

	/**
	 * Json转对象
	 * 
	 * @param result
	 * @param cls
	 * @return
	 */
	public static <T> T parseObject(String result, Class<T> cls) {
		return GsonUtil.fromJson2(result,cls);
	}
}
