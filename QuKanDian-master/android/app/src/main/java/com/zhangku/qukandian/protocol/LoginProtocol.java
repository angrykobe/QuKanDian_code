package com.zhangku.qukandian.protocol;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.RegisterBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;
import com.zhangku.qukandian.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yuzuoning on 2017/3/24.
 */

public class LoginProtocol extends BaseModel {
    private String mUserName;
    private String mPwd;
    private Context mContext;

    public LoginProtocol(Context context, String username, String pwd, OnResultListener<Boolean> onResultListener) {
        super(context, onResultListener);
        mContext = context;
        mUserName = username;
        mPwd = pwd;
    }

    public void getRusult() {
//        call = getAPIService().login(mAuthorization,mContentType,"App/android Qukandian/" + QuKanDianApplication.getCode(),new RegisterBean("","",mUserName,mPwd,""));
        String version = "App/android Qukandian/" + QuKanDianApplication.getCode();
        RegisterBean registerBean = new RegisterBean("", "", mUserName, mPwd, "");
        call = getAPIService().login(mAuthorization,
                mContentType,
                CommonHelper.md5(key + "data" + new Gson().toJson(registerBean) + "nonceStr" + rand + "timestamp" + time),
                time, appid, rand,
                version,
                registerBean);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (null != response.body()) {
                        JSONObject object = new JSONObject(response.body());
                        if (object.optBoolean("success")) {
                            ToastUtils.showLongToast(mContext, object.optString(mMessage));
                        } else {
                            ToastUtils.showLongToast(mContext, object.optJSONObject(mError).optString(mMessage));
                        }
                        if (null != onResultListener) {
                            onResultListener.onResultListener(object.getBoolean(mSuccess));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
