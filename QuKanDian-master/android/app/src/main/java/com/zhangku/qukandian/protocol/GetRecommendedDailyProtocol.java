package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.RecommendDailyBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2018/1/3.
 * 获取每日推荐列表
 */

public class GetRecommendedDailyProtocol extends BaseProtocol<ArrayList<RecommendDailyBean>> {
    public GetRecommendedDailyProtocol(Context context, OnResultListener<ArrayList<RecommendDailyBean>> onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().recommendmissions(mAuthorization, mContentType,1);
    }

    @Override
    protected void getResult(JSONObject object) {
        if (null != object) {
            if (object.optBoolean(mSuccess)) {
                JSONArray array = object.optJSONArray(mResult);
                if (array.length() > 0) {
                    Gson gson = new Gson();
                    ArrayList<RecommendDailyBean> list = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        RecommendDailyBean bean = gson.fromJson(array.optJSONObject(i).toString(), RecommendDailyBean.class);
                        list.add(bean);
                    }
                    if (null != onResultListener) {
                        onResultListener.onResultListener(list);
                    }
                }
            }
        }
    }
}
