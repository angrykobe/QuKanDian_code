package com.zhangku.qukandian.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.protocol.PutBehaviorProtocol;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

/**
 * Created by yuzuoning on 2017/6/26.
 */

public class BatteryReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //判断是否是电池变化广播  
        if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
            int level = intent.getIntExtra("level", 0); //获取当前电量    
            int scale = intent.getIntExtra("scale", 100); //电量的总刻度 
            int battery = level * 100/ scale;
            if (UserManager.getInst().hadLogin()) {
                int last = UserSharedPreferences.getInstance().getInt(Constants.LAST_BATTERY);
                if (last >= 15) {
                    new PutBehaviorProtocol(context, UserManager.getInst().getUserBeam().getId(), 1, 1, null).postRequest();
                    UserSharedPreferences.getInstance().putInt(Constants.LAST_BATTERY, 0);
                } else {
                    if (battery >= 98) {
                        last++;
                        UserSharedPreferences.getInstance().putInt(Constants.LAST_BATTERY, last);
                    }else{
                        UserSharedPreferences.getInstance().putInt(Constants.LAST_BATTERY, 0);
                    }
                }
            }
        }
    }
}
