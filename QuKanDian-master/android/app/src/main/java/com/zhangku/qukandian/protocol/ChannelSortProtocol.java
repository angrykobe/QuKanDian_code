package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONObject;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/3/29.
 */

public class ChannelSortProtocol extends BaseProtocol {
    private int mChannelId;
    private int mOrderId;
    public ChannelSortProtocol(Context context,int channelId, int orderId) {
        super(context, null);
        mChannelId = channelId;
        mOrderId = orderId;
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getMainListResponse("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json",mChannelId, mOrderId);
        return call;
    }

    @Override
    protected void getResult(JSONObject object) {

    }
}
