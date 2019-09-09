package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.bean.TuanInfoBean;
import com.zhangku.qukandian.network.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yuzuoning on 2018/3/15.
 */

public class GetTuanInfoProtocol extends BaseModel {
    public GetTuanInfoProtocol(Context context, OnResultListener<TuanInfoBean> onResultListener) {
        super(context, onResultListener);
    }

    public void getInfo(String tid){
        call = getAPIService().getTuanInfo(tid);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(null != response.body()){
                    try {
                        JSONObject object = new JSONObject(response.body());
                        if(object.optBoolean(mSuccess)){
                            Gson gson = new Gson();
                            TuanInfoBean bean = gson.fromJson(object.optJSONObject(mResult).toString(),TuanInfoBean.class);
                            if(null != onResultListener){
                                onResultListener.onResultListener(bean);
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

    }
}
