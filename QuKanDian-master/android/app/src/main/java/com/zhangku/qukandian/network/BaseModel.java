package com.zhangku.qukandian.network;

import android.content.Context;

import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.protocol.interfaces.APIService;
import com.zhangku.qukandian.retrofit.RetrofitUtil;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Authenticator;
import okhttp3.ConnectionPool;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * Created by yuzuoning on 2017/3/25.
 */

public abstract class BaseModel<T> {
    protected boolean isAes = false;
    protected Context mContext;
    public static final String mSuccess = "success";
    public static final String mMessage = "message";
    public static final String mResult = "result";
    public static final String mError = "error";
    protected OnResultListener onResultListener;
    protected Call<String> call;
    protected String mAuthorization = "Bearer "
            + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU);

    protected int time = CommonHelper.getCheckTimeStamp(QuKanDianApplication.mTimeOffset);
    protected String rand = CommonHelper.getStringRandom(32);
    protected String key = "323311dfe86a4c9eba0d2a2820ecab47";
    protected String appid = "9ebE0L2";

    protected String mContentType = "application/json";
    public BaseModel(Context context, OnResultListener onResultListener) {
        mContext = context;
        if(onResultListener==null){
            this.onResultListener = new OnResultListener() {
                @Override
                public void onResultListener(Object response) {

                }

                @Override
                public void onFailureListener(int code, String error) {

                }
            };
        } else{
            this.onResultListener = onResultListener;
        }
    }

    public BaseModel(Context context) {
        mContext = context;
    }


    public Context getContext() {
        return mContext;
    }

    public String getServerUrl() {
        return Config.BASE_URL;
    }

    protected APIService getAPIService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(getServerUrl())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();
        return retrofit.create(APIService.class);
    }

    protected abstract void release();

    public interface OnResultListener<T> {
        void onResultListener(T response);

        void onFailureListener(int code,String error);
    }
    private static OkHttpClient okHttpClient=null;
    private OkHttpClient getOkHttpClient() {
        if (okHttpClient==null){
            okHttpClient= new OkHttpClient
                    .Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
//                .connectionPool(new ConnectionPool(5,10,TimeUnit.SECONDS))
                    .sslSocketFactory(createSSLSocketFactory())
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    })
                    .authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            try{
                                UserManager.getInst().logout(mContext);
                            }catch (IllegalStateException e){
                            }
                            Request request = response.request().newBuilder()
                                    .addHeader("Authorization", "Bearer "
                                            + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU))
                                    .addHeader("appflag","qksj")
                                    .build();
                            return request;
                        }
                    })
                    .addInterceptor(RetrofitUtil.netCacheInterceptor)
                    .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                        @Override
                        public void log(String message) {
                            if(message.equals("enc: enc")){
                                isAes = true;
                            }
                        }
                    }).setLevel(HttpLoggingInterceptor.Level.BODY)).build();
        }

        return okHttpClient;
    }

    public static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    public static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }

}


