package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.NoviceReadon;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/4/17.
 */

public class GetNoviceReadProtocol extends BaseProtocol {
    public GetNoviceReadProtocol(Context context, OnResultListener<ArrayList<NoviceReadon>> onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getReadMission("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json",25);
        return call;
    }

    @Override
    protected void getResult(JSONObject object) {
        ArrayList<NoviceReadon> mNoviceReadon = new ArrayList<NoviceReadon>();
        if (object.optBoolean(mSuccess)) {
            JSONArray array = object.optJSONArray(mResult);
            if (null != array && array.length() > 0) {
                Gson gson = new Gson();
                for (int i = 0; i < array.length(); i++) {
                    NoviceReadon readon = gson.fromJson(array.optJSONObject(i).toString(), NoviceReadon.class);
                    mNoviceReadon.add(readon);
                }
            }
            if (null != onResultListener) {
                onResultListener.onResultListener(mNoviceReadon);
            }
        }
    }
}
