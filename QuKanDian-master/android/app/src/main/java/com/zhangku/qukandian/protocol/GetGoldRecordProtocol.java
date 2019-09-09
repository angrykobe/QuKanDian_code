package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.GoldBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/4/13.
 */

public class GetGoldRecordProtocol extends BaseProtocol {
    private int mPage;
    private int mSize;

    public GetGoldRecordProtocol(Context context, int page, int size, OnResultListener<List<GoldBean>> onResultListener) {
        super(context, onResultListener);
        mPage = page;
        mSize = size;
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getGoldRecord("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU), "application/json", mPage, mSize);
        return call;
    }

    @Override
    protected void getResult(JSONObject object) {
        if (object.optBoolean(mSuccess)) {
            JSONArray array = object.optJSONArray(mResult);
            ArrayList<GoldBean> goldBeen = new ArrayList<GoldBean>();
            if (null != array && array.length() > 0) {
                Gson gson = new Gson();
                for (int i = 0; i < array.length(); i++) {
                    GoldBean bean = gson.fromJson(array.optJSONObject(i).toString(), GoldBean.class);
                    goldBeen.add(bean);
                }
            }
            if (null != onResultListener) {
                onResultListener.onResultListener(goldBeen);
            }
        }
    }

}
