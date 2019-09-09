package com.zhangku.qukandian.utils.SharedPreferencesUtil;

import android.app.Activity;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.BaseBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.GsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzuoning on 2017/5/7.
 */

public class UserSharedPreferences {
    private SharedPreferences mSharedPreferences;

    private UserSharedPreferences() {
        mSharedPreferences = QuKanDianApplication.getContext().getSharedPreferences(Constants.USER, Activity.MODE_PRIVATE);
    }

    private static UserSharedPreferences mUserSharedPreferences = null;

    public static UserSharedPreferences getInstance() {
        if (mUserSharedPreferences == null) {
            synchronized (UserSharedPreferences.class) {
                if (mUserSharedPreferences == null) {
                    mUserSharedPreferences = new UserSharedPreferences();
                }
            }
        }
        return mUserSharedPreferences;
    }

    public long getLong(String key) {
        return mSharedPreferences.getLong(key, 0);
    }

    public long getLong(String key, long values) {
        return mSharedPreferences.getLong(key, values);
    }

    public int getInt(String key) {
        return mSharedPreferences.getInt(key, 0);
    }

    public int getInt(String key, int val) {
        return mSharedPreferences.getInt(key, val);
    }

    public float getFloat(String key) {
        return mSharedPreferences.getFloat(key, 0);
    }

    public UserSharedPreferences putFloat(String key, float valuse) {
        mSharedPreferences.edit().putFloat(key, valuse).commit();
        return this;
    }

    public boolean getBoolean(String key, boolean valuse) {
        return mSharedPreferences.getBoolean(key, valuse);
    }

    public String getString(String key, String valuse) {
        return mSharedPreferences.getString(key, valuse);
    }

    public UserSharedPreferences putLong(String key, long valuse) {
        mSharedPreferences.edit().putLong(key, valuse).commit();
        return this;
    }

    public UserSharedPreferences putString(String key, String valuse) {
        mSharedPreferences.edit().putString(key, valuse).commit();
        return this;
    }

    public UserSharedPreferences putInt(String key, int valuse) {
        mSharedPreferences.edit().putInt(key, valuse).commit();
        return this;
    }

    public UserSharedPreferences putBoolean(String key, boolean valuse) {
        mSharedPreferences.edit().putBoolean(key, valuse).commit();
        return this;
    }

    public void clear() {
        mSharedPreferences.edit().clear().commit();
    }

    ///////////294///////////////294///////////////294///////////294///////////////
    //////// 小图标 天数 每天只保存一次
    public void saveHomeSmartDayCount() {
        String data = CommonHelper.formatTimeYMD(System.currentTimeMillis(), true);

        String string = getString("saveHomeSmartDayCount", "");
        List<String> list = GsonUtil.fromJsonForList(string, String.class);
        if(list==null) list = new ArrayList<>();
        if (!list.contains(data)) {
            list.add(data);
            putString("saveHomeSmartDayCount", new Gson().toJson(list));
        }
    }
    public int getHomeSmartDayCount() {
        String string = getString("saveHomeSmartDayCount", "");
        List<String> list = GsonUtil.fromJsonForList(string, String.class);
        return list == null ? 0 : list.size();
    }
    ////////任务页面红点 天数展示数
    public void saveTaskFragSpotEverydayCount(){
        String data = CommonHelper.formatTimeYMD(System.currentTimeMillis(), true);

        String string = getString("TaskFragSpotEverydayCount", "");
        List<String> list = GsonUtil.fromJsonForList(string, String.class);
        if(list==null) list = new ArrayList<>();
        if (!list.contains(data)) {
            list.add(data);
            putString("TaskFragSpotEverydayCount", new Gson().toJson(list));
        }
//        putInt("TaskFragSpotEverydayCount", count);
    }
    public int getTaskFragSpotDayCount(){
        String string = getString("TaskFragSpotEverydayCount", "");
        List<String> list = GsonUtil.fromJsonForList(string, String.class);
        return list == null ? 0 : list.size();
//        return getInt("TaskFragSpotEverydayCount", 0);
    }
    ////////我的页面红点 天数展示数
    public void saveMeFragSpotDayCount(){
        String data = CommonHelper.formatTimeYMD(System.currentTimeMillis(), true);

        String string = getString("MeFragSpotEverydayCount", "");
        List<String> list = GsonUtil.fromJsonForList(string, String.class);
        if(list==null) list = new ArrayList<>();
        if (!list.contains(data)) {
            list.add(data);
            putString("MeFragSpotEverydayCount", new Gson().toJson(list));
        }
    }
    public int getMeFragSpotDayCount(){
        String string = getString("MeFragSpotEverydayCount", "");
        List<String> list = GsonUtil.fromJsonForList(string, String.class);
        return list == null ? 0 : list.size();
    }
}
