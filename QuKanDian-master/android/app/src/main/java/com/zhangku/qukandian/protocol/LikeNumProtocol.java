package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.DisLikeNumBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Query;

/**
 * Created by yuzuoning on 2017/4/1.
 * 获取新闻详情 不喜欢个数 ， 状态
 */

public class LikeNumProtocol extends BaseProtocol {
    private int postId;
    private String zyId;

    public LikeNumProtocol(Context context, int postId, @Query("zyId") String zyId, OnResultListener<DisLikeNumBean> onResultListener) {
        super(context,onResultListener);
        this.postId = postId;
        this.zyId = zyId;
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().singlePost("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json", UserManager.getInst().getUserBeam().getId(),postId,zyId);
        return call;
    }

    @Override
    protected void getResult(JSONObject object) {
        if(object.optBoolean("success")){//{"success":true,"result":{"isLike":false,"isDislike":false,"likeNumber":0}}
            Gson gson = new Gson();
            if(object.optJSONObject("result") != null){
                DisLikeNumBean disLikeBean = gson.fromJson(object.optJSONObject("result").toString(),DisLikeNumBean.class);
                if(null != onResultListener&&null != disLikeBean){
                    onResultListener.onResultListener(disLikeBean);
                }
            }
        }
    }
    @Override
    public void release() {
        call.cancel();
    }
}
