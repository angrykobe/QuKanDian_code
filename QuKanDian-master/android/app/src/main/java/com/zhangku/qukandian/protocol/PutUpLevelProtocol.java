package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.UpLevelResBean;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.EventBusUtils;

import retrofit2.Call;

public class PutUpLevelProtocol extends NewBaseProtocol<UpLevelResBean> {

    public PutUpLevelProtocol(Context context, OnResultListener<UpLevelResBean> onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().PutUpLevel(mAuthorization, mContentType);
    }

    @Override
    protected void getResult(UpLevelResBean o) {
        UserManager.getInst().goldChangeNofity(o.getLevelAwardGold());
        UserManager.getInst().getUserBeam().setLevel(o.getLevel());
        UserManager.getInst().getUserBeam().setLevelDisplayName(o.getDisplayName());
        EventBusUtils.postUserBean(UserManager.getInst().getUserBeam());
        super.getResult(o);
    }
}
