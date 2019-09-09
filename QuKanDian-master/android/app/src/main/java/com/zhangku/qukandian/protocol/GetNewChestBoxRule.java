package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.BoxRuleBean;

import org.json.JSONObject;

import retrofit2.Call;

public class GetNewChestBoxRule extends NewBaseProtocol<BoxRuleBean> {
    private static long last_time = 0;

    public GetNewChestBoxRule(Context context, OnResultListener<BoxRuleBean> onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().getChestBoxRule();
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
        //last_time = System.currentTimeMillis();
        super.postRequest();
    }
}
