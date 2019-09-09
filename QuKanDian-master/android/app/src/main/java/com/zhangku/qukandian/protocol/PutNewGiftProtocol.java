package com.zhangku.qukandian.protocol;

import android.content.Context;
import android.view.View;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.WithdrawalsBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogConfirm;
import com.zhangku.qukandian.dialog.DialogWithdrawals;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yuzuoning on 2017/4/14.
 */

public class PutNewGiftProtocol extends NewBaseProtocol<WithdrawalsBean> {

    private int id;

    public PutNewGiftProtocol(Context context, int id,OnResultListener<WithdrawalsBean> onResultListener) {
        super(context, onResultListener);
        this.id = id;
    }

    @Override
    protected Call getMyCall() {
        return call = getAPIService().putGift("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json",id);
    }
}
