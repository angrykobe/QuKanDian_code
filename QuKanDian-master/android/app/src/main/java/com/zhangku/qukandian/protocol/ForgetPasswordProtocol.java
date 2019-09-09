package com.zhangku.qukandian.protocol;

import android.app.Activity;
import android.util.Log;

import com.zhangku.qukandian.bean.PasswordBean;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.manager.DBTools;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yuzuoning on 2017/3/25.
 */

public class ForgetPasswordProtocol extends BaseModel {
    private String mUserName;
    private String mPwd;
    private String mVerificationCode;
    private Activity mActivity;

    public ForgetPasswordProtocol(Activity context, String username, String pwd, String verification, OnResultListener<Boolean> onResultListener) {
        super(context, onResultListener);
        mActivity = context;
        mUserName = username;
        mPwd = pwd;
        mVerificationCode = verification;
    }

    public void getRusult() {
        call = getAPIService().updatePwd(mAuthorization, mContentType, new PasswordBean(mVerificationCode, mUserName, mPwd));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (null != response.body()) {
                        JSONObject object = new JSONObject(response.body());
                        if (object.optBoolean("success")) {
                            ToastUtils.showShortToast(getContext(), object.optString(mMessage));
                            new GetTokenProtocol(mContext, new OnResultListener<Boolean>() {
                                @Override
                                public void onResultListener(Boolean response) {
                                    new GetNewUserInfoProtocol(mContext, new BaseModel.OnResultListener<UserBean>() {
                                        @Override
                                        public void onResultListener(UserBean response) {
                                            if (null != onResultListener) {
                                                onResultListener.onResultListener(true);
                                            }
                                            UserManager.getInst().updateLoginStatus(true);
                                        }

                                        @Override
                                        public void onFailureListener(int code, String error) {

                                        }
                                    }).postRequest();
                                }

                                @Override
                                public void onFailureListener(int code, String error) {

                                }
                            }).getUserToken(mUserName, mPwd);
                        } else {
                            ToastUtils.showShortToast(getContext(), object.optJSONObject(mError).optString(mMessage));
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("TAG", "onFailure:" + new Exception(t));
            }
        });
    }

    @Override
    protected void release() {
        call.cancel();
    }
}
