package com.zhangku.qukandian.protocol;

import android.content.Context;
import android.text.TextUtils;

import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.EventBusUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import cn.jpush.android.api.JPushInterface;
import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/4/1.
 */

public class GetNewUserInfoProtocol extends NewBaseProtocol<UserBean> {
    public GetNewUserInfoProtocol(Context context, OnResultListener onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getUserInfo("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU), "application/json");
        return call;
    }

    @Override
    protected void getResult(final UserBean userBean) {
        UserManager.getInst().setUserInfor(userBean);
        UserSharedPreferences.getInstance()
                .putString(Constants.PHONE, userBean.getMobileNumber())
                .putInt(Constants.USER_ID, userBean.getId());
        UserManager.getInst().updateUserIcon(userBean.getAvatarUrl());
        UserManager.getInst().updateNickName(userBean.getNickName());

        UserManager.getInst().getUserBeam().setExp(userBean.getExp());
        UserManager.getInst().getUserBeam().setStartExp(userBean.getStartExp());
        UserManager.getInst().getUserBeam().setNextExp(userBean.getNextExp());
        UserManager.getInst().getUserBeam().setLevel(userBean.getLevel());
        UserManager.getInst().getUserBeam().setLevelDisplayName(userBean.getLevelDisplayName());
        EventBusUtils.postUserBean(UserManager.getInst().getUserBeam());
        if (null != mContext) {
            JPushInterface.setAlias(mContext, userBean.getId(), userBean.getId() + "");
            //用户登陆过清楚标签
            JPushInterface.cleanTags(mContext, 1);
//          MiPushClient.setAlias(mContext, userBean.getId() + "", userBean.getId() + "");
        }
        //广告获取 实时刷新广告
        // new GetNewAdListProtocol(mContext).postRequest();
        //首次进入app无网络导致活动icon加载不出来bug处理
        if (TextUtils.isEmpty(UserManager.getInst().getmRuleBean().getBottomIconConfig().getIcon()))
            new GetNewRuleProtocol(mContext, null).postRequest();
        super.getResult(userBean);
    }

    @Override
    protected void logout() {
        super.logout();
        //广告获取
        // new GetNewAdListProtocol(mContext).postRequest();
        if (null != onResultListener) {
            onResultListener.onResultListener(null);
        }
    }
}
