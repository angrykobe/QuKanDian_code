package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.NewBaseListProtocol;
import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.PostAdAwakenBean;
import com.zhangku.qukandian.bean.ReadTipsBean;
import com.zhangku.qukandian.manager.UserManager;

import java.util.List;

import retrofit2.Call;

/**
 * 创建者          xuzhida
 * 创建日期        2019/2/26
 */
public class GetReadTipsPro extends NewBaseListProtocol<ReadTipsBean> {

    public GetReadTipsPro(Context context, OnResultListener onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getReadtips(mAuthorization,mContentType);
        return call;
    }

    @Override
    protected void getResult(List<ReadTipsBean> list) {
        super.getResult(list);
        UserManager.getInst().setReadTips(list);
    }
}
