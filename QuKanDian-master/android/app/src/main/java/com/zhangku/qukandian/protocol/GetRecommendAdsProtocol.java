package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.InformationBean;
import com.zhangku.qukandian.bean.RecommendAdsBean;
import com.zhangku.qukandian.bean.RegisterBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.DeviceUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/4/28.
 */

public class GetRecommendAdsProtocol extends NewBaseProtocol<RecommendAdsBean> {
    private int mChannelId;
    private int mType;
    private int mTopN;
    private boolean mIstop;

    public GetRecommendAdsProtocol(Context context, int channelId, int type, int topN, boolean istop, OnResultListener<RecommendAdsBean> onResultListener) {
        super(context, onResultListener);
        mChannelId = channelId;
        mTopN = UserManager.getInst().getQukandianBean().getLoadCount();//加载条数后台控制
        mType = type;
        mIstop = istop;
    }

    @Override
    protected Call getMyCall() {
        double[] latlng = DeviceUtil.getLatlng(mContext);
        String location = "0,0";
        if (latlng != null && latlng.length == 2) {
            location = latlng[0] + "," + latlng[1];
        }
        call = getAPIService().getRecommendAds(mAuthorization, mContentType, mChannelId, mType, mTopN, mIstop, "" + QuKanDianApplication.getCode(), location);
        return call;
    }

    @Override
    protected void getResult(RecommendAdsBean recommendAdsBean) {
        if (Constants.TYPE_NEW == mType) {
            MobclickAgent.onEvent(getContext(), "ArticleList");
        } else {
            MobclickAgent.onEvent(getContext(), "VideoList");
        }
        if (recommendAdsBean.getPostTexts()!=null){
            for (int i = 0; i < recommendAdsBean.getPostTexts().size(); i++) {
                InformationBean bean = recommendAdsBean.getPostTexts().get(i);
                if (mIstop && i == 0) {
                    bean.setTop(true);
                }
                bean.setChannelId(bean.getChannel().getChannelId());
                bean.setChannelName(bean.getChannel().getName());
                bean.setPostId(bean.getId());
            }
        }
        super.getResult(recommendAdsBean);
    }
}
