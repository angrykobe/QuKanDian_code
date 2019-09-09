package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.CollectBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * 收藏动作
 */
public class PutNewCollectArticleProtocol extends NewBaseProtocol<Object> {
    private List<CollectBean> mlist = new ArrayList<>();
    private boolean b;

    public PutNewCollectArticleProtocol(Context context, boolean b,CollectBean bean , OnResultListener<Object> onResultListener) {
        super(context, onResultListener);
        this.b = b;
        mlist.add(bean);
    }
    public PutNewCollectArticleProtocol(Context context, boolean b, List<CollectBean> list, OnResultListener<Object> onResultListener) {
        super(context, onResultListener);
        this.b = b;
        mlist.addAll(list);
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().collect("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU), "application/json", b,mlist);
        return call;
    }
}
