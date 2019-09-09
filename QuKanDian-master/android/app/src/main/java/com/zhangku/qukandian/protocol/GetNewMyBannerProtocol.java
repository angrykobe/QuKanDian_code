package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.MyBannerBean;

import retrofit2.Call;

public class GetNewMyBannerProtocol extends NewBaseProtocol<MyBannerBean> {
    private static long last_time = 0;

    public GetNewMyBannerProtocol(Context context, OnResultListener<MyBannerBean> onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().mypagebanner(mAuthorization, mContentType, QuKanDianApplication.mUmen);
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