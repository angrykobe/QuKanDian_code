package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONObject;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/7/3.
 */

public class PutBehaviorProtocol extends BaseProtocol {
    private int mUserId;
    private int mType;
    private int mTimes;
    public PutBehaviorProtocol(Context context,int userId,int type,int times, OnResultListener onResultListener) {
        super(context, onResultListener);
        mUserId = userId;
        mType = type;
        mTimes = times;
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().behavior("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json",mUserId,mType,mTimes);
        return call;
    }

    @Override
    protected void getResult(JSONObject object) {

    }
}
