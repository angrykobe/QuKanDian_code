package com.zhangku.qukandian.protocol;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.utils.MachineInfoUtil;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yuzuoning on 2017/9/21.
 */

public class GetVerificationCodeProtocol extends BaseModel {

    private Call<ResponseBody> mMCall;

    public GetVerificationCodeProtocol(Context context, OnResultListener<Bitmap> onResultListener) {
        super(context, onResultListener);
    }

    public void postRequest() {
        if (UserSharedPreferences.getInstance().getLong(Constants.TOKEN_RECORD_IN) != 0 &&
                System.currentTimeMillis() - UserSharedPreferences.getInstance().getLong(Constants.TOKEN_RECORD_IN)
                        > UserSharedPreferences.getInstance().getLong(Constants.TOKEN_EXPIRES_IN)) {
            UserManager.getInst().logout(mContext);
        }
        mMCall = getAPIService().getVerificationCodeImg(mAuthorization, mContentType, MachineInfoUtil.getInstance().getIMEI(mContext));
        mMCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if(null != response.body()){
                        ResponseBody body = response.body();
                        InputStream inputStream = null;

                        inputStream = body.byteStream();

                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        if (null != onResultListener) {
                            onResultListener.onResultListener(bitmap);
                        }
                        if (null != inputStream) {
                            inputStream.close();
                        }
                        mMCall.cancel();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (null != onResultListener) {
                    onResultListener.onFailureListener(0, "");
                }
                mMCall.cancel();
            }
        });
    }

    @Override
    protected void release() {

    }
}
