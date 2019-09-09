package com.zhangku.qukandian.protocol;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.zhangku.qukandian.application.QuKanDianApplication;
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

public class LoginDynamicProtocol extends BaseModel {
    private String mTel;
    private String mCode;
    private Context mContext;

    public LoginDynamicProtocol(Context context, String tel, String code, OnResultListener<Integer> onResultListener) {
        super(context, onResultListener);
        mContext = context;
        mTel = tel;
        mCode = code;
    }

    public void getRusult() {
        String version = "App/android Qukandian/" + QuKanDianApplication.getCode();
        long invitation_code = UserSharedPreferences.getInstance().getLong(Constants.INVITATION_CODE, 0);
        String regSource = !TextUtils.isEmpty(UserSharedPreferences.getInstance().getString(Constants.INVITATION_SOURCE, ""))?
                UserSharedPreferences.getInstance().getString(Constants.INVITATION_SOURCE, "") : QuKanDianApplication.mUmen;
        call = getAPIService().loginDynamic(mAuthorization,
                mContentType,
                CommonHelper.md5(key + "code" + mCode + "inviterId" + invitation_code + "nonceStr" + rand + "regSource" + regSource + "tel" + mTel + "timestamp" + time),
                time, appid, rand,
                version,
                mTel, mCode, regSource, invitation_code);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject object = new JSONObject(response.body());
                    int userId = 0;
                    if (object.optBoolean("success")) {
                        userId = object.optJSONObject(mResult).optInt("id");
                        ToastUtils.showLongToast(mContext, object.optString(mMessage));
                    } else {
                        ToastUtils.showLongToast(mContext, object.optJSONObject(mError).optString(mMessage));
                    }
                    if (null != onResultListener) {
                        onResultListener.onResultListener(userId);
                    }
                } catch (Exception e) {
                    if (null != onResultListener)
                        onResultListener.onFailureListener(1, "网络异常，请重试");
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
