package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yuzuoning on 2017/3/25.
 */

public class UpPackageProtocol extends BaseModel {
    private Context mContext;
    private String mContent;

    public UpPackageProtocol(Context context, String content) {
        super(context);
        mContext = context;
        mContent = content;
    }

    public void postRequest() {
        call = getAPIService().upPackage(mAuthorization, mContentType, mContent);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (null != response.body()) {
                    try {
                        String message = "";
                        JSONObject object = new JSONObject(response.body());
                        if (object.optBoolean(mSuccess)) {
                            message = object.optString(mMessage);
                            // 上传竞品数据成功
                            UserSharedPreferences.getInstance().putBoolean(Constants.UP_PACEAGE_OK, true);
                        } else {
                            message = object.optJSONObject(mError).optString(mMessage);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    @Override
    protected void release() {
        call.cancel();
    }
}
