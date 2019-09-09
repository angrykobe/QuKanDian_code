package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.InitUserBean;

import org.json.JSONObject;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2018/1/11.
 */

public class AdsuserProtocol extends BaseProtocol<Boolean> {
    private InitUserBean mInitUserBean;
    public AdsuserProtocol(Context context,InitUserBean bean, OnResultListener<Boolean> onResultListener) {
        super(context, onResultListener);
        mInitUserBean = bean;
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().adsuser(mAuthorization,mContentType,mInitUserBean);
    }

    @Override
    protected void getResult(JSONObject object) {
        if(null != object){
            if(null != onResultListener){
                onResultListener.onResultListener(object.optBoolean(mSuccess));
            }
        }
    }
}
