package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.BannerBean;
import com.zhangku.qukandian.bean.MyBannerBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * 我要体现页面banner条配置
 */

public class GetNewWithdrawalsAdBannerProtocol extends NewBaseProtocol<MyBannerBean> {

    public GetNewWithdrawalsAdBannerProtocol(Context context, OnResultListener<MyBannerBean> onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().hebenpagebanner(mAuthorization, mContentType);
    }
}
