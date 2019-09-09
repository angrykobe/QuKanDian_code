package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.SignRuleBean;

import org.json.JSONObject;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2018/1/25.
 */

public class GetCheckinRuleProtocol extends BaseProtocol<SignRuleBean> {
    public GetCheckinRuleProtocol(Context context, OnResultListener<SignRuleBean> onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().getCheckinrule();
    }

    @Override
    protected void getResult(JSONObject object) {
        if (null != object) {
            JSONObject object1 = object.optJSONObject(mResult);
            if (null != object1) {
                Gson gson = new Gson();
                SignRuleBean bean = gson.fromJson(object1.toString(), SignRuleBean.class);
                if (null != bean) {
                    if (null != onResultListener) {
                        onResultListener.onResultListener(bean);
                    }
                }
            }
        }
    }
}
