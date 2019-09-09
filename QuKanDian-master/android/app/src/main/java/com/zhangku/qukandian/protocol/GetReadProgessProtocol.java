package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.ReadProgessBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.DeviceUtil;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import retrofit2.Call;

public class GetReadProgessProtocol extends NewBaseProtocol<ReadProgessBean> {

    public GetReadProgessProtocol(Context context, OnResultListener<ReadProgessBean> onResultListener) {
        super(context, onResultListener);
    }


    @Override
    protected Call getMyCall() {
        double[] latlng = DeviceUtil.getLatlng(mContext);
        String location = "0,0";
        if (latlng != null && latlng.length == 2) {
            location = latlng[0] + "," + latlng[1];
        }
        call = getAPIService().getReadProgressDetails("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU), "application/json",  location);
        return call;
    }
}
