package com.zhangku.qukandian.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.observer.NetOberver;
import com.zhangku.qukandian.protocol.GetTestMissionProtocol;
import com.zhangku.qukandian.protocol.QukandianNewProtocol;

/**
 * Created by yuzuoning on 2016/5/18.
 * 监听网络变化
 */
public class NetTypeBrocoadcastReceiver extends BroadcastReceiver {
    private ConnectivityManager mConnectivityManager;
    private NetworkInfo netInfo;
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            mConnectivityManager = (ConnectivityManager) QuKanDianApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            netInfo = mConnectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isAvailable()) {//有网络
                if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    /////WiFi网络
                    NetOberver.getInstance().notityNetChange(true, true);
                } else {
                    NetOberver.getInstance().notityNetChange(true, false);
                }
                QuKanDianApplication.getCityBeanFirst(null);
            } else {
                ////////网络断开
                NetOberver.getInstance().notityNetChange(false, false);
            }
        }
    }

    private void requestSettting(Context context){
        if(context == null) return;
        new QukandianNewProtocol(context, null).postRequest();
        // ??  一些广告红包规则
//        new GetRuleProtocol(context, new BaseModel.OnResultListener<RuleBean>() {
//            @Override
//            public void onResultListener(RuleBean response) {
//            }
//            @Override
//            public void onFailureListener(int code, String error) {
//            }
//        }).postRequest();
        //获取测试任务
        new GetTestMissionProtocol(context, new BaseModel.OnResultListener() {
            @Override
            public void onResultListener(Object response) {
            }
            @Override
            public void onFailureListener(int code, String error) {
            }
        }).postRequest();
    }
}
