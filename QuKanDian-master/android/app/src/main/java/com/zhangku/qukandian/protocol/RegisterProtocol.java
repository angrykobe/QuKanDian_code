package com.zhangku.qukandian.protocol;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.RegisterBean;
import com.zhangku.qukandian.bean.WeChatBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogBindRemind;
import com.zhangku.qukandian.dialog.DialogWechatRemind;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.CommonUtil;
import com.zhangku.qukandian.utils.LogUtils;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yuzuoning on 2017/3/25.
 */

public class RegisterProtocol extends BaseModel {
    private String mUserName;
    private String mPwd;
    private String mVerificationCode;
    private String mOpenId;
    private Context mContext;
    private DialogWechatRemind mDialogWechatRemind;

    public RegisterProtocol(Context context, String username, String pwd, String verification,String openId) {
        super(context);
        mContext = context;
        mUserName = username;
        mPwd = pwd;
        mOpenId = openId;
        mVerificationCode = verification;
    }
    //注册
    public void getRusult(final OnResultListener<Boolean> onResultListener) {
        String version = "App/android Qukandian/" + QuKanDianApplication.getCode();
        RegisterBean registerBean = new RegisterBean(mVerificationCode, "", mUserName, mPwd, "");
        call = getAPIService().register(mAuthorization,
                mContentType,
                CommonHelper.md5(key + "data" + new Gson().toJson(registerBean) + "nonceStr" + rand + "openId" + mOpenId + "RegSource" + QuKanDianApplication.mUmen + "timestamp" + time),
                time, appid, rand,
                version,
                registerBean,
                QuKanDianApplication.mUmen,
                mOpenId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (null != response.body()) {
                        JSONObject object = new JSONObject(response.body());
                        if (object.optBoolean(mSuccess)) {
                            ToastUtils.showLongToast(getContext(), object.optString(mMessage));
                            UserSharedPreferences.getInstance()
                                    .putString(Constants.USERNAME, mUserName)
                                    .putString(Constants.PWD, mPwd);
                        } else {
                            new DialogBindRemind(mContext).shows(object.optJSONObject(mError).optString(mMessage));
                        }
                        if (null != onResultListener) {
                            onResultListener.onResultListener(object.optBoolean(mSuccess));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ToastUtils.showLongToast(getContext(), "error:" + new Exception(t));
            }
        });
    }

    //绑定手机
    public void getRusult2(final OnResultListener<Integer> onResultListener) {
        WeChatBean weChatBean = UserManager.getInst().getWeChatBean();
        weChatBean.setTel(mUserName);
        weChatBean.setCode(mVerificationCode);
        String regSource = !TextUtils.isEmpty(UserSharedPreferences.getInstance().getString(Constants.INVITATION_SOURCE, ""))?
                UserSharedPreferences.getInstance().getString(Constants.INVITATION_SOURCE, "") : QuKanDianApplication.mUmen;
        weChatBean.setRegSource(regSource);
        long invitation_code = UserSharedPreferences.getInstance().getLong(Constants.INVITATION_CODE, 0);
        weChatBean.setInviterId(invitation_code);
        String version = "App/android Qukandian/" + QuKanDianApplication.getCode();
        Call<String> call = getAPIService().register(mAuthorization,
                mContentType,
                CommonHelper.md5(key + "data" + new Gson().toJson(weChatBean) + "nonceStr" + rand + "timestamp" + time ),
                time, appid, rand,
                version,
                weChatBean);
//        Call<String> call = getAPIService().register("Bearer "
//                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),
//                "application/json", "App/android Qukandian/" + QuKanDianApplication.getCode(),
//                weChatBean, QuKanDianApplication.mUmen);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (null != response.body()) {
                        JSONObject object = new JSONObject(response.body());
                        int userId = 0;
                        if (object.optBoolean(mSuccess)) {
                            userId = object.optJSONObject(mResult).optInt("id");
                            mPwd = CommonUtil.encrypt(mUserName+"_"+userId);
                            ToastUtils.showLongToast(getContext(), object.optString(mMessage));
                            UserSharedPreferences.getInstance()
                                    .putString(Constants.USERNAME, mUserName)
                                    .putString(Constants.PWD, mPwd);
                        } else {
                            new DialogBindRemind(mContext).shows(object.optJSONObject(mError).optString(mMessage));
                        }
                        if (null != onResultListener) {
                            onResultListener.onResultListener(userId);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ToastUtils.showLongToast(getContext(), "error:" + new Exception(t));
            }
        });
    }

    @Override
    protected void release() {
        call.cancel();
    }
}
