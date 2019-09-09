package com.zhangku.qukandian.protocol;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.bean.WeChatBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.DBTools;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yuzuoning on 2017/9/15.
 */

public class LoginByWechatProtocol extends BaseModel {
    private String mOpenId;

    public LoginByWechatProtocol(Context context, String openId, OnResultListener<Object> onResultListener) {
        super(context, onResultListener);
        mOpenId = openId;
    }

    public void getResult() {
//        call = getAPIService().Getlogincaptcha("Bearer " + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),
//                "application/json",
//                CommonHelper.md5(key + "code" + mCode + "nonceStr" + rand + "tel" + mTel + "timestamp" + time),
//                time, appid, rand,
//                mTel,mCode
//                CommonHelper.md5(key + "data" + g + "nonceStr" + rand + "timestamp" + time),
        call = getAPIService().loginByWx(mAuthorization,
                mContentType,
                CommonHelper.md5(key + "nonceStr" + rand + "openId" + mOpenId+ "regSource" + QuKanDianApplication.mUmen+ "timestamp" + time),
                time, appid, rand,
                mOpenId, QuKanDianApplication.mUmen);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (null != response.body()) {
                    try {
                        JSONObject object = new JSONObject(response.body());
                        if (object.optBoolean(mSuccess)) {
                            UserSharedPreferences.getInstance()
                                    .putString(Constants.USERNAME, object.optJSONObject(mResult).optInt("id") + "")
                                    .putString(Constants.PWD, mOpenId);
                            new GetTokenProtocol(mContext, new BaseModel.OnResultListener<Boolean>() {
                                @Override
                                public void onResultListener(Boolean response) {
                                    new GetNewUserInfoProtocol(mContext, new BaseModel.OnResultListener<UserBean>() {
                                        @Override
                                        public void onResultListener(UserBean response) {
                                            UserManager.getInst().updateLoginStatus(true);
                                            if (null != onResultListener) {
                                                onResultListener.onResultListener("");
                                            }
                                        }

                                        @Override
                                        public void onFailureListener(int code, String error) {
                                            if (null != onResultListener) {
                                                onResultListener.onFailureListener(code, error);
                                            }
                                        }
                                    }).postRequest();
                                }

                                @Override
                                public void onFailureListener(int code, String error) {

                                }
                            }).getUserTokenWX(object.optJSONObject(mResult).optInt("id") + "", mOpenId);
                        } else {
                            if (object.optJSONObject(mError).optInt("code") == 403 ) {
                                if("请先与手机号进行绑定".equals(object.optJSONObject(mError).optString("message"))){
                                    ActivityUtils.startToBindPhoneActivity(mContext);
                                    ((Activity) mContext).finish();
                                }else{//message=一个手机不能多个账号登录哦，账号锁定中。
                                    ToastUtils.showLongToast(mContext,""+object.optJSONObject(mError).optString("message"));
//                                    if (null != onResultListener) {
//                                        onResultListener.onFailureListener(403, "一个手机不能多个账号登录哦，账号锁定中");
//                                    }
                                }
                            } else {
                                if (null != object.optJSONArray(mResult)) {
                                    JSONArray array = object.optJSONArray(mResult);
                                    ArrayList<WeChatBean> list = new ArrayList<>();
                                    Gson gson = new Gson();
                                    for (int i = 0; i < array.length(); i++) {
                                        WeChatBean bean = gson.fromJson(array.optJSONObject(i).toString(), WeChatBean.class);
                                        list.add(bean);
                                    }
                                    ActivityUtils.startToUpdateWechatBindActivity(mContext, list);
                                    ((Activity) mContext).finish();
                                }
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
