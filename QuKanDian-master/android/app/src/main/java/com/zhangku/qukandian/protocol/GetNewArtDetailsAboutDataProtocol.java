package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.NewBaseListProtocol;
import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.InformationBean;
import com.zhangku.qukandian.bean.RecommendAdsBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.DeviceUtil;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import java.util.List;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/3/30.
 */

public class GetNewArtDetailsAboutDataProtocol extends NewBaseProtocol<RecommendAdsBean> {
    private int mPostId;
    private int mTopN;
    private String zyId;

    public GetNewArtDetailsAboutDataProtocol(Context context, int postId, int topN, String zyId, OnResultListener<RecommendAdsBean> onResultListener) {
        super(context, onResultListener);
        mPostId = postId;
        mTopN = topN;
        this.zyId = zyId;
    }

    public GetNewArtDetailsAboutDataProtocol(Context context, int postId, int topN, OnResultListener<RecommendAdsBean> onResultListener) {
        super(context, onResultListener);
        mPostId = postId;
        mTopN = topN;
    }

    @Override
    protected Call getMyCall() {
        double[] latlng = DeviceUtil.getLatlng(mContext);
        String location = "0,0";
        if (latlng != null && latlng.length == 2) {
            location = latlng[0] + "," + latlng[1];
        }
        call = getAPIService().getAboutListAds("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU), "application/json", mPostId, mTopN, zyId, 0, location);
        return call;
    }
}
