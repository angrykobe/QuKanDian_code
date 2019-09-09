package com.zhangku.qukandian.protocol;

import android.app.Activity;
import android.content.Context;

import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogShowGold;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.observer.HongbaoShowObserver;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yuzuoning on 2017/4/13.
 */

public class PutMentorIdProtocol extends BaseModel implements DialogShowGold.OnClickConfirmListener {

    private OnClickPutListener mOnClickPutListener;
    private GetNewUserInfoProtocol mGetUserInfoProtocol;
    private DialogShowGold mDialogShowGold;

    public PutMentorIdProtocol(Context context, OnClickPutListener onClickPutListener, OnResultListener<Boolean> onResultListener) {
        super(context, onResultListener);
        mOnClickPutListener = onClickPutListener;
        mDialogShowGold = new DialogShowGold(context, this);
    }

    public void putMentorId(final long mentorId) {
        int time = CommonHelper.getCheckTimeStamp(QuKanDianApplication.mTimeOffset);
        String rand = CommonHelper.getStringRandom(32);
        call = getAPIService().putMentorId("Bearer "
                        + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),
                "application/json", CommonHelper.md5(key + "mentorId" + mentorId + "nonceStr"
                        + rand + "timestamp"
                        + time),
                time, appid, rand,
                mentorId);
//        call = getAPIService().putMentorId("Bearer "
//                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU), "application/json", mentorId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (null != response.body()) {
                    try {
                        JSONObject object = new JSONObject(response.body());
                        if (object.optBoolean(mSuccess)) {
                            if (null == mGetUserInfoProtocol) {
                                mGetUserInfoProtocol = new GetNewUserInfoProtocol(getContext(), new OnResultListener<UserBean>() {
                                    @Override
                                    public void onResultListener(UserBean response) {
                                        HongbaoShowObserver.getInstance().notifyUp(Constants.FIRST_ON_MENTEE);
                                        mDialogShowGold.show();
                                        mDialogShowGold.setCoin(Constants.FIRST_ON_MENTEE, "");
                                        mGetUserInfoProtocol = null;
                                    }

                                    @Override
                                    public void onFailureListener(int code, String error) {
                                        mGetUserInfoProtocol = null;
                                    }
                                });
                                mGetUserInfoProtocol.postRequest();
                            }

                        } else {
                            ToastUtils.showLongToast(getContext(), object.optJSONObject(mError).optString(mMessage));
                        }
                        if (null != mOnClickPutListener) {
                            mOnClickPutListener.onClickPutListener(object.optBoolean(mSuccess));
                        }
                        if (null != onResultListener) {
                            onResultListener.onResultListener(object.optBoolean(mSuccess));
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

    @Override
    public void onCancelListener() {

    }

    @Override
    public void onClickConfirmListener() {
        mDialogShowGold.dismiss();
        ((Activity) getContext()).finish();
    }

    public interface OnClickPutListener {
        void onClickPutListener(boolean success);
    }
}
