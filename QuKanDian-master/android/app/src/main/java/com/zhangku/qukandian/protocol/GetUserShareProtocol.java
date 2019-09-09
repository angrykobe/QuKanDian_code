package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONObject;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/8/30.
 */

public class GetUserShareProtocol extends BaseProtocol<String> {
    public GetUserShareProtocol(Context context, OnResultListener<String> onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().getShareMomentCoin("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU), "application/json");
    }

    @Override
    protected void getResult(JSONObject object) {
        if(null != onResultListener){
            onResultListener.onResultListener(object.optString(mResult));
        }
    }
}
