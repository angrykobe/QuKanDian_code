package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;

/**
 * Created by  on 2017/4/1.
 * 获取不喜欢文章的理由
 */
public class DisLikeReasonProtocol extends BaseProtocol {
    public DisLikeReasonProtocol(Context context, OnResultListener<List<String>> onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getDislikeReason("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json");
//        call = getAPIService().getDislikeReason();
        return call;
    }

    @Override
    protected void getResult(JSONObject object) {
        if (object.optBoolean("success")) {
            Gson gson = new Gson();
            List<String> list = gson.fromJson(object.optJSONArray("result").toString(), new TypeToken<List<String>>() {}.getType());
            if (null != onResultListener && null != list) {
                onResultListener.onResultListener(list);
            }
        }
    }

    @Override
    public void release() {
        call.cancel();
    }
}
