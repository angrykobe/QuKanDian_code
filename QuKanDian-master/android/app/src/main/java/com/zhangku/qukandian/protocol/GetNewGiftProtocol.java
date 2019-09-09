package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.NewBaseListProtocol;
import com.zhangku.qukandian.bean.GiftBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import retrofit2.Call;

/**
 * xuzhida
 * 获取提现列表
 */

public class GetNewGiftProtocol extends NewBaseListProtocol<GiftBean> {
    private String payment;
    public GetNewGiftProtocol(Context context, String payment ,OnResultListener onResultListener) {
        super(context, onResultListener);
        this.payment = payment;
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getGift("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json",payment);
        return call;
    }
}
