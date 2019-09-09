package com.zhangku.qukandian.utils.SharedPreferencesUtil;

import android.app.Activity;
import android.content.SharedPreferences;

import com.zhangku.qukandian.application.QuKanDianApplication;

/**
 * Created by yuzuoning on 2017/12/8.
 */

public class AdsRecordUtils {
    private SharedPreferences mSharedPreferences;

    private AdsRecordUtils() {
        mSharedPreferences = QuKanDianApplication.getAppContext().getSharedPreferences("ads_record", Activity.MODE_PRIVATE);
    }

    private static AdsRecordUtils mAdsRecordUtils = null;

    public static AdsRecordUtils getInstance() {
        if (null == mAdsRecordUtils) {
            synchronized (AdsRecordUtils.class) {
                if (null == mAdsRecordUtils) {
                    mAdsRecordUtils = new AdsRecordUtils();
                }
            }
        }
        return mAdsRecordUtils;
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

    public AdsRecordUtils putFloat(String key, float valuse) {
        mSharedPreferences.edit().putFloat(key, valuse).commit();
        return this;
    }

    public boolean getBoolean(String key, boolean valuse) {
        return mSharedPreferences.getBoolean(key, valuse);
    }

    public String getString(String key, String valuse) {
        return mSharedPreferences.getString(key, valuse);
    }

    public AdsRecordUtils putLong(String key, long valuse) {
        mSharedPreferences.edit().putLong(key, valuse).commit();
        return this;
    }

    public AdsRecordUtils putString(String key, String valuse) {
        mSharedPreferences.edit().putString(key, valuse).commit();
        return this;
    }

    public AdsRecordUtils putInt(String key, int valuse) {
        mSharedPreferences.edit().putInt(key, valuse).commit();
        return this;
    }

    public AdsRecordUtils putBoolean(String key, boolean valuse) {
        mSharedPreferences.edit().putBoolean(key, valuse).commit();
        return this;
    }


    public void clear() {
        mSharedPreferences.edit().clear().commit();
    }

    //////// 小图标每天次数
    public void saveHomeSmartEverydayCount(int count) {
        putInt("HomeSmartEverydayCount", count);
    }

    public int getHomeSmartEverydayCount() {
        return getInt("HomeSmartEverydayCount", 0);
    }

    ////////任务页面红点每天展示数
    public void saveTaskFragSpotEverydayCount(int count) {
        putInt("TaskFragSpotEverydayCount", count);
    }

    public int getTaskFragSpotEverydayCount() {
        return getInt("TaskFragSpotEverydayCount", 0);
    }

    ////////我的页面红点每天展示数
    public void saveMeFragSpotEverydayCount(int count) {
        putInt("MeFragSpotEverydayCount", count);
    }

    public int getMeFragSpotEverydayCount() {
        return getInt("MeFragSpotEverydayCount", 0);
    }

    ////////我的
    public void saveMeFragClickTime() {
        putLong("MeFragClickTime", System.currentTimeMillis());
    }

    public long getMeFragClickTime() {
        return getLong("MeFragClickTime", 0);
    }

    ////////
    public void saveMeFragClickNum() {
        putInt("MeFragClickNum", getMeFragClickNum() + 1);
    }

    public int getMeFragClickNum() {
        return getInt("MeFragClickNum", 0);
    }


}
