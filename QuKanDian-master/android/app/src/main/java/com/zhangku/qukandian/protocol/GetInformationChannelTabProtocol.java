package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.ChannelBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/3/27.
 */

public class GetInformationChannelTabProtocol extends BaseProtocol {
    private int mType;
    private int version;

    public GetInformationChannelTabProtocol(Context context, int type,int version, OnResultListener<ArrayList<ChannelBean>> onResultListener) {
        super(context, onResultListener);
        mType = type;
        this.version = version;
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getTabList("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json",mType,version);
        return call;
    }

    @Override
    protected void getResult(JSONObject object) {
        ArrayList<ChannelBean> mTabBeans = new ArrayList<>();
        if (object.optBoolean("success")) {
            JSONArray json = object.optJSONArray("result");
            if (null != json) {
                for (int i = 0; i < json.length(); i++) {
                    JSONObject object1 = json.optJSONObject(i).optJSONObject("channel");
                    ChannelBean tabBean = new ChannelBean();
                    tabBean.setYesNo(json.optJSONObject(i).optBoolean("yesNo"));
                    tabBean.setOrderId(json.optJSONObject(i).optInt("orderId"));
                    tabBean.setKindType(mType);
                    tabBean.setId(object1.optInt("id"));
                    tabBean.setChannelId(object1.optInt("id"));
                    tabBean.setDisplayName(object1.optString("displayName"));
                    tabBean.setName(object1.optString("name"));
                    mTabBeans.add(tabBean);
                }
            }

            if (null != onResultListener) {
                onResultListener.onResultListener(mTabBeans);
            }
        }
    }

    @Override
    public void release() {
        if(null != call){
            call.cancel();
        }
    }
}
