package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.DetailsBean;
import com.zhangku.qukandian.bean.PostTextDtl;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import retrofit2.Call;

/**
 * 新闻详情
 */
public class GetNewDetailsArtProtocol extends NewBaseProtocol<PostTextDtl> {
    private int mId;
    private String zyId;
    private int channelId;

    public GetNewDetailsArtProtocol(Context context, int id, String zyId,  int channelId,OnResultListener<PostTextDtl> onResultListener) {
        super(context,onResultListener);
        mId = id;
        this.zyId = zyId;
        this.channelId = channelId;
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getInformationDetails2("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json",mId,zyId,channelId);
        return call;
    }
}
