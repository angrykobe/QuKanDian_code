package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.ShareBean;

import org.json.JSONObject;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2018/2/2.
 */

public class GetShareImgProtocol extends BaseProtocol<ShareBean> {
    public GetShareImgProtocol(Context context, OnResultListener<ShareBean> onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().getShareImg();
    }

    @Override
    protected void getResult(JSONObject object) {
        if (null == object) {
            return;
        }
        if (object.optBoolean(mSuccess)) {
            Gson gson = new Gson();
            ShareBean bean = gson.fromJson(object.optJSONObject(mResult).toString(), ShareBean.class);
            if (null != bean && null != onResultListener) {
                onResultListener.onResultListener(bean);
            }

        }
    }
}
