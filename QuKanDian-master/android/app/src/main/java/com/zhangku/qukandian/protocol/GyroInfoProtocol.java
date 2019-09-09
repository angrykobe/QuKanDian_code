package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.XyzBean;

import java.util.Map;

import retrofit2.Call;

public class GyroInfoProtocol extends NewBaseProtocol<Map> {
    private float X;
    private float Y;
    private float Z;

    public GyroInfoProtocol(Context context, float x, float y, float z, OnResultListener<Map> onResultListener) {
        super(context, onResultListener);
        X = x;
        Y = y;
        Z = z;
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().gyroInfo(mAuthorization, mContentType, new XyzBean(X, Y, Z));
    }
}