package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.UpdateBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yuzuoning on 2017/4/27.
 */

public class UpdateProtocol extends BaseModel {
    public UpdateProtocol(Context context, OnResultListener<ArrayList<UpdateBean>> onResultListener) {
        super(context, onResultListener);
    }

    public void checkUpdate() {
        call = getAPIService().checkUpdate("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json",1,"qksj",""+QuKanDianApplication.getCode());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (null != response.body()) {
                    try {
                        JSONObject object = new JSONObject(response.body());
                        if (object.optBoolean(mSuccess)) {
                            Gson gson = new Gson();
                            JSONArray array = object.optJSONArray(mResult);
                            ArrayList<UpdateBean> list = new ArrayList<UpdateBean>();
                            if (null != array && array.length() > 0) {
                                for (int i = 0; i < array.length(); i++) {
                                    UpdateBean updateBean = gson.fromJson(array.optJSONObject(i).toString(), UpdateBean.class);
                                    list.add(updateBean);
                                }
                            }
                            if (null != onResultListener) {
                                onResultListener.onResultListener(list);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    @Override
    protected void release() {
        call.cancel();
    }
}
