package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.UserLevelBean;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.EventBusUtils;

import retrofit2.Call;

public class GetUserLevelProtocol extends NewBaseProtocol<UserLevelBean> {

    public GetUserLevelProtocol(Context context, OnResultListener<UserLevelBean> onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().GetUserLevel(mAuthorization, mContentType);
    }

    @Override
    protected void getResult(UserLevelBean userLevelBean) {
        UserManager.getInst().getUserBeam().setLevel(userLevelBean.getGrade());
        UserManager.getInst().getUserBeam().setLevelDisplayName(userLevelBean.getLevelInfo().getDisplayName());
        UserManager.getInst().getUserBeam().setExp(userLevelBean.getExp());
        UserManager.getInst().getUserBeam().setNextExp(userLevelBean.getNextLevelInfo() == null ? 0 : userLevelBean.getNextLevelInfo().getExp());
        UserManager.getInst().getUserBeam().setStartExp(userLevelBean.getLevelInfo().getExp());
        EventBusUtils.postUserBean(UserManager.getInst().getUserBeam());
        super.getResult(userLevelBean);
    }
}
