package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.WeChatBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yuzuoning on 2017/4/13.
 */

public class UploadWeChatInfoProtocol extends BaseProtocol {
    public String mErrorString = " ";
    public UploadWeChatInfoProtocol(Context context, OnResultListener<Boolean> onResultListener) {
        super(context, onResultListener);
    }

    public void uploadWeChatInfo(WeChatBean bean) {
        call = getAPIService().uploadWeChatInfo("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU), "application/json", bean);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (null != response.body()) {
                    JSONObject object = null;
                    try {
                        object = new JSONObject(response.body());
                        if (null != onResultListener) {
                            if (object.getBoolean(mSuccess)) {
                                onResultListener.onResultListener(true);
                            } else {
                                mErrorString = object.optJSONObject(mError).optString(mMessage);
                                onResultListener.onResultListener(false);
                                onResultListener.onFailureListener(0,mErrorString);
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
    protected Call getMyCall() {
        return null;
    }

    @Override
    protected void getResult(JSONObject object) {

    }

    @Override
    public void release() {
        call.cancel();
    }
}
