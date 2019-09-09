package com.zhangku.qukandian.protocol;

import android.content.Context;
import android.util.Base64;

import com.zhangku.qukandian.network.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yuzuoning on 2017/11/20.
 */

public class DecodeVideoJRTTProtocol extends BaseModel {
    public DecodeVideoJRTTProtocol(Context context, OnResultListener<String> onResultListener) {
        super(context, onResultListener);
    }

    public void getVideoUrl(String url) {
        call = getAPIService().getVideoUrl(url);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject object = new JSONObject(response.body());
                    JSONObject data = object.optJSONObject("data");
                    String url = null;
                    if (null != data) {
                        JSONObject video_list = data.optJSONObject("video_list");
                        if (null != video_list.optJSONObject("video_3")) {
                            url = video_list.optJSONObject("video_3").optString("main_url");
                        } else if (null != video_list.optJSONObject("video_2")) {
                            url = video_list.optJSONObject("video_2").optString("main_url");
                        } else if (null != video_list.optJSONObject("video_1")) {
                            url = video_list.optJSONObject("video_1").optString("main_url");
                        }
                        if (url == null && null != onResultListener) {
                            onResultListener.onFailureListener(0, "字符串为空");
                            return;
                        }
                        url = new String(Base64.decode(url, Base64.DEFAULT));
                    }
                    if (null != onResultListener) {
                        onResultListener.onResultListener(url);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (null != onResultListener) {
                        onResultListener.onFailureListener(0, e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (null != onResultListener) {
                    onResultListener.onFailureListener(0, t.toString());
                }
            }
        });
    }

    @Override
    protected void release() {

    }


}
