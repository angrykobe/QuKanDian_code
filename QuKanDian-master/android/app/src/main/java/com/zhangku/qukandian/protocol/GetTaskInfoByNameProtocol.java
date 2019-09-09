package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.TaskInfoBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/4/7.
 */

public class GetTaskInfoByNameProtocol extends BaseProtocol {
    private String mName;
    public GetTaskInfoByNameProtocol(Context context,String name, OnResultListener<List<TaskInfoBean>> onResultListener) {
        super(context, onResultListener);
        mName = name;
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getTaskInfoByName("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json",mName);
        return call;
    }

    @Override
    protected void getResult(JSONObject object) {
        if (null != object && object.optBoolean(mSuccess)) {
            ArrayList<TaskInfoBean> infoBeen = new ArrayList<>();
            Gson gson = new Gson();
            JSONObject object1 = object.optJSONObject(mResult);
            if(null != object1){
                JSONArray array = object1.optJSONArray("missionRules");
                if(array != null){
                    for (int i = 0; i < array.length(); i++) {
                        TaskInfoBean task = gson.fromJson(array.optJSONObject(i).toString(), TaskInfoBean.class);
                        infoBeen.add(task);
                    }
                    if (null != onResultListener) {
                        onResultListener.onResultListener(infoBeen);
                    }
                }
            }
        }
    }
}
