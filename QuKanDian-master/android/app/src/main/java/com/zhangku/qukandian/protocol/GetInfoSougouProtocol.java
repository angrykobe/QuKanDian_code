package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.blankj.utilcode.util.TimeUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.base.BaseProtocolSougou;
import com.zhangku.qukandian.bean.SougouHotwordBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.protocol.interfaces.APIService;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Authenticator;
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
//域名地址 不同  特殊处理
public class GetInfoSougouProtocol extends BaseProtocolSougou {

    public GetInfoSougouProtocol(Context context,  OnResultListener<ArrayList<SougouHotwordBean>> onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService2().getSougouHotword("Bearer","application/json", TimeUtils.getNowMills());
        return call;
    }

    protected APIService getAPIService2() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://ts.mobile.sogou.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();
        return retrofit.create(APIService.class);
    }

    @Override
    protected void getResult(JsonArray object) {
        ArrayList<SougouHotwordBean> mTabBeans = new ArrayList<>();
        Gson gson = new Gson();
        //循环遍历
        for (JsonElement user : object) {
            SougouHotwordBean userBean = gson.fromJson(user, new TypeToken<SougouHotwordBean>() {}.getType());
            mTabBeans.add(userBean);
        }
        if (null != onResultListener) {
            onResultListener.onResultListener(mTabBeans);
        }
    }

    @Override
    public void release() {
        if(null != call){
            call.cancel();
        }
    }

    private OkHttpClient getOkHttpClient() {

        OkHttpClient httpClientBuilder = new OkHttpClient
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
                        return response.request().newBuilder().header("Authorization", "Bearer "
                                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU))
                                .build();
                    }
                })
                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        if(message.equals("enc: enc")){
                            isAes = true;
                        }
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY)).build();
        return httpClientBuilder;
    }
}
