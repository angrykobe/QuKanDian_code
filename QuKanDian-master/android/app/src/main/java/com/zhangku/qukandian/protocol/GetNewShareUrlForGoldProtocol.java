package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.NewShareBean;

import org.json.JSONObject;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/11/15.
 * 转发分享获取主域名
 */

public class GetNewShareUrlForGoldProtocol extends NewBaseProtocol<NewShareBean> {
    private int mUserId;
    private int mPostId;
    public GetNewShareUrlForGoldProtocol(Context context, int userId, int postId, OnResultListener<NewShareBean> onResultListener) {
        super(context, onResultListener);
        mUserId = userId;
        mPostId = postId;
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().getShareUrl(mAuthorization,mContentType,mUserId,mPostId);
    }
}
