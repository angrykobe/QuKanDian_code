package com.zhangku.qukandian.protocol;

import android.content.Context;
import android.util.Log;

import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yuzuoning on 2017/3/29.
 */

public class ChannelDeleteProtocol extends BaseModel {
    public ChannelDeleteProtocol(Context context) {
        super(context,null);
    }

    @Override
    protected void release() {
        call.cancel();
    }

    public void getRusult(int channelId,boolean yesNo) {
        call = getAPIService().getMainListResponse("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json",channelId, yesNo);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("TAG","ch:"+response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
