package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.NewBaseListProtocol;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.utils.AppUtils;
import com.zhangku.qukandian.utils.DeviceUtil;

import java.util.List;

import retrofit2.Call;

public class GetAdsListForTypeProtocol extends NewBaseListProtocol<AdLocationBeans.AdLocationsBean.ClientAdvertisesBean> {
    private int mType;
    private int mChannelId;

    public GetAdsListForTypeProtocol(Context context, int type, int channel, OnResultListener<List<AdLocationBeans.AdLocationsBean.ClientAdvertisesBean>> onResultListener) {
        super(context, onResultListener);
        mType = type;
        mChannelId = channel;
    }

    @Override
    protected Call getMyCall() {
        double[] latlng = DeviceUtil.getLatlng(mContext);
        String location = "0,0";
        if (latlng != null && latlng.length == 2) {
            location = latlng[0] + "," + latlng[1];
        }
        int versionCode = AppUtils.getVersionCode(mContext);
        String version=versionCode+"";
        return getAPIService().getAdListForType(mAuthorization, mContentType, mType, mChannelId, location,version);
    }
}