package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.AdMissionBean;
import com.zhangku.qukandian.bean.SougouCacheBean;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.CustomToast;

import org.json.JSONObject;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/12/13.
 */

public class PutAdMissionProtocol extends BaseProtocol {
    private AdMissionBean mAdMissionBean;
    public PutAdMissionProtocol(Context context, AdMissionBean bean, OnResultListener<Boolean> onResultListener) {
        super(context, onResultListener);
        mAdMissionBean = bean;
    }

    @Override
    protected Call getMyCall() {
        int time = CommonHelper.getCheckTimeStamp(QuKanDianApplication.mTimeOffset);
        String rand = CommonHelper.getStringRandom(32);

        Gson gson = new Gson();
        String g = gson.toJson(mAdMissionBean);

        return getAPIService().putMissionCommon(mAuthorization,mContentType, CommonHelper.md5(key + "data"+g+"nonceStr"
                        + rand + "timestamp"
                        + time),
                time, appid, rand,mAdMissionBean);
    }

    @Override
    protected void getResult(JSONObject object) {
        if(null != object){
            if(null != onResultListener){
                if(object.optBoolean(mSuccess)){
                    JSONObject object1 = object.optJSONObject(mResult);
                    if(null == object1){
                        return;
                    }
                    CustomToast.showToastOther(mContext,object1.optInt("goldAmount")+"","任务奖励");
                    UserManager.getInst().goldChangeNofity(Integer.valueOf(object1.optString("goldAmount")));

                    SougouCacheBean.addSougouAdCache();
                    onResultListener.onResultListener(object.optBoolean(mSuccess));
                }
            }
        }
    }
}
