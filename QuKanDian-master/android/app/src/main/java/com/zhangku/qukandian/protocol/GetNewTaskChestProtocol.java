package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.NewBaseListProtocol;
import com.zhangku.qukandian.bean.TaskChestBean;

import java.util.List;

import retrofit2.Call;

/**
 * 创建者          xuzhida
 * 创建日期        2018/11/1
 * 你不注释一下？
 */
public class GetNewTaskChestProtocol extends NewBaseListProtocol<TaskChestBean> {
    private static long last_time = 0;

    public GetNewTaskChestProtocol(Context context, OnResultListener<List<TaskChestBean>> onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().GetTaskchestboxconfig(mAuthorization, mContentType);
    }

    @Override
    public void postRequest() {
        // 三秒防止刷
        //long space = System.currentTimeMillis() - last_time;
        //if (space < 3 * 1000) {
        //    return;
        //}
        last_time = System.currentTimeMillis();
        super.postRequest();
    }
}
