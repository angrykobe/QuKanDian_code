package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.utils.ToastUtils;

import org.json.JSONObject;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/9/21.
 */

public class VerificationShenmijinCodeProtocol extends BaseProtocol<Boolean> {
    private String mCode;
    public VerificationShenmijinCodeProtocol(Context context,String code, OnResultListener<Boolean> onResultListener) {
        super(context, onResultListener);
        mCode = code;
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().verificationCode(mAuthorization,mContentType,mCode);
    }

    @Override
    protected void getResult(JSONObject object) {
        if(null != onResultListener){
            if(!object.optBoolean(mSuccess)){
                ToastUtils.showShortToast(mContext,"验证码错误，请重新输入");
            }else {
                onResultListener.onResultListener(object.optBoolean(mSuccess));
            }
        }
    }
}
