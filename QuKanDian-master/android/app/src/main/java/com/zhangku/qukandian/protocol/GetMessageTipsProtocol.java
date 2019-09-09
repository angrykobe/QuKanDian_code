package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.MessageTipBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/4/19.
 */

public class GetMessageTipsProtocol extends BaseProtocol {
    private static long last_time = 0;

    public GetMessageTipsProtocol(Context context, OnResultListener<List<MessageTipBean>> onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getTips("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU), "application/json");
        return call;
    }

    @Override
    protected void getResult(JSONObject object) {
        if (object.optBoolean(mSuccess)) {
            Gson gson = new Gson();
            JSONArray array = object.optJSONArray(mResult);
            ArrayList<MessageTipBean> list = new ArrayList<>();
            if (null != array && array.length() > 0) {
                for (int i = 0; i < array.length(); i++) {
                    MessageTipBean messageTipBean = null;
                    try {
                        messageTipBean = gson.fromJson(array.getJSONObject(i).toString(), MessageTipBean.class);
                        list.add(messageTipBean);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (null != onResultListener) {
                onResultListener.onResultListener(list);
            }
        }
    }

    @Override
    public void postRequest() {
        // 三秒防止刷
        //if (System.currentTimeMillis() - last_time < 3 * 1000) {
        //    if (onResultListener != null) {
        //        onResultListener.onFailureListener(-1100, "");
        //    }
        //    return;
        //}
        last_time = System.currentTimeMillis();
        super.postRequest();
    }

}
