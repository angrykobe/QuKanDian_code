package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.NewBaseListProtocol;
import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.UserMenuConfig;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import java.util.List;

import retrofit2.Call;

/**
 * 创建者          xuzhida
 * 创建日期        2018/8/27
 * 获取昨日战报接口
 */
public class GetNewYesterdayGold extends NewBaseProtocol<Integer> {

    public GetNewYesterdayGold(Context context, OnResultListener<Integer> onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().GetYesterdayGold("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json");
        return call;
    }
}
