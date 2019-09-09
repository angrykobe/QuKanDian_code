package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yuzuoning on 2017/4/1.
 */

public class PutUserInfoProtocol extends BaseModel {
    public PutUserInfoProtocol(Context context,OnResultListener onResultListener) {
        super(context,onResultListener);

    }

    @Override
    protected void release() {
        call.cancel();
    }

    public void putUserInfo(final UserBean userBean){
        call = getAPIService().putUserInfo("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json",userBean);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    if(null != response.body()){
                        JSONObject object = new JSONObject(response.body());
                        if(object.optBoolean("success")){
                            UserManager.getInst().updateNickName(userBean.getNickName());
                            if(null != onResultListener){
                                onResultListener.onResultListener(null);
                            }
                        }
                        ToastUtils.showLongToast(getContext(),object.optString("message"));
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
}
