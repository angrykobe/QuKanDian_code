package com.zhangku.qukandian.protocol;

import android.content.Context;

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

public class VerificationCodeProtocol extends BaseModel {
    private Context mContext;
    private String mPhone;

    public VerificationCodeProtocol(Context context, String phone) {
        super(context);
        mContext = context;
        mPhone = phone;

    }

    public void getResult(final OnResultListener<Boolean> onResultListener) {
        call = getAPIService().getVerificationCode(mAuthorization, mContentType, mPhone);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (null != response.body()) {
                    try {
                        String message = "";
                        JSONObject object = new JSONObject(response.body());
                        if (object.optBoolean(mSuccess)) {
                            message = object.optString(mMessage);
                        } else {
                            message = object.optJSONObject(mError).optString(mMessage);
                        }
                        ToastUtils.showLongToast(mContext, message);
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
                if (null != onResultListener) {
                    onResultListener.onFailureListener(0, t.toString());
                }
            }
        });
    }

    @Override
    protected void release() {
        call.cancel();
    }
}
