package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.PushCommentBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.observer.CommentOberser;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONObject;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/9/14.
 */

public class SubmitCommentProtocol extends BaseProtocol<Boolean> {
    private PushCommentBean mPushCommentBean;

    public SubmitCommentProtocol(Context context, PushCommentBean bean, OnResultListener<Boolean> onResultListener) {
        super(context, onResultListener);
        mPushCommentBean = bean;
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().submitComment("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU), "application/json", mPushCommentBean);
    }

    @Override
    protected void getResult(JSONObject object) {
        if (null != onResultListener) {
            onResultListener.onResultListener(object.optBoolean(mSuccess));
            CommentOberser.getInstance().notifys(CommentOberser.TYPE_COMMENT);
        }
    }
}
