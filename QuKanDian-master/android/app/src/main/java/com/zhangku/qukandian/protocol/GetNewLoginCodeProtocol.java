package com.zhangku.qukandian.protocol;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;
import com.zhangku.qukandian.utils.ToastUtils;

import retrofit2.Call;

public class GetNewLoginCodeProtocol extends NewBaseProtocol<Object> {
    private String mTel;
    private String mCode;

    public GetNewLoginCodeProtocol(Context context, String tel,String code, OnResultListener<Object> onResultListener) {
        super(context, onResultListener);
        mTel = tel;
        mCode = code;
    }

    @Override
    protected Call getMyCall() {
        if(TextUtils.isEmpty(mCode)){
            int time = CommonHelper.getCheckTimeStamp(QuKanDianApplication.mTimeOffset);
            String rand = CommonHelper.getStringRandom(32);
            call = getAPIService().Getlogincaptcha("Bearer "
                            + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),
                    "application/json", CommonHelper.md5(key
                            + "nonceStr" + rand + "tel" + mTel + "timestamp" + time),
                    time, appid, rand,
                    mTel);
        }else{
            int time = CommonHelper.getCheckTimeStamp(QuKanDianApplication.mTimeOffset);
            String rand = CommonHelper.getStringRandom(32);
            call = getAPIService().Getlogincaptcha("Bearer "
                            + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),
                    "application/json", CommonHelper.md5(key + "code" + mCode
                            + "nonceStr" + rand + "tel" + mTel + "timestamp" + time),
                    time, appid, rand,
                    mTel,mCode);
        }
        return call;
    }

    @Override
    protected void getResult(Object o, String messgae) {
        ToastUtils.showLongToast(mContext,messgae);
        super.getResult(o, messgae);
    }
}
