package com.zhangku.qukandian.base;

import android.content.Context;

import com.zhangku.qukandian.bean.BaseBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.utils.CommonUtil;
import com.zhangku.qukandian.utils.GsonUtil;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by xuzhida
 */
public abstract class NewBaseListProtocol<T> extends BaseModel<T> {

    public NewBaseListProtocol(Context context, OnResultListener<List<T>> onResultListener) {
        super(context, onResultListener);
    }

    protected abstract Call getMyCall();

    protected void getResult(List<T> list) {
        if (null != onResultListener)
            onResultListener.onResultListener(list);
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
                    try {
                        String bo = null;
                        String myBody = response.body();
                        if (isAes) {
                            bo = CommonUtil.decrypt(myBody);
                        } else {
                            bo = myBody;
                        }
                        if (null == bo) {
                            if (null != onResultListener)
                                onResultListener.onFailureListener(-10086, "当前网络不佳，请重试");
                            return;
                        }
                        BaseBean<List<T>> bean = GsonUtil.fromJsonArray(bo, (Class<T>) ((ParameterizedType) NewBaseListProtocol.this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
                        if (bean.isSuccess() && bean.getResult() != null) {
                            getResult(bean.getResult());
                        } else if (bean.getError() != null) {
                            if (bean.getError().getCode() == 417 || bean.getError().getCode() == 403 && (bean.getError().getMessage().contains("Session") || bean.getError().getMessage().contains("must use user access_token for this request"))) {
                                logout();
                            } else {
                                if (null != onResultListener)
                                    onResultListener.onFailureListener(bean.getError().getCode(), bean.getError().getMessage());
                            }
                        } else {
                            if (null != onResultListener)
                                onResultListener.onFailureListener(-10086, "当前网络不佳，请重试一下");
                        }
                    } catch (Exception e) {
                        if (null != onResultListener)
                            onResultListener.onFailureListener(-10086, "当前网络不佳，请重试~");
                    }
                } else {
                    if (null != onResultListener) {
                        onResultListener.onFailureListener(-10086, "当前网络不佳，请重试~~");
                    }
                }
                call.cancel();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (null != onResultListener)
                    onResultListener.onFailureListener(-10086, "当前网络不佳，请重试!");
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
