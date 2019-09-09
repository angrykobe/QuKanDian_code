package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.NewShareBean;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/11/15.
 * 转发分享获取主域名
 */

public class GetNewShareUrlForGold2Protocol extends NewBaseProtocol<NewShareBean> {
    private int mUserId;
    private int mPostId;
    private int zfType;// zfType==1 高价文分享  0 普通金币文章分享
    public GetNewShareUrlForGold2Protocol(Context context, int userId, int postId, int zfType, OnResultListener<NewShareBean> onResultListener) {
        super(context, onResultListener);
        mUserId = userId;
        mPostId = postId;
        this.zfType = zfType;
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().getShareUrl(mAuthorization,mContentType,mUserId,mPostId,zfType);
    }
}
