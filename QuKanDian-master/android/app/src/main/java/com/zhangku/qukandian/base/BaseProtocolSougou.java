package com.zhangku.qukandian.base;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetTokenProtocol;
import com.zhangku.qukandian.utils.CommonUtil;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 搜狗热词 特殊处理
 */
@Deprecated
public abstract  class BaseProtocolSougou <T> extends BaseModel {

    public BaseProtocolSougou(Context context, OnResultListener<T> onResultListener) {
        super(context, onResultListener);
    }

    protected abstract Call getMyCall();

    protected abstract void getResult(JsonArray object);

    public void postRequest() {
        if (UserSharedPreferences.getInstance().getLong(Constants.TOKEN_RECORD_IN) != 0 &&
                System.currentTimeMillis() - UserSharedPreferences.getInstance().getLong(Constants.TOKEN_RECORD_IN)
                        > UserSharedPreferences.getInstance().getLong(Constants.TOKEN_EXPIRES_IN)) {
            UserManager.getInst().logoutGotoLogin(mContext);
        }
        call = getMyCall();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (null != response.body()) {
                    try {
                        String bo = null;
                        String myBody = response.body();
                        if(isAes){
                            try {
                                bo = CommonUtil.decrypt(myBody);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else {
                            bo = myBody;
                        }
                        if (null == bo) return;
                        JsonParser parser = new JsonParser();
                        JsonElement tradeElement = parser.parse(bo);
                        JsonArray jsonArray = tradeElement.getAsJsonArray();
                        if (null != onResultListener) {
                            getResult(jsonArray);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (null != onResultListener) {
                        onResultListener.onFailureListener(0,"");
                    }
                }
                call.cancel();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (null != onResultListener) {
                    onResultListener.onFailureListener(0,"");
                }
                call.cancel();
            }
        });
    }

    protected void logout() {
//        KickedOutObserver.getInstance().notifys();
        UserManager.getInst().logoutGotoLogin(mContext);
    }


    public void release() {
        call.cancel();
    }


}
