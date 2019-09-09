package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.RankBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONObject;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/4/14.
 */

public class GetIncomeRankProtocol extends BaseProtocol {
    public GetIncomeRankProtocol(Context context, OnResultListener<RankBean> onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getIncomeRank("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json");
        return call;
    }

    @Override
    protected void getResult(JSONObject object) {
        if (object.optBoolean(mSuccess)) {
            Gson gson = new Gson();
            RankBean bean = gson.fromJson(object.optJSONObject(mResult).toString(), RankBean.class);
            if (null != onResultListener) {
                onResultListener.onResultListener(bean);
            }
        }
    }

}
