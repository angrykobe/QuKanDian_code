package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.observer.CommentOberser;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONObject;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/9/14.
 */

public class PutPraiseProtocol extends BaseProtocol<Boolean> {
    private int mCommentId;
    public PutPraiseProtocol(Context context,int commentId, OnResultListener<Boolean> onResultListener) {
        super(context, onResultListener);
        mCommentId = commentId;
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().putPraise("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU), "application/json",mCommentId,1);
    }

    @Override
    protected void getResult(JSONObject object) {
        if(null != onResultListener){
            onResultListener.onResultListener(object.optBoolean(mSuccess));
            ToastUtils.showShortToast(mContext,"点赞成功");
            CommentOberser.getInstance().notifys(CommentOberser.TYPE_PRAISE);
        }
    }
}
