package com.zhangku.qukandian.biz.adcore.oupeng;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.CityBean;
import com.zhangku.qukandian.bean.DeviceInfoBeen;
import com.zhangku.qukandian.bean.NetworkBeen;
import com.zhangku.qukandian.biz.adbeen.oupeng.OupengReqBean;
import com.zhangku.qukandian.biz.adbeen.vlion.UserInfo;
import com.zhangku.qukandian.biz.adbeen.vlion.VlionAdRequestBean;
import com.zhangku.qukandian.biz.adbeen.vlion.VlionAdResponseBean;
import com.zhangku.qukandian.biz.adcore.AdConfig;
import com.zhangku.qukandian.biz.adcore.NativeAdLoaderListener;
import com.zhangku.qukandian.biz.adcore.ZkNativeAd;
import com.zhangku.qukandian.biz.adcore.vlion.VlionZkNativeAd;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.interfaces.ApplicationGetIp;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.AppUtils;
import com.zhangku.qukandian.utils.DeviceUtil;
import com.zhangku.qukandian.utils.OkHttp3Utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.UUID;

import cn.jiguang.adsdk.AdContants;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OupengNativeAdLoader {
    public String getRequestUrl(String ip) {
        try {
            Context context = QuKanDianApplication.getAppContext();
            DeviceInfoBeen deviceInfoBeen = DeviceUtil.getDeviceInfo(context);
            OupengReqBean adRequestBeen = new OupengReqBean();
            NetworkBeen networkBeen = DeviceUtil.getNetwork(context);

            String release = Build.VERSION.RELEASE;
            String[] split = release.split("\\.");
            OupengReqBean.VersionBean versionBean = new OupengReqBean.VersionBean();
            if (split.length > 1) {
                versionBean.major = split[0];
                versionBean.minor = split[1];
            }

            OupengReqBean.AdslotBean adslotBean = new OupengReqBean.AdslotBean();
            adslotBean.id = AdConfig.oupeng_appid_for_code;
            adRequestBeen.addAdslots(adslotBean);
            //////////////////////////////
            OupengReqBean.AppBean appBean = new OupengReqBean.AppBean();
//            appBean.app_version = AppUtils.getVersionName(QuKanDianApplication.getmContext());
            String appVersion = AppUtils.getVersionName(QuKanDianApplication.getmContext());
            String[] appsplit = appVersion.split("\\.");
            OupengReqBean.VersionBean appVersionBean = new OupengReqBean.VersionBean();

            if (split.length > 1) {
                appVersionBean.major = appsplit[0];
                appVersionBean.micro = appsplit[1];
            }
            appBean.app_version = appVersionBean;
            appBean.app_package_name = AppUtils.getPackName(QuKanDianApplication.getmContext());
            appBean.app_name = AppUtils.getAppName(QuKanDianApplication.getmContext());
            adRequestBeen.setApp(appBean);
            //////////////////////////////
            OupengReqBean.DeviceBean deviceBean = new OupengReqBean.DeviceBean();
            deviceBean.os = "ANDROID";
            deviceBean.imei = deviceInfoBeen.imei;//
            deviceBean.android_id = deviceInfoBeen.android_id;//
            deviceBean.ua = deviceInfoBeen.user_agent;//
            deviceBean.mac = deviceInfoBeen.mac;//
            deviceBean.vendor = android.os.Build.MANUFACTURER;//
            deviceBean.model = deviceInfoBeen.model;//
            OupengReqBean.SizeBean sizeBean = new OupengReqBean.SizeBean();
            sizeBean.height = "" + Config.SCREEN_HEIGHT;
            sizeBean.width = "" + Config.SCREEN_WIDTH;
            deviceBean.screen_size = sizeBean;
            switch (networkBeen.connectionType) {//1为WI-FI，2为 2G，3为3G， 4为4G, 未知为0
                case 1:
                    deviceBean.connection_type = "WIFI";
                    break;
                case 2:
                    deviceBean.connection_type = "CELL_2G";
                    break;
                case 3:
                    deviceBean.connection_type = "CELL_3G";
                    break;
                case 4:
                    deviceBean.connection_type = "CELL_4G";
                    break;
                case 0:
                default:
                    deviceBean.connection_type = "UNKNOWN";
                    break;
            }
//            deviceBean.timestamp = System.currentTimeMillis()/1000;
            deviceBean.operator_id = getCarrier();
            deviceBean.imsi = deviceInfoBeen.imsi;
            deviceBean.ip = ip;//
//            deviceBean.os_version = android.os.Build.VERSION.RELEASE;
            deviceBean.os_version = versionBean;
            adRequestBeen.setDevice(deviceBean);
            ////////////////////////
            adRequestBeen.setApi_version(new OupengReqBean.VersionBean());
            ////////////////////////
            adRequestBeen.setRequest_id("" + UUID.randomUUID());


            return new Gson().toJson(adRequestBeen);
        } catch (Exception e) {
            return new Gson().toJson(new OupengReqBean().toString());
        }
    }


    private String getCarrier() {
        try {
            TelephonyManager telManager = (TelephonyManager) QuKanDianApplication.getmContext().getSystemService(Context.TELEPHONY_SERVICE);
            String operator = telManager.getSimOperator();
            return operator;
        } catch (Exception e) {
            return "";
        }

    }
}
