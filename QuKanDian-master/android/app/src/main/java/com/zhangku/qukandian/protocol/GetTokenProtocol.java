package com.zhangku.qukandian.protocol;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.utils.MachineInfoUtil;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yuzuoning on 2017/3/25.
 */

public class GetTokenProtocol extends BaseModel {
    private Context mContext;

    public GetTokenProtocol(Context context, OnResultListener<Boolean> onResultListener) {
        super(context, onResultListener);
        mContext = context;
    }

    @Override
    protected void release() {
        if (call != null) {
            call.cancel();
        }
    }

    public void getClientToken() {
        String str = "grant_type=client_credentials";
        RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded")
                , str);
        call = getAPIService()
                .getClientGredentials("Basic " + Config.AU
                        , "App/android Qukandian/" + QuKanDianApplication.getCode()
                                + " Session/" + MachineInfoUtil.getInstance().getIMEI(mContext)
                        , body);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (null != response.body()) {
                    try {
                        JSONObject object = new JSONObject(response.body());
                        UserSharedPreferences.getInstance().putLong(Constants.TOKEN_RECORD_IN, System.currentTimeMillis())
                                .putString(Constants.TOKEN, object.optString("access_token"))
                                .putString(Constants.TOKEN_TYPE, object.optString("token_type"))
                                .putString(Constants.REFRESH_TOKEN, object.optString("refresh_token"))
                                .putLong(Constants.TOKEN_EXPIRES_IN, object.optLong("expires_in") * 1000);
                        if (null != onResultListener) {
                            onResultListener.onResultListener(true);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (null != onResultListener) {
                    onResultListener.onResultListener(false);
                }
            }
        });
    }

    public void getUserToken(final String userName, final String psd) {
        String str = "grant_type=password&username=" + userName + "&password=" + psd;
        RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded")
                , str);
        getAPIService()
                .getClientGredentials("Basic " + Config.AU
                        , "App/android Qukandian/" + QuKanDianApplication.getCode() +
                                " Session/" + MachineInfoUtil.getInstance().getIMEI(mContext)
                        , body).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (null != response.body()) {
                    JSONObject object = null;
                    try {
                        object = new JSONObject(response.body());
                        UserSharedPreferences.getInstance()
                                .putString(Constants.TOKEN, object.optString("access_token"))
                                .putString(Constants.REFRESH_TOKEN, object.optString("refresh_token"))
                                .putLong(Constants.TOKEN_RECORD_IN, System.currentTimeMillis())
                                .putLong(Constants.TOKEN_EXPIRES_IN, object.optLong("expires_in") * 1000);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (null != onResultListener) {
                    onResultListener.onResultListener(true);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (null != onResultListener) {
                    onResultListener.onResultListener(false);
                }
            }
        });
    }

    public void getUserTokenWX(final String userName, final String psd) {
        String str = "grant_type=password&username=" + userName + "&password=" + psd;
        RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded")
                , str);
        getAPIService()
                .getClientGredentialsWX("Basic " + Config.AU
                        , "App/android Qukandian/" + QuKanDianApplication.getCode() + " Session/" + MachineInfoUtil.getInstance().getIMEI(mContext)
                        , body).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (null != response.body()) {
                    JSONObject object = null;
                    try {
                        object = new JSONObject(response.body());
                        UserSharedPreferences.getInstance()
                                .putString(Constants.TOKEN, object.optString("access_token"))
                                .putString(Constants.REFRESH_TOKEN, object.optString("refresh_token"))
                                .putLong(Constants.TOKEN_RECORD_IN, System.currentTimeMillis())
                                .putLong(Constants.TOKEN_EXPIRES_IN, object.optLong("expires_in") * 1000);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (null != onResultListener) {
                    onResultListener.onResultListener(true);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (null != onResultListener) {
                    onResultListener.onResultListener(false);
                }
            }
        });

    }

    public void getRefreshToken() {
        if (!TextUtils.isEmpty(UserSharedPreferences.getInstance().getString(Constants.REFRESH_TOKEN, ""))) {
            String str = "grant_type=refresh_token&refresh_token=" + UserSharedPreferences.getInstance().getString(Constants.REFRESH_TOKEN, "");
            RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded")
                    , str);

            getAPIService()
                    .getClientGredentials("Basic " + UserSharedPreferences.getInstance().getString(Constants.REFRESH_TOKEN, Config.AU)
                            , "App/android Qukandian/" + QuKanDianApplication.getCode() + " Session/" + MachineInfoUtil.getInstance().getIMEI(mContext)
                            , body).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.e("body", response.body() + "");
                    if (null != response.body()) {
                        JSONObject object = null;
                        try {
                            object = new JSONObject(response.body());
                            UserSharedPreferences.getInstance().putLong(Constants.TOKEN_RECORD_IN, System.currentTimeMillis())
                                    .putString(Constants.TOKEN, object.optString("access_token"))
                                    .putString(Constants.TOKEN_TYPE, object.optString("token_type"))
                                    .putLong(Constants.TOKEN_EXPIRES_IN, object.optLong("expires_in") * 1000)
                                    .putString(Constants.REFRESH_TOKEN, object.optString("refresh_token"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (UserManager.getInst().hadLogin()) {
                            getUserToken(UserSharedPreferences.getInstance().getString(Constants.USERNAME, ""), UserSharedPreferences.getInstance().getString(Constants.PWD, ""));
                        } else {
                            getClientToken();
                        }
                    }
                    if (null != onResultListener) {
                        onResultListener.onResultListener(true);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    if (null != onResultListener) {
                        onResultListener.onResultListener(false);
                    }
                }
            });

        }
    }

    public void getRefreshTokenWX() {
        if (!TextUtils.isEmpty(UserSharedPreferences.getInstance().getString(Constants.REFRESH_TOKEN, ""))) {
            String str = "grant_type=refresh_token&refresh_token=" + UserSharedPreferences.getInstance().getString(Constants.REFRESH_TOKEN, "");
            RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded")
                    , str);

            getAPIService()
                    .getClientGredentialsWX("Basic " + UserSharedPreferences.getInstance().getString(Constants.REFRESH_TOKEN, Config.AU)
                            , "App/android Qukandian/" + QuKanDianApplication.getCode() + " Session/" + MachineInfoUtil.getInstance().getIMEI(mContext)
                            , body).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.e("body", response.body() + "");
                    if (null != response.body()) {
                        JSONObject object = null;
                        try {
                            object = new JSONObject(response.body());
                            UserSharedPreferences.getInstance().putLong(Constants.TOKEN_RECORD_IN, System.currentTimeMillis())
                                    .putString(Constants.TOKEN, object.optString("access_token"))
                                    .putString(Constants.TOKEN_TYPE, object.optString("token_type"))
                                    .putLong(Constants.TOKEN_EXPIRES_IN, object.optLong("expires_in") * 1000)
                                    .putString(Constants.REFRESH_TOKEN, object.optString("refresh_token"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (UserManager.getInst().hadLogin()) {
                            getUserToken(UserSharedPreferences.getInstance().getString(Constants.USERNAME, ""), UserSharedPreferences.getInstance().getString(Constants.PWD, ""));
                        } else {
                            getClientToken();
                        }
                    }
                    if (null != onResultListener) {
                        onResultListener.onResultListener(true);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    if (null != onResultListener) {
                        onResultListener.onResultListener(false);
                    }
                }
            });

        }
    }

    public void getUserTokenDynamic(final String userName, final String psd) {
        String str = "grant_type=password&username=" + userName + "&password=" + psd;
        RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded")
                , str);
        getAPIService()
                .getClientGredentialsDynamic("Basic " + Config.AU
                        , "App/android Qukandian/" + QuKanDianApplication.getCode() + " Session/" + MachineInfoUtil.getInstance().getIMEI(mContext)
                        , body ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (null != response.body()) {
                    JSONObject object = null;
                    try {
                        object = new JSONObject(response.body());
                        UserSharedPreferences.getInstance()
                                .putString(Constants.TOKEN, object.optString("access_token"))
                                .putString(Constants.REFRESH_TOKEN, object.optString("refresh_token"))
                                .putLong(Constants.TOKEN_RECORD_IN, System.currentTimeMillis())
                                .putLong(Constants.TOKEN_EXPIRES_IN, object.optLong("expires_in") * 1000);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (null != onResultListener) {
                    onResultListener.onResultListener(true);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (null != onResultListener) {
                    onResultListener.onResultListener(false);
                }
            }
        });
    }
}
