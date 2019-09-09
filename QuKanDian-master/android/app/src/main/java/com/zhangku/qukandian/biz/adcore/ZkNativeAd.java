package com.zhangku.qukandian.biz.adcore;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;

import com.zhangku.qukandian.bean.AdLocationBeans;

import org.json.JSONObject;

public interface ZkNativeAd {

    void onAdClick(View view,AdLocationBeans.AdLocationsBean.ClientAdvertisesBean bean);

    void onAdClick(Activity activity, View view);

    void onAdShowed(View view);

    void onAdReadied(View view);

    void onAdClosed();

    int getActionType();

    int getAPPStatus();

    int getProgress();

    @Nullable
    String getAdSpaceId();

    @Nullable
    JSONObject getAPPInfo();
}
