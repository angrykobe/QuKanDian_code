package com.zhangku.qukandian.protocol;

import android.content.Context;
import android.util.Log;

import com.zhangku.qukandian.network.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yuzuoning on 2017/11/21.
 */

public class DecodeVideoTencentProtocol extends BaseModel {
    public DecodeVideoTencentProtocol(Context context, OnResultListener<String> onResultListener) {
        super(context, onResultListener);
    }

    public void getUrl(String vid) {
        String analysisUrl = "http://sec.video.qq.com/p/h5vv.video/getinfo?callback=v&otype=json&vids=" + vid + "&platform=11001";
        call = getAPIService().getVideoUrl(analysisUrl);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    String result = response.body().toString().trim();
                    result = result.substring(2, result.length() - 1);
                    JSONObject object = new JSONObject(result);

                    String url = null;
                    JSONObject vl = object.optJSONObject("vl");
                    JSONObject ui = null;
                    JSONObject vis = null;
                    if (null != vl) {
                        if (null != vl.optJSONArray("vi") && vl.optJSONArray("vi").length() > 0) {
                            String vi = vl.optJSONArray("vi").get(0).toString();
                            JSONObject object1 = new JSONObject(vi);
                            String ui1 = object1.optJSONObject("ul").optJSONArray("ui").get(0).toString();
                            ui = new JSONObject(ui1);
                        }
                        if (null != vl.optJSONArray("vi") && vl.optJSONArray("vi").length() > 0) {
                            String vi = vl.optJSONArray("vi").get(0).toString();
                            vis = new JSONObject(vi);
                        }
                        url = ui.optString("url")+vis.optString("fn") +"?vkey="+vis.optString("fvkey");
                    }

                    if(null != onResultListener){
                        onResultListener.onResultListener(url);
                    }
                    Log.e("getUrl", response.body().toString());
                    Log.e("getUrl", "dd/" + result);
                    Log.e("getUrl", "dd//" + url);
                } catch (JSONException e) {
                    e.printStackTrace();
                    if(null != onResultListener){
                        onResultListener.onFailureListener(10,"");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if(null != onResultListener){
                    onResultListener.onFailureListener(10,"");
                }
            }
        });
    }

    @Override
    protected void release() {

    }
}
