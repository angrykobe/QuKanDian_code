package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.HelpBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/4/19.
 */

public class GetEcoHelpProtocol extends BaseProtocol {
    public GetEcoHelpProtocol(Context context, OnResultListener<ArrayList<HelpBean>> onResultListener) {
        super(context, onResultListener);
    }
    @Override
    protected Call getMyCall() {
        call = getAPIService().getEcoHelp("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json");
        return call;
    }

    @Override
    protected void getResult(JSONObject object) {
        if (object.optBoolean(mSuccess)) {
            JSONArray array = object.optJSONArray(mResult);
            if (null != array && array.length() > 0) {
                Gson gson = new Gson();
                ArrayList<HelpBean> datas = new ArrayList<HelpBean>();
                for (int i = 0; i < array.length(); i++) {
                    HelpBean bean = gson.fromJson(array.optJSONObject(i).toString(), HelpBean.class);
                    datas.add(bean);
                }
                if (null != onResultListener) {
                    onResultListener.onResultListener(datas);
                }
            }
        }
    }
}
