package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.AdMissionOtherBean;
import com.zhangku.qukandian.bean.SubmitTaskBean;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.CustomToast;

import org.json.JSONObject;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2018/1/10.
 */

public class DynmicmissionProtocol extends NewBaseProtocol<SubmitTaskBean> {

    private AdMissionOtherBean mAdMissionOtherBean;

    public DynmicmissionProtocol(Context context, AdMissionOtherBean bean, OnResultListener<SubmitTaskBean> onResultListener) {
        super(context, onResultListener);
        mAdMissionOtherBean = bean;
    }

    @Override
    protected Call getMyCall() {
        int time = CommonHelper.getCheckTimeStamp(QuKanDianApplication.mTimeOffset);
        String rand = CommonHelper.getStringRandom(32);
        Gson gson = new Gson();
        String g = gson.toJson(mAdMissionOtherBean);
        return getAPIService().dynamicmission(mAuthorization, mContentType,
                CommonHelper.md5(key
                        + "data" + g
                        + "nonceStr" + rand
                        + "timestamp" + time),
                time, appid, rand, mAdMissionOtherBean);
    }

    @Override
    protected void getResult(SubmitTaskBean object) {
        UserManager.getInst().goldChangeNofity(Integer.valueOf(object.getGoldAmount()));
        super.getResult(object);
    }
}
