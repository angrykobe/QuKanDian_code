package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.ChestBoxBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONObject;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/8/29.
 */

public class GetNewChestBoxProtocol extends NewBaseProtocol<ChestBoxBean> {
    private static long last_time = 0;
    public GetNewChestBoxProtocol(Context context, OnResultListener<ChestBoxBean> onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().getChestBox("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU), "application/json");
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
