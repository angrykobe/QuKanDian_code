package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.NewBaseListProtocol;
import com.zhangku.qukandian.bean.HadDownAppBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import java.util.List;

import retrofit2.Call;

/**
 * 创建者          xuzhida
 * 创建日期        2018/8/27
 * 2、我参与的列表
 */
public class GetHadDownAppListProtocol extends NewBaseListProtocol<HadDownAppBean> {

    private int page;

    public GetHadDownAppListProtocol(Context context, int page, OnResultListener<List<HadDownAppBean>> onResultListener) {
        super(context, onResultListener);
        this.page = page;
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getGoldTaskHadDoneList("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json",page,Config.PAGER_SIZE);
        return call;
    }
}
