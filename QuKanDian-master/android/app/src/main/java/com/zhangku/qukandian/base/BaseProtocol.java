package com.zhangku.qukandian.base;

import android.content.Context;

import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.observer.KickedOutObserver;
import com.zhangku.qukandian.protocol.GetTokenProtocol;
import com.zhangku.qukandian.utils.CommonUtil;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yuzuoning on 2017/5/25.
 */
@Deprecated
public abstract class BaseProtocol<T> extends BaseModel {
    private GetTokenProtocol mGetTokenProtocol;

    public BaseProtocol(Context context, OnResultListener<T> onResultListener) {
        super(context, onResultListener);
    }

    protected abstract Call getMyCall();

    protected abstract void getResult(JSONObject object);

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
                        JSONObject object = new JSONObject(bo);
                        if (object.optBoolean(mSuccess)) {
                            if (null != onResultListener) {
                                getResult(object);
                            }
                        } else {
                            if (null != onResultListener) {
                                onResultListener.onFailureListener(object.optJSONObject(mError).optInt("code"),object.optJSONObject(mError).optString(mMessage));
                            }
                            if (object.optJSONObject(mError).optInt("code") == 417
                                    || (object.optJSONObject(mError).optInt("code") == 403
                                    && (object.optJSONObject(mError).optString(mMessage).contains("Session")||object.optJSONObject(mError).optString(mMessage).contains("must use user access_token for this request")))) {
                                logout();
                            }
                        }
                    } catch (JSONException e) {
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

    public void verification() {
        if (UserManager.getInst().hadLogin()) {
            if (null == mGetTokenProtocol) {
                mGetTokenProtocol = new GetTokenProtocol(mContext, new BaseModel.OnResultListener<Boolean>() {
                    @Override
                    public void onResultListener(Boolean response) {
                        mGetTokenProtocol = null;
                    }

                    @Override
                    public void onFailureListener(int code,String error) {
                        mGetTokenProtocol = null;
                    }
                });
                mGetTokenProtocol.getRefreshToken();
            }
        } else {
            if (null == mGetTokenProtocol) {
                mGetTokenProtocol = new GetTokenProtocol(mContext, new BaseModel.OnResultListener<Boolean>() {
                    @Override
                    public void onResultListener(Boolean response) {
                        mGetTokenProtocol = null;
                    }

                    @Override
                    public void onFailureListener(int code,String error) {
                        mGetTokenProtocol = null;
                    }
                });
                mGetTokenProtocol.getClientToken();
            }
        }
    }
}
