package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.InformationBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/4/28.
 */

public class GetRecommendProtocol extends BaseProtocol {
    private int mChannelId;
    private int mType;
    private int mTopN;
    private boolean mIstop;

    public GetRecommendProtocol(Context context, int channelId, int type, int topN, boolean istop, OnResultListener<ArrayList<InformationBean>> onResultListener) {
        super(context, onResultListener);
        mChannelId = channelId;
        mTopN = UserManager.getInst().getQukandianBean().getLoadCount();//加载条数后台控制
        mType = type;
        mIstop = istop;
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getRecommend(mAuthorization, mContentType, mChannelId, mType, mTopN, mIstop, "" + QuKanDianApplication.getCode());
        return call;
    }

    @Override
    public void getResult(JSONObject object) {
        Gson gson = new Gson();
        ArrayList<InformationBean> mDatas = new ArrayList<>();
        JSONArray array = object.optJSONArray("result");
        if (null != array) {
            if (Constants.TYPE_NEW == mType) {
                MobclickAgent.onEvent(getContext(), "ArticleList");
            } else {
                MobclickAgent.onEvent(getContext(), "VideoList");
            }
            for (int i = 0; i < array.length(); i++) {
                InformationBean bean = gson.fromJson(array.optJSONObject(i).toString(), InformationBean.class);
                bean.setChannelId(bean.getChannel().getChannelId());
                bean.setChannelName(bean.getChannel().getName());
                bean.setPostId(bean.getId());
                if (mIstop && i == 0) {
                    // bean.setTop(true);
                }
                mDatas.add(bean);
            }
        }
        if (null != onResultListener) {
            onResultListener.onResultListener(mDatas);
        }
    }
}
