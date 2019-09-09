package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.SharedBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/11/15.
 */

public class GetSharedDataProtocol extends BaseProtocol<ArrayList<SharedBean>> {
    private int mPage;
    private int mSize;
    public GetSharedDataProtocol(Context context,int page,int size, OnResultListener<ArrayList<SharedBean>> onResultListener) {
        super(context, onResultListener);
        mPage = page;
        mSize = size;
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().getSharedData(mAuthorization,mContentType, UserSharedPreferences.getInstance().getInt(Constants.USER_ID),mPage,mSize);
    }

    @Override
    protected void getResult(JSONObject object) {
        if(null != object){
            if(object.optBoolean(mSuccess)){
                JSONArray array = object.optJSONArray(mResult);
                ArrayList<SharedBean> mDatas = new ArrayList<>();
                if(array != null && array.length() > 0){
                    Gson gson = new Gson();
                    for (int i = 0; i < array.length(); i++) {
                        SharedBean bean = gson.fromJson(array.optJSONObject(i).toString(),SharedBean.class);
                        mDatas.add(bean);
                    }
                }
                if(null != onResultListener){
                    onResultListener.onResultListener(mDatas);
                }
            }
        }
    }
}
