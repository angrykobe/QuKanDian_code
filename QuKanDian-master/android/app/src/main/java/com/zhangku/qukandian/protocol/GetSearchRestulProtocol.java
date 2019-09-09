package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.InformationBean;
import com.zhangku.qukandian.bean.SearchBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.LogUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/4/5.
 */

public class GetSearchRestulProtocol extends BaseProtocol {
    public SearchBean mSearchBean = null;
    private int mType;
    private String mKeyword;
    private int mSize;
    private int mPage;
    public GetSearchRestulProtocol(Context context, int type, String keyword, int size, int page,OnResultListener<ArrayList<InformationBean>> onResultListener) {
        super(context, onResultListener);
        mType = type;
        mKeyword = keyword;
        mSize = size;
        mPage = page;
    }
    @Override
    protected Call getMyCall() {
        call = getAPIService().getSearchRestul("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json",mType, mKeyword, mSize, mPage);
        return call;
    }

    @Override
    protected void getResult(JSONObject object) {
        if (object.optBoolean("success")) {
            Gson gson = new Gson();
            if(null != object.optJSONObject("paged")){
                LogUtils.LogE(object.optJSONObject("paged").toString());
                mSearchBean = gson.fromJson(object.optJSONObject("paged").toString(), SearchBean.class);
            }

            ArrayList<InformationBean> datas = new ArrayList<InformationBean>();
            JSONArray array = object.optJSONArray("result");
            if (null != array) {
                for (int i = 0; i < array.length(); i++) {
                    InformationBean bean = gson.fromJson(array.optJSONObject(i).toString(), InformationBean.class);
                    bean.setChannelId(bean.getChannel().getChannelId());
                    bean.setPostId(bean.getId());
                    datas.add(bean);
                }
                if (null != onResultListener) {
                    onResultListener.onResultListener(datas);
                }
            }
        }
    }
}
