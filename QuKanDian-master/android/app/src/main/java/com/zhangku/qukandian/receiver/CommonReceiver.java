package com.zhangku.qukandian.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zhangku.qukandian.bean.QkdPushBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.LogUtils;

import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;


public class CommonReceiver extends BroadcastReceiver {

    public static final String ACTION_RECEIVER_WAKE_UP_APP = "com.zhangku.qukandian.action.WAKE_UP_APP";


    public static final int ACTION_TYPE_1 = 1000;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            LogUtils.LogD("[CommonReceiver] onReceive - " + intent.getAction() + ", extras: ");

            if (intent.getAction().equals(ACTION_RECEIVER_WAKE_UP_APP)) {
                // 唤醒app
                LogUtils.LogE("--醒app");
                QkdPushBean qkdPushBean = (QkdPushBean) intent.getSerializableExtra(Constants.INTENT_DATA);
                LogUtils.LogE("---CommonReceiver="+ qkdPushBean.toString());
            }

        } catch (Exception e) {

        }

    }

}
