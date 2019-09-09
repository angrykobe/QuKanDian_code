package com.zhangku.qukandian.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.bugly.crashreport.CrashReport;
import com.zhangku.qukandian.BuildConfig;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.manager.UserManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

public class  CommonUtil{
    /**
     * 判断某个Activity 界面是否在前台
     * @param context
     * @param className 某个界面名称
     * @return
     */
    public static boolean  isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }

        return false;

    }

    public static void initBugly(Context context) {
        //在开发测试阶段，可以在初始化Bugly之前通过以下接口把调试设备设置成“开发设备”。
        CrashReport.setIsDevelopmentDevice(context, BuildConfig.DEBUG);
        String imei = MachineInfoUtil.getInstance().getIMEI(context);
//        LogUtils.LogE(imei);
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = CommonUtil.getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        CrashReport.initCrashReport(context, "20476698f8", BuildConfig.DEBUG, strategy);
        // 设置用户ID要在CrashReport.initCrashReport之后才能生效
        CrashReport.setUserId(imei);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    public static String encrypt(String str) {
        return AES.encrypt(CommonHelper.md5(UserManager.dataKey + Config.DATAKEY_SALT), str);
    }
    public static String decrypt(String str) {
        return AES.decrypt(CommonHelper.md5(UserManager.dataKey + Config.DATAKEY_SALT), str);
    }
    public static String GetLocalIP() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("getip", ex.toString());
        }
        return "";
    }
}