package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import retrofit2.Call;

/**
 * 创建者          xuzhida
 * 创建日期        2018/7/19
 * 你不注释一下？
 */
public class GetNewPeopleGoldProtocol extends NewBaseProtocol<Boolean> {

    public GetNewPeopleGoldProtocol(Context context, OnResultListener<Boolean> onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().getNewPeopleGold("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json");
    }
}
