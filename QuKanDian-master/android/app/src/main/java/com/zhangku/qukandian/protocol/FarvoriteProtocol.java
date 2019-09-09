package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.InformationBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/4/3.
 */

public class FarvoriteProtocol extends BaseProtocol {
    private int mSize;
    private int mPage;
    public FarvoriteProtocol(Context context,int size,int page, OnResultListener<ArrayList<InformationBean>> onResultListener) {
        super(context, onResultListener);
        mSize = size;
        mPage = page;
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getFavoriteTogether("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json",mSize, mPage);
        return call;
    }

    @Override
    protected void getResult(JSONObject object) {
        if (object.optBoolean("success")) {
            ArrayList<InformationBean> datas = new ArrayList<InformationBean>();
            JSONArray array = object.optJSONArray("result");
            Gson gson = new Gson();
            for (int i = 0; i < array.length(); i++) {
                InformationBean bean = gson.fromJson(array.optJSONObject(i).toString(), InformationBean.class);
                bean.setPostId(bean.getId());
                datas.add(bean);
            }
            if (null != onResultListener) {
                onResultListener.onResultListener(datas);
            }

        }
    }
}
