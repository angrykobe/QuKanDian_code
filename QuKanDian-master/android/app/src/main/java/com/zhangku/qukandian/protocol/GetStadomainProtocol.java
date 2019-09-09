package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.BaseProtocol;

import org.json.JSONObject;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2018/2/5.
 */

public class GetStadomainProtocol extends BaseProtocol<String> {
    public GetStadomainProtocol(Context context, OnResultListener<String> onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().getStadomain();
    }

    @Override
    protected void getResult(JSONObject object) {
        if(null == object){
            return;
        }
        if(object.optBoolean(mSuccess)){
            if(null != onResultListener){
                onResultListener.onResultListener(object.optString(mResult));
            }
        }
    }
}
