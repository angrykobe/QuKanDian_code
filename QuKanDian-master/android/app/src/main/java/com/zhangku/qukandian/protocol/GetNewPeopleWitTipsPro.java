package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.NewPeopleWithdrawalBean;
import retrofit2.Call;

/**
 * 创建者          xuzhida
 * 创建日期        2019/2/26
 */
public class GetNewPeopleWitTipsPro extends NewBaseProtocol<NewPeopleWithdrawalBean> {


    public GetNewPeopleWitTipsPro(Context context, OnResultListener<NewPeopleWithdrawalBean> onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getNewPeopleWitTips(mAuthorization,mContentType);
        return call;
    }

}
