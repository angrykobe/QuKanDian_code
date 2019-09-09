package com.zhangku.qukandian.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.webkit.WebView;

import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.DeviceInfoBeen;
import com.zhangku.qukandian.bean.NetworkBeen;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

import static android.content.Context.TELEPHONY_SERVICE;

public class DeviceUtil {

    //运营商类型，必填。1为移动，2为联通，3为电信, 未知为0
    public static final int OPERATOR_YIDONG = 1;
    public static final int OPERATOR_LIANTONG = 2;
    public static final int OPERATOR_DIANXIN = 3;
    public static final int OPERATOR_UNKNOWN = 0;

    //
    public static DeviceInfoBeen getDeviceInfo(Context context) {
        if (context == null) return null;
        try {
            TelephonyManager mTm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            DeviceInfoBeen deviceInfoBeen = new DeviceInfoBeen();
            deviceInfoBeen.imei = mTm.getDeviceId();
            deviceInfoBeen.imsi = mTm.getSubscriberId();
            // 手机型号
            deviceInfoBeen.model = android.os.Build.MODEL;
            // 手机品牌
            deviceInfoBeen.brand = android.os.Build.BRAND;
            // 手机号码，有的可得，有的不可得
            deviceInfoBeen.tel = mTm.getLine1Number();
            deviceInfoBeen.mac = getLocalMacAddressFromIp();
            deviceInfoBeen.android_id = getAndroidId(context);
            // 设备SDK版本
            deviceInfoBeen.version_sdk = Build.VERSION.SDK_INT;
            // 设备的系统版本
            deviceInfoBeen.version_release = android.os.Build.VERSION.RELEASE;
            deviceInfoBeen.user_agent = getUserAgent(context);
            int[] wh = getScreenSize(context);
            if (wh != null) {
                deviceInfoBeen.width = wh[0];
                deviceInfoBeen.heigth = wh[1];
            } else {
                deviceInfoBeen.width = 0;
                deviceInfoBeen.heigth = 0;
            }
            deviceInfoBeen.device_type = isTabletDevice(context) ? 0 : 1;
            return deviceInfoBeen;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static NetworkBeen getNetwork(Context context) {
        if (context == null) return null;
        try {
            TelephonyManager mTm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            NetworkBeen networkBeen = new NetworkBeen();
            InetAddress ip = getLocalInetAddress();
            if (ip != null) {
                networkBeen.ip = ip.getHostAddress();
            }
            int networkState = IntenetUtil.getNetworkState(context);
            networkBeen.connectionType = networkState;
            networkBeen.operatorType = getOperator(context);
            networkBeen.cellularId = getCellularId(context) + "";

            return networkBeen;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前的运营商。运营商类型，必填。1为移动，2为联通，3为电信, 未知为0
     *
     * @param context
     * @return 运营商名字
     */
    public static int getOperator(Context context) {
        int provider = OPERATOR_UNKNOWN;
        if (context == null) return provider;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return provider;
        }
        String IMSI = telephonyManager.getSubscriberId();
        if (IMSI != null) {
            if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
                provider = OPERATOR_YIDONG;
            } else if (IMSI.startsWith("46001") || IMSI.startsWith("46006")) {
                provider = OPERATOR_LIANTONG;
            } else if (IMSI.startsWith("46003")) {
                provider = OPERATOR_DIANXIN;
            }
            return provider;
        } else {
            return provider;
        }
    }

    public static int getCellularId(Context context) {
        int cellularId = 0;
        if (context == null) return cellularId;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return cellularId;
        }

        try {
            CellLocation cellLocation = tm.getCellLocation();
//            Class cdmaClass = Class.forName("android.telephony.cdma.CdmaCellLocation");
//            Class gsmClass = Class.forName("android.telephony.gsm.GsmCellLocation");
            if (cellLocation != null) {
                if (cellLocation instanceof GsmCellLocation) {
                    // 中国移动和中国联通获取LAC、CID的方式
                    GsmCellLocation location = (GsmCellLocation) tm.getCellLocation();
                    cellularId = location.getCid();
                } else if (cellLocation instanceof CdmaCellLocation) {
                    // 中国电信获取LAC、CID的方式
                    CdmaCellLocation location1 = (CdmaCellLocation) tm.getCellLocation();
                    cellularId = location1.getBaseStationId();
                }
            }
        } catch (Exception e) {
            LogUtils.LogE("---------------------" + e.getMessage());
        }
        return cellularId;
    }

    /**
     * 通过WiFiManager获取mac地址。只有手机开启wifi才能获取到mac地址
     *
     * @param context
     * @return
     */
    @Deprecated
    private static String tryGetWifiMac(Context context) {
        WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wi = wm.getConnectionInfo();
        if (wi == null || wi.getMacAddress() == null) {
            return null;
        }
        if ("02:00:00:00:00:00".equals(wi.getMacAddress().trim())) {
            return null;
        } else {
            return wi.getMacAddress().trim();
        }
    }

    /**
     * 根据IP地址获取MAC地址
     *
     * @return
     */
    private static String getLocalMacAddressFromIp() {
        String strMacAddr = null;
        try {
            //获得IpD地址
            InetAddress ip = getLocalInetAddress();
            byte[] b = NetworkInterface.getByInetAddress(ip).getHardwareAddress();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                if (i != 0) {
                    buffer.append(':');
                }
                String str = Integer.toHexString(b[i] & 0xFF);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            strMacAddr = buffer.toString().toUpperCase();
        } catch (Exception e) {

        }

        return strMacAddr;
    }

    /**
     * 获取移动设备本地IP
     *
     * @return
     */
    public static InetAddress getLocalInetAddress() {
        InetAddress ip = null;
        try {
            //列举
            Enumeration<NetworkInterface> en_netInterface = NetworkInterface.getNetworkInterfaces();
            while (en_netInterface.hasMoreElements()) {//是否还有元素
                NetworkInterface ni = (NetworkInterface) en_netInterface.nextElement();//得到下一个元素
                Enumeration<InetAddress> en_ip = ni.getInetAddresses();//得到一个ip地址的列举
                while (en_ip.hasMoreElements()) {
                    ip = en_ip.nextElement();
                    if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1)
                        break;
                    else
                        ip = null;
                }

                if (ip != null) {
                    break;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
    }

    public static String getAndroidId(Context context) {
        if (context == null) return "";
        String androidId = Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId;
    }

    //一定要在主线程里面调用
    public static String getUserAgent(Context context) {
        String userAgent = "";
        if (context == null) return userAgent;
        if (!TextUtils.isEmpty(QuKanDianApplication.mUserAgent)) {
            userAgent = QuKanDianApplication.mUserAgent;
        } else if (Looper.myLooper() == Looper.getMainLooper()) {
            userAgent = new WebView(context).getSettings().getUserAgentString();
        }
        return userAgent;
    }

    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isTabletDevice(Context context) {
        if (context == null) return false;
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static int[] getScreenSize(Context context) {
        if (context == null) return null;
        //2、通过Resources获取
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int heigth = dm.heightPixels;
        int[] wh = {width, heigth};
        return wh;
    }

    public static double[] getLatlng(Context context) {
        return GPSUtils.getInstance(context).getLngAndLat(null);
        //String provider = "";
        ////获取定位服务
        //LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        ////获取当前可用的位置控制器
        //List<String> list = locationManager.getProviders(true);

        //if (list.contains(LocationManager.GPS_PROVIDER)) {
        //    //是否为GPS位置控制器
        //    provider = LocationManager.GPS_PROVIDER;
        //} else if (list.contains(LocationManager.NETWORK_PROVIDER)) {
        //    //是否为网络位置控制器
        //    provider = LocationManager.NETWORK_PROVIDER;
        //}

        ////版本判断
        //boolean isFineLocation = true;
        //if (Build.VERSION.SDK_INT >= 23) {
        //    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        //            && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        //        isFineLocation = false;
        //    }
        //}
        //if (isFineLocation) {
        //    Location location = locationManager.getLastKnownLocation(provider);
        //    if (location != null) {
        //        //获取当前位置，这里只用到了经纬度
        //        double[] latlng = {location.getLatitude(), location.getLongitude()};
        //        return latlng;
        //    }
        //}
        //return null;

    }


}
