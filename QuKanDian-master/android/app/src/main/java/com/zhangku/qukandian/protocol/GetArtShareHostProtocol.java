package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONObject;

import retrofit2.Call;

/**
 * 获取正常收徒页面域名地址
 */
public class GetArtShareHostProtocol extends BaseProtocol<String> {
    private int postId;
    public GetArtShareHostProtocol(Context context,int postId, OnResultListener<String> onResultListener) {
        super(context, onResultListener);
        this.postId = postId;
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().getArticleShareHost("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU), "application/json",postId);
    }

    @Override
    protected void getResult(JSONObject object) {
        if(null != onResultListener){
            onResultListener.onResultListener(object.optString(mResult));
        }
    }
}
