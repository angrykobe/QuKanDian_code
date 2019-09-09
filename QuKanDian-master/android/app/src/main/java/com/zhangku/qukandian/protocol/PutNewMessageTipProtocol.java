package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.observer.ClickTipObserver;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/4/19.
 */
public class PutNewMessageTipProtocol extends NewBaseProtocol<Object> {

    private int mType;
    public PutNewMessageTipProtocol(Context context, int type, OnResultListener<Object> onResultListener) {
        super(context, onResultListener);
        mType = type;
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().putTips("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json",mType);
        return call;
    }

    @Override
    protected void getResult(Object o) {
        ClickTipObserver.getInstance().notifyUpdate(mType, 0);
        super.getResult(o);
    }
}
