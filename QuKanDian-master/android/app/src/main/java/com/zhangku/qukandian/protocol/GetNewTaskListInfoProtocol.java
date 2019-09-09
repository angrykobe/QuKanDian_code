package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.NewBaseListProtocol;
import com.zhangku.qukandian.bean.TaskBean;

import java.util.List;

import retrofit2.Call;

/**
 */

public class GetNewTaskListInfoProtocol extends NewBaseListProtocol<TaskBean> {
    private static long last_time = 0;

    public GetNewTaskListInfoProtocol(Context context, OnResultListener<List<TaskBean>> onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getTaskListInfo(mAuthorization, mContentType, "" + QuKanDianApplication.getCode());
        return call;
    }

    @Override
    public void postRequest() {
        // 三秒防止刷
        //if (System.currentTimeMillis() - last_time < 3 * 1000) {
        //    if (onResultListener != null) {
        //        onResultListener.onFailureListener(-1100, "");
        //    }
        //    return;
        //}
        last_time = System.currentTimeMillis();
        super.postRequest();
    }
}
