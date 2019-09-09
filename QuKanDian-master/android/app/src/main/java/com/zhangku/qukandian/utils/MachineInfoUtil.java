package com.zhangku.qukandian.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class MachineInfoUtil {

    /**
     * 手机信息管理实例
     */
    private static MachineInfoUtil mInst = null;

    /**
     * IMEI（国际移动设备唯一识别符）
     */
    private String IMEI = "0000000000000000";

    /**
     * IMSI（国际移动注册用户认证号码）
     */
    public String IMSI = "";

    /**
     * 生产商，如HTC
     */
    public String MANUFACTURER = "";

    /**
     * 型号别名
     */
    public String MODEL = "";

    /**
     * 设备类型，如passion
     */
    public String DEVICE_TYPE = "";

    /**
     * SDK 版本号
     */
    public int SDK_VERSION = 3;
    /**
     * SDK 版本号
     */
    public String SDK_;

    /**
     * 像素密度
     */
    public int DENSITY = 160;

    /**
     * 网络连接类型
     */
    public String CONNECT_TYPE = "";

    /**
     * 硬件
     */
    public String HARDWARE = "default";

    //ro.build.display.id
    public String DISPLAY_ID = "unknown";

    //4.2.2
    public String SDK_VERSION_STR = "unknown";

    public String BUILD_BOARD = "unknown";

    /**
     * 分辨率
     */
    public String RESOLUTION = "";

    /**
     * 版本号
     */
    public int SSH_CLIENT_VERSION_CODE = 0;

    /**
     * CPU平台
     */
    public String CPU_PLATFORM = "";

    public String macAddress;

    private MachineInfoUtil() {
        initInfo();
    }

    private void initInfo() {
        int connectType = CommonHelper.getConnectType(QuKanDianApplication.getAppContext());
        if (connectType == ConnectivityManager.TYPE_WIFI) {
            CONNECT_TYPE = "wifi";
        } else {
            CONNECT_TYPE = "2g/3g";
        }
        SSH_CLIENT_VERSION_CODE = getVersionCode(QuKanDianApplication.getAppContext());
        CPU_PLATFORM = checkString(android.os.Build.CPU_ABI);
        RESOLUTION = checkString(getResolution());
        getOtherInfo();

        macAddress = checkString(getMacAddress());
    }

    public String checkString(String str) {
        if (TextUtils.isEmpty(str)) return "null";
        return str;
    }

    private void getOtherInfo() {
        try {
            DENSITY = QuKanDianApplication.getAppContext().getResources().getDisplayMetrics().densityDpi;
            SDK_VERSION = android.os.Build.VERSION.SDK_INT;
            SDK_ = android.os.Build.VERSION.RELEASE;//系统版本
            checkString(SDK_);

            MANUFACTURER = checkString(android.os.Build.MANUFACTURER);
            MODEL = checkString(android.os.Build.MODEL);
            DEVICE_TYPE = checkString(android.os.Build.DEVICE);
            HARDWARE = checkString(android.os.Build.HARDWARE);
            DISPLAY_ID = checkString(android.os.Build.DISPLAY);
            SDK_VERSION_STR = checkString(android.os.Build.VERSION.RELEASE);
            BUILD_BOARD = checkString(checkString(android.os.Build.BOARD));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //    public String getIMEI(Context context) {
//        String imei = "";
//        try {
//            TelephonyManager tm = (TelephonyManager) context
//                    .getSystemService(Context.TELEPHONY_SERVICE);
//            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                return "";
//            }
//            imei = tm.getDeviceId();
//            if (TextUtils.isEmpty(imei)) {
//                imei = tm.getSimSerialNumber();
//            }
//        } catch (Exception e) {
//        }
//        if (TextUtils.isEmpty(imei)) {
//            UserSharedPreferences.getInstance().putString(Constants.ONLY_MARK, imei);
//        }
//        return imei;
//    }
//    device_id == null || isEmpty(device_id) || "0".equals(device_id) || "00000000000000".equals(device_id) || "000000000000000".equals(device_id)
    public String getIMEI(Context context) {
        String imei = "";
        try {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                if (TextUtils.isEmpty(imei)) {
                    imei = getIMEI(context, 0);
                }

                if (TextUtils.isEmpty(imei)) {
                    imei = getIMEI(context, 1);
                }
                if (TextUtils.isEmpty(imei)) {
                    imei = tm.getDeviceId();//获取IMEI
                }
                if (TextUtils.isEmpty(imei) || "0".equals(imei) || "00000000000000".equals(imei) || "000000000000000".equals(imei)) {
                    imei = tm.getSimSerialNumber();//获取SIM ID – 手机SIM卡唯一标识
                }
            }

            if (!TextUtils.isEmpty(imei)) {
                UserSharedPreferences.getInstance().putString(Constants.ONLY_MARK, imei);
            }

            if (TextUtils.isEmpty(imei)) {
                imei = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }

            if (TextUtils.isEmpty(imei)) {
                imei = UserSharedPreferences.getInstance().getString(Constants.ONLY_MARK, "");
            }

        } catch (Exception e) {
        }
        return imei;
    }

    private static String getIMEI(Context context, int slotId) {
        try {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Method method = manager.getClass().getMethod("getImei", int.class);
            String imei = (String) method.invoke(manager, slotId);
            return imei;
        } catch (Exception e) {
            return "";
        }
    }

    private String getIMSI() {
        TelephonyManager tm = (TelephonyManager) QuKanDianApplication.getAppContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(QuKanDianApplication.getAppContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "";
        }
        String imsi = tm.getSubscriberId();
        if (TextUtils.isEmpty(imsi)) {
            imsi = "";
        }
        return imsi;
    }

    public static MachineInfoUtil getInstance() {
        if (null == mInst) {
            mInst = new MachineInfoUtil();
        }
        return mInst;
    }

    private String getResolution() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager mgr = (WindowManager) QuKanDianApplication.getAppContext().getSystemService(Context.WINDOW_SERVICE);
        mgr.getDefaultDisplay().getMetrics(dm);
        return "" + dm.heightPixels + "x" + dm.widthPixels;
    }


    /**
     * 获取当前软件版本号
     *
     * @param context
     * @return
     * @author xxj
     * @time 2012-2-28 | 下午02:13:52
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = pi.versionCode;
        } catch (NameNotFoundException e) {
        }
        return versionCode;
    }

    private String getMacAddress() {
        @SuppressLint("WifiManagerLeak") WifiManager wifiManager = (WifiManager) QuKanDianApplication.getAppContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo();
        if (info != null) {
            return info.getMacAddress();
        }
        return null;
    }

    //获取手机的ip地址
    public String getIpAddress(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (null == networkInfo) {
            return "0";
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            try {
                return int2Ip(wifiInfo.getIpAddress());
            } catch (Exception e) {
                return "0";
            }
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return getPhoneIp();
        }
        return "0";
    }

    private static String int2Ip(int i) {
        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }

    private static String getPhoneIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
        }
        return "";
    }

}
