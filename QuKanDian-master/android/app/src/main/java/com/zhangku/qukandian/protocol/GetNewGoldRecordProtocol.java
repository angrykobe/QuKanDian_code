package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.NewBaseListProtocol;
import com.zhangku.qukandian.bean.GoldBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import java.util.List;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/4/13.
 */

public class GetNewGoldRecordProtocol extends NewBaseListProtocol<GoldBean> {
    private int mPage;
    private int mSize;

    public GetNewGoldRecordProtocol(Context context, int page, int size, OnResultListener onResultListener) {
        super(context, onResultListener);
        mPage = page;
        mSize = size;
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getTodayGoldRecord("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU), "application/json", mPage, mSize);
        return call;
    }

    @Override
    protected void getResult(List<GoldBean> list) {
        super.getResult(list);
    }
}
