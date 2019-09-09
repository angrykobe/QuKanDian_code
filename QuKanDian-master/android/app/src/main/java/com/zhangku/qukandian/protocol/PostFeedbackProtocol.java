package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.bean.FeedbackBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yuzuoning on 2017/4/19.
 */

public class PostFeedbackProtocol extends BaseModel {
    public PostFeedbackProtocol(Context context,OnResultListener<Boolean> onResultListener) {
        super(context,onResultListener);
    }

    @Override
    protected void release() {
        call.cancel();
    }

    public void postFeedback(FeedbackBean bean) {
        call = getAPIService().postFeedback("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json",bean);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (null != response.body()) {
                    try {
                        JSONObject object = new JSONObject(response.body());
                        if (object.optBoolean(mSuccess)) {
                            ToastUtils.showLongToast(getContext(), object.optString(mMessage));
                        } else {
                            ToastUtils.showLongToast(getContext(), object.optJSONObject(mError).optString(mMessage));
                        }
                        if(null != onResultListener){
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
}
