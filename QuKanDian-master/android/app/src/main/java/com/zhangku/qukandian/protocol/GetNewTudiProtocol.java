package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.NewTudiBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/8/11.
 */

public class GetNewTudiProtocol extends BaseProtocol<ArrayList<NewTudiBean>> {
    private int mPage;
    private int mSize;
    private int mStep;
    public GetNewTudiProtocol(Context context,int step,int page,int size, OnResultListener<ArrayList<NewTudiBean>> onResultListener) {
        super(context, onResultListener);
        mPage = page;
        mSize = size;
        mStep = step;
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().getV3Mentee(mAuthorization,mContentType,mStep,mPage,mSize);
    }

    @Override
    protected void getResult(JSONObject object) {
        if(null != onResultListener){
            ArrayList<NewTudiBean> arrayList = new ArrayList<>();
            JSONArray array = object.optJSONArray(mResult);
            if(null != array){
                Gson gson = new Gson();
                for (int i = 0; i < array.length(); i++) {
                    NewTudiBean bean = gson.fromJson(array.optJSONObject(i).toString(),NewTudiBean.class);
                    arrayList.add(bean);
                }
            }
            onResultListener.onResultListener(arrayList);
        }
    }
}
