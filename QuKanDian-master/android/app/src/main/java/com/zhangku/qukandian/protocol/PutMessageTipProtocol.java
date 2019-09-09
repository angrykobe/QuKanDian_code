package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONObject;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/4/19.
 */

public class PutMessageTipProtocol extends BaseProtocol {
    private int mType;
    public PutMessageTipProtocol(Context context,int type, OnResultListener<Boolean> onResultListener) {
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
    protected void getResult(JSONObject object) {
        if (object.optBoolean(mSuccess)) {
            if (null != onResultListener) {
                onResultListener.onResultListener(object.optBoolean(mSuccess));
            }
        }
    }
}
