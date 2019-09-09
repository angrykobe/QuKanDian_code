package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.DoneTaskResBean;
import com.zhangku.qukandian.bean.PutReadProgessBean;
import com.zhangku.qukandian.utils.CommonHelper;


import retrofit2.Call;

public class PutReadProgessProtocol extends NewBaseProtocol<PutReadProgessBean> {

    private int orderId;

    public PutReadProgessProtocol(Context context, int orderId,OnResultListener<PutReadProgessBean> onResultListener) {
        super(context, onResultListener);
        this.orderId = orderId;
    }

    @Override
    protected Call getMyCall() {
        int time = CommonHelper.getCheckTimeStamp(QuKanDianApplication.mTimeOffset);
        String rand = CommonHelper.getStringRandom(32);
        return getAPIService().PutReadProgress(mAuthorization, mContentType,
                CommonHelper.md5(key + "nonceStr" + rand + "orderId" + orderId + "timestamp" + time),
                time, appid, rand, orderId);
    }

}
