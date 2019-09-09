package com.zhangku.qukandian.biz.adcore.saibo;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.google.gson.Gson;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.CityBean;
import com.zhangku.qukandian.bean.DeviceInfoBeen;
import com.zhangku.qukandian.bean.NetworkBeen;
import com.zhangku.qukandian.biz.adbeen.saibo.SaiboReqBean;
import com.zhangku.qukandian.biz.adbeen.saibo.SaiboResBean;
import com.zhangku.qukandian.biz.adcore.AdConfig;
import com.zhangku.qukandian.biz.adcore.NativeAdLoaderListener;
import com.zhangku.qukandian.biz.adcore.ZkNativeAd;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.interfaces.ApplicationGetIp;
import com.zhangku.qukandian.utils.AppUtils;
import com.zhangku.qukandian.utils.DeviceUtil;
import com.zhangku.qukandian.utils.OkHttp3Utils;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.UUID;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SaiboNativeAdLoader {

    public void loadAds(final AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean ,final NativeAdLoaderListener listener) {

        QuKanDianApplication.getCityBeanFirst(new ApplicationGetIp() {
            @Override
            public void getIp(CityBean cityBean) {
                String url = getRequestUrl(adsRuleBean,cityBean.getCip());
                OkHttp3Utils.getInstance().doPostJson(AdConfig.saibo_address,url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        listener.onAdLoadFailed(1, "" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        try {
                            String string = response.body().string();
                            SaiboResBean bean = new Gson().fromJson(string, SaiboResBean.class);

                            if(bean != null && bean.getCode() == 200 && bean.getImp()!=null && bean.getImp().size()!=0){
                                ArrayList<ZkNativeAd> zkNativeAds = new ArrayList<ZkNativeAd>();

                                SaiboZkNativeAd nativeAd = new SaiboZkNativeAd();
                                nativeAd.makeNiceNativeAdDataBeen(bean.getImp().get(0));
                                zkNativeAds.add(nativeAd);
                                listener.onAdLoadSuccess(zkNativeAds);
                            }else{
                                listener.onAdLoadFailed(1, "");
                            }
                        } catch (Exception e) {
                            listener.onAdLoadFailed(1, "" + e.getMessage());
                        }
                    }
                });
            }
        });
    }

    public String getRequestUrl(AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean,String ip) {
        try {
            Context context = QuKanDianApplication.getAppContext();
            DeviceInfoBeen deviceInfoBeen = DeviceUtil.getDeviceInfo(context);
            NetworkBeen networkBeen = DeviceUtil.getNetwork(context);
            SaiboReqBean adRequestBeen = new SaiboReqBean();

            long l = System.currentTimeMillis();
            adRequestBeen.setTime(""+ l);
            adRequestBeen.setSw(Config.SCREEN_WIDTH);
            adRequestBeen.setSh(Config.SCREEN_HEIGHT);

            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            adRequestBeen.setPpi((int) metrics.density);

            adRequestBeen.setOsv(android.os.Build.VERSION.RELEASE);
            adRequestBeen.setMake(android.os.Build.MANUFACTURER);
//            adRequestBeen.setAppid(AppUtils.getPackName(QuKanDianApplication.getContext()));
            adRequestBeen.setAppver(AppUtils.getVersionName(QuKanDianApplication.getContext()));
//            adRequestBeen.setImpsize();
//            adRequestBeen.setIdfa();
//            adRequestBeen.setRes_type();
            adRequestBeen.setCarrier(getCarrier());
            adRequestBeen.setIp(ip);
            if (deviceInfoBeen != null) {
                adRequestBeen.setUa(deviceInfoBeen.user_agent);
                adRequestBeen.setMac(deviceInfoBeen.mac);
                adRequestBeen.setModel(deviceInfoBeen.model);
                adRequestBeen.setImei(deviceInfoBeen.imei);
                adRequestBeen.setDevicetype(deviceInfoBeen.device_type);
                adRequestBeen.setAndroidid(deviceInfoBeen.android_id);
            }
            if (networkBeen != null) {
                adRequestBeen.setNetwork(networkBeen.connectionType);//0:未识别,1:WIFI,2:2G,3:3G,4:4G。
            }

            double[] latlng = DeviceUtil.getLatlng(context);
            if (latlng != null && latlng.length == 2) {
                adRequestBeen.setLon(latlng[0]);
                adRequestBeen.setLat(latlng[1]);
            }

            String adsCode = adsRuleBean.getAdsCode();
            String id = TextUtils.isEmpty(adsCode) ? AdConfig.saibo_id : adsCode;//获取广告位id，后台有配置取后台，没有取默认广告位id

            adRequestBeen.setAppid(AdConfig.saibo_appid);
            adRequestBeen.setAdspotid(id);
            String s = UUID.randomUUID().toString();
            adRequestBeen.setReqid(""+ s.replaceAll("-",""));
            String tokenRaw = AdConfig.saibo_appid + AdConfig.saibo_key + l;
            adRequestBeen.setToken(getMD5(tokenRaw));

            return new Gson().toJson(adRequestBeen);
        } catch (Exception e) {
            return AdConfig.saibo_address;
        }
    }


    private String getMD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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

//    private Location getLocation() {
//        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
//        String locationProvider;
//        //获取所有可用的位置提供器
//        List<String> providers = locationManager.getProviders(true);
//        if (providers.contains(LocationManager.GPS_PROVIDER)) {
//            //如果是GPS
//            locationProvider = LocationManager.GPS_PROVIDER;
//        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
//            //如果是Network
//            locationProvider = LocationManager.NETWORK_PROVIDER;
//        } else {
//            return null;
//        }
//        //获取Location
//        Location location = locationManager.getLastKnownLocation(locationProvider);
//        if (location != null) {
//            //不为空,显示地理位置经纬度
//            return location;
//        } else {
//            return null;
//        }
//    }
}
