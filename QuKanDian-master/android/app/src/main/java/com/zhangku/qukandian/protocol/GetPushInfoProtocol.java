package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.TodayNewBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/8/25.
 */

public class GetPushInfoProtocol extends BaseProtocol<ArrayList<TodayNewBean>> {

    public GetPushInfoProtocol(Context context, OnResultListener<ArrayList<TodayNewBean>> onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().getPushInfo("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU), "application/json");
    }

    @Override
    protected void getResult(JSONObject object) {
        JSONArray array = object.optJSONArray(mResult);
        ArrayList<TodayNewBean> list = new ArrayList<>();
        Gson gson = new Gson();
        for (int i = 0; i < array.length(); i++) {
            TodayNewBean bean = gson.fromJson(array.optJSONObject(i).toString(), TodayNewBean.class);
            list.add(bean);
        }
        if (null != onResultListener) {
            onResultListener.onResultListener(list);
        }
    }
}
