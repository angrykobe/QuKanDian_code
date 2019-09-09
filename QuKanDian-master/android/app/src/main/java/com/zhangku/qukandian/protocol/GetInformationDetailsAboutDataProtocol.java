package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.InformationBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/3/30.
 */

public class GetInformationDetailsAboutDataProtocol extends BaseProtocol {
    private int mPostId;
    private int mTopN;
    private String zyId;

    public GetInformationDetailsAboutDataProtocol(Context context,int postId,int topN, OnResultListener<ArrayList<InformationBean>> onResultListener) {
        super(context,onResultListener);
        mPostId = postId;
        mTopN = topN;
    }
    public GetInformationDetailsAboutDataProtocol(Context context,int postId,int topN, String zyId, OnResultListener<ArrayList<InformationBean>> onResultListener) {
        super(context,onResultListener);
        mPostId = postId;
        mTopN = topN;
        this.zyId = zyId;
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getAboutList("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json",mPostId, mTopN,zyId);
        return call;
    }

    @Override
    protected void getResult(JSONObject object) {
        if (object.optBoolean("success")) {
            ArrayList<InformationBean> mDatas = new ArrayList<>();
            Gson gson = new Gson();
            JSONArray array = object.optJSONArray("result");
            if (null != array) {
                for (int i = 0; i < array.length(); i++) {
                    InformationBean bean = gson.fromJson(array.optJSONObject(i).toString(), InformationBean.class);
                    mDatas.add(bean);
                }
            }
            if(null != onResultListener){
                onResultListener.onResultListener(mDatas);
            }
        }
    }
    @Override
    public void release() {
        call.cancel();
    }
}
