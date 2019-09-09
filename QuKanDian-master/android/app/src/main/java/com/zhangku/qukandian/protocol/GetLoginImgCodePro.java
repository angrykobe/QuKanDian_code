package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.CodeBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import retrofit2.Call;

/**
 * 创建者          xuzhida
 * 创建日期        2019/1/27
 * 你不注释一下？
 */
public class GetLoginImgCodePro extends NewBaseProtocol<CodeBean> {

    private String mTel;

    public GetLoginImgCodePro(Context context, String tel, OnResultListener<CodeBean> onResultListener) {
        super(context, onResultListener);
        mTel = tel;
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().Getimgcode1("Bearer "
                        + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),
                "application/json", CommonHelper.md5(key + "nonceStr"
                        + rand + "tel" + mTel + "timestamp"
                        + time),
                time, appid, rand,
                mTel);
        return call;
    }
}
