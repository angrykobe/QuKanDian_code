package com.zhangku.qukandian.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import java.io.File;

/**
 * apk包安装和移除广播
 *
 * @author
 */

public class InstallAPKReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
            File file = new File(UserSharedPreferences.getInstance().getString(Constants.PATH,""));
            if (file.exists()) {
                if(file.delete()){
                    UserSharedPreferences.getInstance().putString(Constants.PATH,"");
                }
            }
        }
    }
}
