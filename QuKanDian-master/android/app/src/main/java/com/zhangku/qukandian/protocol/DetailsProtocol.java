package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.DetailsBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONObject;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/4/1.
 * 新闻详情
 */

public class DetailsProtocol extends BaseProtocol {
    private int mId;
    public DetailsProtocol(Context context,int id,OnResultListener<DetailsBean> onResultListener) {
        super(context,onResultListener);
        mId = id;
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getInformationDetails("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json",mId,"",0);
        return call;
    }

    @Override
    protected void getResult(JSONObject object) {
        String ret = object.toString();
        if(object.optBoolean("success")){
            Gson gson = new Gson();
            if(object.optJSONObject("result") != null){
                DetailsBean mDetailsBean = gson.fromJson(object.optJSONObject("result").toString(),DetailsBean.class);
                mDetailsBean.setNewId(mDetailsBean.getId());
                if(null != onResultListener&&null != mDetailsBean){
                    onResultListener.onResultListener(mDetailsBean);
                }
            }else{

            }
        }
    }
    @Override
    public void release() {
        call.cancel();
    }
}
