package com.zhangku.qukandian.base;

import android.content.Context;
import android.text.TextUtils;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.bean.BaseBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.utils.CommonUtil;
import com.zhangku.qukandian.utils.GsonUtil;
import com.zhangku.qukandian.utils.LogUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import java.lang.reflect.ParameterizedType;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by xuzhida
 */
public abstract class NewBaseProtocol<T> extends BaseModel<T> {

    public NewBaseProtocol(Context context, OnResultListener<T> onResultListener) {
        super(context, onResultListener);
    }

    protected abstract Call getMyCall();

    protected void getResult(T t) {
        if (onResultListener != null)
            onResultListener.onResultListener(t);
    }

    protected void getResult(T t, String messgae) {
        getResult(t);
    }

    protected void getErrorResult(int code, String errorStr) {
        if (onResultListener != null)
            onResultListener.onFailureListener(code, errorStr);
    }

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
                    String myBody = null;
                    boolean isRequestSuccess = false;
                    try {
                        String bo = null;
                        myBody = response.body();
                        LogUtils.LogW("myBody:" + myBody);
                        if (isAes) {
                            bo = CommonUtil.decrypt(myBody);
                        } else {
                            bo = myBody;
                        }
                        if (null == bo) {
                            getErrorResult(-10080, "当前网络不佳，请重试");
                            return;
                        }

                        Class<T> clazz = (Class<T>) ((ParameterizedType) NewBaseProtocol.this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                        BaseBean<T> bean = GsonUtil.fromJson(bo, clazz);

                        if (bean.isSuccess()) {
                            isRequestSuccess = true;
                            getResult(bean.getResult(), bean.getMessage());
                        } else if (bean.getError() != null) {
                            if (bean.getError().getCode() == 417
                                    || bean.getError().getCode() == 403
                                    && (bean.getError().getMessage().contains("Session") || bean.getError().getMessage().contains("must use user access_token for this request"))) {
                                logout();
                            }
                            getErrorResult(bean.getError().getCode(), bean.getError().getMessage());
                        } else {
                            getErrorResult(-10086, "当前网络不佳，请重试一下");
                        }
                    } catch (Exception e) {
                        if (isRequestSuccess) {//友盟bug
                            return;
                        }
                        if (mContext != null && e != null) {
//                            if (!TextUtils.isEmpty(myBody))
//                                try {
//                                    MobclickAgent.reportError(mContext, e.toString() + "  \n\n  " + call.request().url().toString() + "  \n\n  " + myBody);
//                                } catch (Exception a) {
//                                    MobclickAgent.reportError(mContext, e.toString() + " \n\n " + myBody);
//                                }
//                            else
//                                MobclickAgent.reportError(mContext, e);
                        }
                        getErrorResult(-10082, "当前网络不佳，请重试~");

                    }
                } else {
                    getErrorResult(-10083, "当前网络不佳，请重试~~");
                }
                call.cancel();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                getErrorResult(-10084, "当前网络不佳，请重试!");
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
