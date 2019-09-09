package com.zhangku.qukandian.protocol;

import android.content.Context;
import android.view.View;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogConfirm;
import com.zhangku.qukandian.dialog.DialogWithdrawals;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.LogUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yuzuoning on 2017/4/14.
 */

public class PutGiftProtocol extends BaseModel {
    private DialogWithdrawals mDialogWithdrawals;
    public PutGiftProtocol(Context context, OnResultListener<Boolean> onResultListener) {
        super(context, onResultListener);
        mDialogWithdrawals = new DialogWithdrawals(context,null);
    }

    public void putGift(int id) {
        call = getAPIService().putGift("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json",id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (null != response.body()) {
                    JSONObject object = null;
                    try {
                        object = new JSONObject(response.body());
                        if (object.optBoolean(mSuccess)) {
                            new DialogConfirm(mContext)
                                    .setTitles(R.string.checking_for_money)
                                    .setMessage(R.string.checking_for_money_three_day)
                                    .setYesBtnText(R.string.goto_task_btn)
                                    .setListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            MobclickAgent.onEvent(mContext, "04_07_06_clicktaskbtn2");
                                            ActivityUtils.startToMainActivity(mContext, 2, 0);
                                        }
                                    })
                                    .show();
                        }else {
                            JSONObject object1 = object.optJSONObject(mError);
                            mDialogWithdrawals.show();
                            mDialogWithdrawals.setId(-1);
                            mDialogWithdrawals.setTitles(object1.optString(mMessage)+"");
                            mDialogWithdrawals.setBtnText("知道了");
                        }
                        if(onResultListener != null){
                            onResultListener.onResultListener(object.optBoolean(mSuccess));
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
