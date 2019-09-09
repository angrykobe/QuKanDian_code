package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.IncomeDetialsBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONObject;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/4/13.
 */

public class GetIncomeDetailsTopDataProtocol extends BaseProtocol {
    public GetIncomeDetailsTopDataProtocol(Context context, OnResultListener<IncomeDetialsBean> onResultListener) {
        super(context, onResultListener);
    }
    @Override
    protected Call getMyCall() {
        call = getAPIService().getIncomeDetailsTopData("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json");
        return call;
    }

    @Override
    protected void getResult(JSONObject object) {
        if (object.optBoolean(mSuccess)) {
            Gson gson = new Gson();
            IncomeDetialsBean bean = null;
            if (null != object.optJSONObject(mResult)) {
                JSONObject object1 = object.optJSONObject(mResult);
                if(null == object1){
                    return;
                }
                bean = gson.fromJson(object1.toString(), IncomeDetialsBean.class);
                if (null != onResultListener) {
                    if(null != bean){
                        onResultListener.onResultListener(bean);
                    }
                }
            }
        }
    }
}
