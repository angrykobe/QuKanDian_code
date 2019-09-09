package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONObject;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/4/13.
 */

public class PutMentorHongbaoProtocol extends BaseProtocol {
    private int mentorId;
    public PutMentorHongbaoProtocol(Context context,int id, OnResultListener<Boolean> onResultListener) {
        super(context, onResultListener);
        mentorId = id;
    }

    @Override
    protected Call getMyCall() {
        int time = CommonHelper.getCheckTimeStamp(QuKanDianApplication.mTimeOffset);
        String rand = CommonHelper.getStringRandom(32);
        call = getAPIService().putMentorHonbao("Bearer "
                        + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),
                "application/json", CommonHelper.md5(key + "mentorId" + mentorId + "nonceStr"
                        + rand + "timestamp"
                        + time),
                time, appid, rand,
                mentorId);

//        call = getAPIService().putMentorHonbao("Bearer "
//                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json",mId);
        return call;
    }

    @Override
    protected void getResult(JSONObject object) {
        if (object.optBoolean(mSuccess)) {
            if (null != onResultListener) {
                onResultListener.onResultListener(true);
            }
        }
    }
}
