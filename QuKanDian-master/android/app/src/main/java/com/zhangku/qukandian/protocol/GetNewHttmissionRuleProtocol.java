package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.LaoBean;

import org.json.JSONObject;

import retrofit2.Call;

public class GetNewHttmissionRuleProtocol extends NewBaseProtocol<LaoBean> {
    private int mHttType;

    public GetNewHttmissionRuleProtocol(Context context, int mHttType,OnResultListener<LaoBean> onResultListener) {
        super(context, onResultListener);
        this.mHttType =mHttType;
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().getHttmissionRule(mAuthorization,mContentType,mHttType);
    }

}
