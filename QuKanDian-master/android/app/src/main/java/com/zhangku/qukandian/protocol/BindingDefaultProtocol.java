package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.utils.ToastUtils;

import org.json.JSONObject;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/9/18.
 */

public class BindingDefaultProtocol extends BaseProtocol<Boolean> {
    private String mOpenId;
    private int mUserId;
    public BindingDefaultProtocol(Context context,String openId,int userId, OnResultListener<Boolean> onResultListener) {
        super(context, onResultListener);
        mOpenId = openId;
        mUserId = userId;
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().bindDefault(mAuthorization,mContentType,mOpenId,mUserId);
    }

    @Override
    protected void getResult(JSONObject object) {
        if(null != onResultListener){
            onResultListener.onResultListener(object.optBoolean(mSuccess));
            if(null != object.optString(mMessage)){
                ToastUtils.showShortToast(mContext,object.optString(mMessage));
            }
        }
    }
}
