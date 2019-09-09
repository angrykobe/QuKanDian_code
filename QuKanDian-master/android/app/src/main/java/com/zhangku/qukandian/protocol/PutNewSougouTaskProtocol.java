package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.activitys.TaskWebViewAct;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.AdMissionBean;
import com.zhangku.qukandian.bean.DoneTaskResBean;
import com.zhangku.qukandian.bean.NewPeopleTaskBean;
import com.zhangku.qukandian.dialog.DialogNewPeopleTask;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.CustomToast;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/12/13.
 */

public class PutNewSougouTaskProtocol extends NewBaseProtocol<DoneTaskResBean> {
    private AdMissionBean mAdMissionBean;
    public PutNewSougouTaskProtocol(Context context, AdMissionBean bean, OnResultListener<DoneTaskResBean> onResultListener) {
        super(context, onResultListener);
        mAdMissionBean = bean;
    }

    @Override
    protected Call getMyCall() {
        Gson gson = new Gson();
        String g = gson.toJson(mAdMissionBean);

        return getAPIService().putMissionCommon(mAuthorization,mContentType, CommonHelper.md5(key + "data"+g+"nonceStr"
                        + rand + "timestamp"
                        + time),
                time, appid, rand,mAdMissionBean);
    }

    @Override
    protected void getResult(DoneTaskResBean doneTaskResBean) {
        super.getResult(doneTaskResBean);
        UserManager.getInst().getQukandianBean().setRscnt(doneTaskResBean.getRsCnt());
    }
}
