package com.zhangku.qukandian.retrofit;

import android.util.Log;

import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.protocol.interfaces.APIService;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by yuzuoning on 2017/5/7.
 */

public class RetrofitUtil {

    private APIService mAPIService;
    private static RetrofitUtil mRetrofitUtil = null;

    private static String mBaseUrl = Config.BASE_URL;

    private RetrofitUtil() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(mBaseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();
        mAPIService = retrofit.create(APIService.class);
    }

    public static RetrofitUtil getInstance() {
        if (mRetrofitUtil == null) {
            synchronized (RetrofitUtil.class) {
                if (mRetrofitUtil == null) {
                    mRetrofitUtil = new RetrofitUtil();
                }
            }
        }
        return mRetrofitUtil;
    }
    private static OkHttpClient okHttpClient=null;
    private OkHttpClient getOkHttpClient() {
        if (okHttpClient==null){
            okHttpClient = new OkHttpClient
                    .Builder()
                    .authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            return response.request().newBuilder().header("Authorization", "Bearer "
                                    + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU))
                                    .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64)")
                                    .build();

                        }
                    })
                    .addInterceptor(netCacheInterceptor)
                    .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                        @Override
                        public void log(String message) {
                            Log.e("authenticator",message+"/message");
                        }
                    }).setLevel(HttpLoggingInterceptor.Level.BODY)).build();
        }


        return okHttpClient;
    }
    public APIService getmAPIService() {
        return mAPIService;
    }
    public static String getmBaseUrl() {
        return mBaseUrl;
    }

    public static void setmBaseUrl(String mBaseUrl) {
        RetrofitUtil.mBaseUrl = mBaseUrl;
    }

    public static final Interceptor netCacheInterceptor = new Interceptor() {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();
            HttpUrl httpUrl = request.url()
                    .newBuilder()
                    // add common parameter
                    .build();
            Request build = request.newBuilder()
                    // add common header
                    .addHeader("appflag", "qksj")
                    .url(httpUrl)
                    .build();
            Response response = chain.proceed(build);
            return response;
        }
    };

}
