package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.AppErrorLogDto;

import org.json.JSONObject;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2018/3/28.
 */

public class AddLogProtocol extends BaseProtocol {
    private AppErrorLogDto mAppErrorLogDto;
    public AddLogProtocol(Context context, AppErrorLogDto bean, OnResultListener onResultListener) {
        super(context, onResultListener);
        mAppErrorLogDto = bean;
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().addLog(mAuthorization,mContentType,mAppErrorLogDto);
    }

    @Override
    protected void getResult(JSONObject object) {

    }
}
