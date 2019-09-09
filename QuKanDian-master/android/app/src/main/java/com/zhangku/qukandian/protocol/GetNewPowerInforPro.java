package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.NewBaseListProtocol;
import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.UserLevelBean;
import com.zhangku.qukandian.bean.UserLevelInforBean;

import java.util.List;

import retrofit2.Call;

/**
 * 创建者          xuzhida
 * 创建日期        2019/2/26
 */
public class GetNewPowerInforPro extends NewBaseProtocol<UserLevelBean> {

    public GetNewPowerInforPro(Context context, OnResultListener<UserLevelBean> onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getPowerInfor(mAuthorization,mContentType, false);
        return call;
    }

}
