package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.NewBaseListProtocol;
import com.zhangku.qukandian.bean.RecommendDailyBean;

import java.util.List;

import retrofit2.Call;

/**
 * 获取每日推荐列表
 */
public class GetNewRecommendedDailyProtocol extends NewBaseListProtocol<RecommendDailyBean> {

    private int type;
    public GetNewRecommendedDailyProtocol(Context context, int type, OnResultListener<List<RecommendDailyBean>> onResultListener) {
        super(context, onResultListener);
        this.type = type;
    }

    @Override
    protected Call getMyCall() {
        //type（1：首页任务弹窗，2：签到弹窗，3：宝箱弹窗）
        return getAPIService().recommendmissions(mAuthorization, mContentType,type);
    }
}
