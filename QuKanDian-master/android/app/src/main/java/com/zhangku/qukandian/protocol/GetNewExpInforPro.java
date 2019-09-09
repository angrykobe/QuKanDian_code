package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.NewBaseListProtocol;
import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.NewPeopleWithdrawalBean;
import com.zhangku.qukandian.bean.UserLevelInforBean;

import java.util.List;

import retrofit2.Call;

/**
 * 创建者          xuzhida
 * 创建日期        2019/2/26
 */
public class GetNewExpInforPro extends NewBaseListProtocol<UserLevelInforBean> {

    public GetNewExpInforPro(Context context, OnResultListener<List<UserLevelInforBean>> onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getExpInfor(mAuthorization,mContentType);
        return call;
    }

}
