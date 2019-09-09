package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONObject;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/5/8.
 */

public class GetFavoriteStatusProtocol extends BaseProtocol {
    private int mPostId;
    private String zyId;

    public GetFavoriteStatusProtocol(Context context,int postId,OnResultListener<Boolean> onResultListener) {
        super(context, onResultListener);
        mPostId = postId;
    }

    public GetFavoriteStatusProtocol(Context context,int postId, String zyId,OnResultListener<Boolean> onResultListener) {
        super(context, onResultListener);
        mPostId = postId;
        this.zyId = zyId;
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getFavoriteStatus("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json",mPostId,zyId);
        return call;
    }

    @Override
    protected void getResult(JSONObject object) {
        if(object.optBoolean(mSuccess)){
            JSONObject result = object.optJSONObject(mResult);
            if(null != onResultListener){
                onResultListener.onResultListener(result.optBoolean("isfavorite"));
            }

        }
    }

    @Override
    public void release() {
        call.cancel();
    }
}
