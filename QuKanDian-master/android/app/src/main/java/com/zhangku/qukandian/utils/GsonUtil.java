package com.zhangku.qukandian.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.BaseBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ikidou.reflect.TypeBuilder;
import ikidou.reflect.TypeToken;

/**
 * 创建者          xuzhida
 * 创建日期        2018/7/13
 * 你不注释一下？
 */
public class GsonUtil {
    public static <Z> BaseBean<List<Z>> fromJsonArray(String json, Class<Z> clazz) {
        Type type = TypeBuilder
                .newInstance(BaseBean.class)
                .beginSubType(List.class)
                .addTypeParam(clazz)
                .endSubType()
                .build();
        return new Gson().fromJson(json, type);
    }

    public static <Z> BaseBean<Z> fromJson(String json, Class<Z> clazz) {
        Type type = TypeBuilder
                .newInstance(BaseBean.class)
                .addTypeParam(clazz)
                .build();
        return new Gson().fromJson(json, type);
    }

    public static <Z> List<Z> fromJsonForList(String json, Class<Z> clazz) {
        Type type = TypeBuilder
                .newInstance(List.class)
                .beginSubType(clazz)
                .endSubType()
                .build();
        return new Gson().fromJson(json, type);
    }

    public static String toJson(Object o) {
        return new Gson().toJson(o);
    }

    public static <T> T fromJson2(String json, Class<T> clazz) {
        Gson gson = new Gson();
        T t = gson.fromJson(json, clazz);
        return t;
    }


    public static Map<String, JsonElement> fromJson2MapJson(String json) {
        Gson gson = new Gson();
        Map<String, JsonElement> map = gson.fromJson(json,
                new TypeToken<Map<String, JsonElement>>() {
                }.getType());
        return map;
    }

    public static <T> ArrayList<T> fromJson2List(String json, Class<T> clazz) {
        Gson gson = new Gson();
        ArrayList<JsonObject> list = null;
        list = gson.fromJson(json, new TypeToken<List<JsonObject>>() {
        }.getType());
        ArrayList<T> resultList = new ArrayList<T>();
        for (JsonObject t : list) {
            resultList.add(new Gson().fromJson(t, clazz));
        }
        return resultList;
    }


    static String temp = "{\"code\" : \"200\",\n" +
            "  \"message\" : \" \"}";
}
