package com.zhangku.qukandian.protocol;

import android.content.Context;
import android.support.v7.util.SortedList;

import com.google.gson.Gson;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.DetailsBean;
import com.zhangku.qukandian.bean.UserPostBehaviorDto;
import com.zhangku.qukandian.utils.CommonHelper;

import org.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;

import retrofit2.Call;
import retrofit2.http.Query;

/**
 * Created by xuzhida on 2017/12/7.
 * 取消点赞
 */
public class RemovePraiseProtocol extends BaseProtocol<Object> {
    private int userId;
    private long postId;
    private String zyId;
    private int textType;

    public RemovePraiseProtocol(Context context, int userId, long postId, OnResultListener<Object> onResultListener) {
        super(context, onResultListener);
        this.userId = userId;
        this.postId = postId;
        textType = 1;
    }

    public RemovePraiseProtocol(Context context, int userId, DetailsBean bean, OnResultListener<Object> onResultListener) {
        super(context, onResultListener);
        this.userId = userId;
        this.postId = bean.getNewId();
        this.zyId = bean.getZyId();
        this.textType = bean.getTextType();
    }

    @Override
    protected Call getMyCall() {
        if(1==textType){
            return getAPIService().removePraiseForVideo(mAuthorization, mContentType,
                    CommonHelper.MyCheck(rand,time,"userId"+userId,"postId"+postId),
                    time, appid, rand, userId, postId);
        }else{
            return getAPIService().removePraise(mAuthorization, mContentType,
                    CommonHelper.MyCheck(rand,time,"userId"+userId,"postId"+postId,"zyId"+zyId,"textType"+textType ),
                    time, appid, rand, userId, postId,zyId,textType);
        }
    }

    @Override
    protected void getResult(JSONObject object) {
        if (object.optBoolean("success")) {
            onResultListener.onResultListener(object.optString("result"));
        } else {
            onResultListener.onFailureListener(0, "");
        }
    }
}
