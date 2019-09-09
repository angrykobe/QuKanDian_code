package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.DoneTaskResBean;
import com.zhangku.qukandian.bean.DownAppBean;
import com.zhangku.qukandian.bean.TaskChestBean;
import com.zhangku.qukandian.utils.CommonHelper;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2018/3/28.
 */

public class PutNewTaskchestBoxProtocol extends NewBaseProtocol<DoneTaskResBean> {

    private int orderId;
    private TaskChestBean bean;

    public PutNewTaskchestBoxProtocol(Context context, TaskChestBean bean, OnResultListener<DoneTaskResBean> onResultListener) {
        super(context, onResultListener);
        this.bean = bean;
        this.orderId = bean.getOrder();
    }

    @Override
    protected Call getMyCall() {
        int time = CommonHelper.getCheckTimeStamp(QuKanDianApplication.mTimeOffset);
        String rand = CommonHelper.getStringRandom(32);
        return getAPIService().PutTaskchestboxconfig(mAuthorization, mContentType,
                CommonHelper.md5(key + "nonceStr" + rand + "orderId" + orderId + "timestamp" + time),
                time, appid, rand, orderId);
    }

    @Override
    protected void getResult(DoneTaskResBean doneTaskResBean) {
        super.getResult(doneTaskResBean);
        EventBus.getDefault().post(bean);
    }
}
