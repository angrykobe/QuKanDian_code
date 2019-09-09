package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.NewBaseListProtocol;
import com.zhangku.qukandian.bean.DownAppBean;
import com.zhangku.qukandian.bean.UserMenuConfig;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import java.util.List;

import retrofit2.Call;

/**
 * 创建者          xuzhida
 * 创建日期        2018/8/27
 * 你不注释一下？
 */
public class GetMyMenuListProtocol extends NewBaseListProtocol<UserMenuConfig> {
    private static long last_time = 0;

    public GetMyMenuListProtocol(Context context, OnResultListener<List<UserMenuConfig>> onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getGrtList("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU), "application/json");
        return call;
    }

    @Override
    public void postRequest() {
        // 三秒防止刷
        //if (System.currentTimeMillis() - last_time < 3 * 1000) {
        //    if (onResultListener != null) {
        //        onResultListener.onFailureListener(-1100, "");
        //    }
        //    return;
        //}
        last_time = System.currentTimeMillis();
        super.postRequest();
    }
}
