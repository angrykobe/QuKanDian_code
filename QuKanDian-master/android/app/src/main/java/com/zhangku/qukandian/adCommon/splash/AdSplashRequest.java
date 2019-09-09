package com.zhangku.qukandian.adCommon.splash;

import android.content.Context;
import android.view.ViewGroup;

import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.interfaces.OnAdSplashListener;

/**
 * Created by yuzuoning on 2017/10/17.
 */

public abstract class AdSplashRequest {
    protected Context mContext;
    public AdSplashRequest(Context context){
        mContext = context;
    }
    public abstract void getAdData(ViewGroup view, ViewGroup skipView, AdLocationBeans.AdLocationsBean.ClientAdvertisesBean name, OnAdSplashListener onAdSplashListener);
}
