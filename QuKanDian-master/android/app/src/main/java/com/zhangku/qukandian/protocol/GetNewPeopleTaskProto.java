package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.NewBaseListProtocol;
import com.zhangku.qukandian.bean.RecommendDailyBean;

import retrofit2.Call;

/**
 * 创建者          xuzhida
 * 创建日期        2018/8/2
 * 新手任务奖励
 */
public class GetNewPeopleTaskProto extends NewBaseListProtocol<RecommendDailyBean> {

    public GetNewPeopleTaskProto(Context context, OnResultListener onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().getNewPeopleTask(mAuthorization, mContentType);
    }
}
