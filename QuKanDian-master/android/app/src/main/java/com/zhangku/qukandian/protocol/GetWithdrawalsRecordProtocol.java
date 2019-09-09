package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.WithdrawalsRecordBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/4/14.
 */

public class GetWithdrawalsRecordProtocol extends BaseProtocol {
    private int mPage;
    public GetWithdrawalsRecordProtocol(Context context,int page, OnResultListener<ArrayList<WithdrawalsRecordBean>> onResultListener) {
        super(context, onResultListener);
        mPage = page;
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getWithdrawalsRecord("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json",mPage, 200);
        return call;
    }

    @Override
    protected void getResult(JSONObject object) {
        if (object.optBoolean(mSuccess)) {
            JSONArray array = object.optJSONArray(mResult);
            ArrayList<WithdrawalsRecordBean> beans = new ArrayList<WithdrawalsRecordBean>();
            if (null != array && array.length() > 0) {
                Gson gson = new Gson();
                for (int i = 0; i < array.length(); i++) {
                    WithdrawalsRecordBean bean = gson.fromJson(array.optJSONObject(i).toString(), WithdrawalsRecordBean.class);
                    beans.add(bean);
                }
            }
            if (null != onResultListener) {
                onResultListener.onResultListener(beans);
            }
        }
    }
}
