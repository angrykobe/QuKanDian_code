package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.UserPostBehaviorDto;
import com.zhangku.qukandian.utils.CommonHelper;

import org.json.JSONObject;

import retrofit2.Call;

/**
 * Created by xuzhida on 2017/12/7.
 * 设置屏蔽新闻
 */
public class DisLikeNewsProtocol extends BaseProtocol<Object> {
    private UserPostBehaviorDto mDislikeBean;
    public boolean success = false;

    public DisLikeNewsProtocol(Context context, UserPostBehaviorDto mDislikeBean, OnResultListener<Object> onResultListener) {
        super(context, onResultListener);
        this.mDislikeBean = mDislikeBean;
    }

    @Override
    protected Call getMyCall() {
        Gson gson = new Gson();
        String g = gson.toJson(mDislikeBean);

        return getAPIService().dislikeNews(mAuthorization, mContentType
                , CommonHelper.md5(key + "data" + g + "nonceStr" + rand + "timestamp" + time),
                time, appid, rand, mDislikeBean);
    }

    @Override
    protected void getResult(JSONObject object) {
        if(object.optBoolean("success")){
            onResultListener.onResultListener(object);
        }else{
            onResultListener.onFailureListener(0,"");
        }
    }
}
