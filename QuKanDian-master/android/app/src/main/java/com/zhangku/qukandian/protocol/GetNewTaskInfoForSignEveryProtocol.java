package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.EveryDaySignBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import retrofit2.Call;

/**
 */

public class GetNewTaskInfoForSignEveryProtocol extends NewBaseProtocol<EveryDaySignBean> {
    private String mName;
    public GetNewTaskInfoForSignEveryProtocol(Context context, String name, OnResultListener onResultListener) {
        super(context, onResultListener);
        mName = name;
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getTaskInfoByName("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json",mName);
        return call;
    }
}
