package com.zhangku.qukandian.biz.adcore.huitoutiao;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.DeviceInfoBeen;
import com.zhangku.qukandian.bean.NetworkBeen;
import com.zhangku.qukandian.biz.adbeen.huitoutiao.AdInfoVO;
import com.zhangku.qukandian.biz.adbeen.huitoutiao.AdRequestBeen;
import com.zhangku.qukandian.biz.adbeen.huitoutiao.AdResponseBeen;
import com.zhangku.qukandian.biz.adbeen.huitoutiao.App;
import com.zhangku.qukandian.biz.adbeen.huitoutiao.DeviceInfo;
import com.zhangku.qukandian.biz.adbeen.huitoutiao.Location;
import com.zhangku.qukandian.biz.adbeen.huitoutiao.Network;
import com.zhangku.qukandian.biz.adbeen.huitoutiao.UserProfile;
import com.zhangku.qukandian.biz.adcore.AdConfig;
import com.zhangku.qukandian.biz.adcore.ZkNativeAd;
import com.zhangku.qukandian.biz.adcore.NativeAdLoaderListener;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.AppUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.DeviceUtil;
import com.zhangku.qukandian.utils.OkHttp3Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 惠头条API广告
 */
public class HuitoutiaoNativeAdLoader {

    public HuitoutiaoNativeAdLoader() {
    }

    public void loadAds(final NativeAdLoaderListener listener, AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean) {
        try {
            AdRequestBeen adRequestBeen = makeAdRequestBeen(adsRuleBean);
            OkHttp3Utils.getInstance().doPostJson(AdConfig.huitoutiao_address, adRequestBeen.toJson(), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    listener.onAdLoadFailed(1, "" + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    ArrayList<ZkNativeAd> zkNativeAds = new ArrayList<ZkNativeAd>();
                    try {
                        Gson gson = new Gson();

                        String json = response.body().string();
                        if (!TextUtils.isEmpty(json)) {
                            AdResponseBeen bean = gson.fromJson(json, AdResponseBeen.class);
                            if (bean != null && bean.getStatusCode() == 200
                                    && bean.getAds() != null && bean.getAds().size() > 0) {
                                for (AdInfoVO item : bean.getAds()) {
                                    HuitoutiaoZkNativeAd nativeAd = new HuitoutiaoZkNativeAd();
                                    nativeAd.makeNiceNativeAdDataBeen(item);
                                    zkNativeAds.add(nativeAd);
                                }
                                listener.onAdLoadSuccess(zkNativeAds);
                                return;
                            }
                        }
                        listener.onAdLoadFailed(1, "");

                    } catch (Exception e) {
                        listener.onAdLoadFailed(1, "" + e.getMessage());
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
        }
    }

    public AdRequestBeen makeAdRequestBeen(AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean) {
        Context context = QuKanDianApplication.getAppContext();
        AdRequestBeen adRequestBeen = new AdRequestBeen();
        adRequestBeen.setChannelId(AdConfig.huitoutiao_cids);
        //获取广告位id，后台有配置取后台，没有取默认广告位id
        String adsCode = adsRuleBean.getAdsCode();
        String id = TextUtils.isEmpty(adsCode) ? AdConfig.huitoutiao_pids : adsCode;
        adRequestBeen.setPositionId(id);

        String requestId = makeRequestId(adRequestBeen);
        adRequestBeen.setRequestId(requestId);
        adRequestBeen.setApiVersion("1.0");
        // 惠头条要求“提前请求广告数控制在2-3条”
        adRequestBeen.setAdCount(1);
        adRequestBeen.setMode((short) 1);

        DeviceInfoBeen deviceInfoBeen = DeviceUtil.getDeviceInfo(context);

        DeviceInfo deviceInfo = new DeviceInfo();
        if (deviceInfoBeen != null) {
            deviceInfo.setAndroidId(deviceInfoBeen.android_id);
            deviceInfo.setOs("Android");
            deviceInfo.setOsVersion(deviceInfoBeen.version_release);
            deviceInfo.setImei(deviceInfoBeen.imei);
            deviceInfo.setMac(deviceInfoBeen.mac);
            deviceInfo.setDeviceType(deviceInfoBeen.device_type);
            deviceInfo.setBrand(deviceInfoBeen.brand);
            deviceInfo.setModel(deviceInfoBeen.model);
            deviceInfo.setScreenWidth(deviceInfoBeen.width);
            deviceInfo.setScreenHeight(deviceInfoBeen.heigth);
            deviceInfo.setImeiMd5(CommonHelper.md5(deviceInfoBeen.imei));
            deviceInfo.setAndroidIdMd5(CommonHelper.md5(deviceInfoBeen.android_id));
            deviceInfo.setUserAgent(deviceInfoBeen.user_agent);
        } else {
            deviceInfo.setOsVersion("unknown");
            deviceInfo.setUserAgent("unknown");
            deviceInfo.setBrand("unknown");
        }
        adRequestBeen.setDeviceInfo(deviceInfo);

        Network network = new Network();
        NetworkBeen networkBeen = DeviceUtil.getNetwork(context);
        network.setIp("" + networkBeen.ip);
        network.setConnectionType(networkBeen.connectionType);
        network.setOperatorType(networkBeen.operatorType);
        network.setCellularId(networkBeen.cellularId);
        adRequestBeen.setNetwork(network);

        UserProfile userProfile = new UserProfile();
        if (UserManager.getInst().hadLogin() && UserManager.getInst().getUserBeam().getWechatUser() != null) {
            if (UserManager.getInst().getUserBeam().getWechatUser().getSex() == 1) {
                userProfile.setGender("M");
            } else if (UserManager.getInst().getUserBeam().getWechatUser().getSex() == 2) {
                userProfile.setGender("F");
            }
        }
        adRequestBeen.setUserProfile(userProfile);

        Location location = new Location();
        double[] latlng = DeviceUtil.getLatlng(context);
        if (latlng != null && latlng.length == 2) {
            location.setLogLatitude(latlng[0]);
            location.setLogLongitude(latlng[1]);
        }
        adRequestBeen.setLocation(location);

        App app = new App();
        app.setName(AppUtils.getAppName(context));
        app.setPackName(AppUtils.getPackName(context));
        app.setVersion(AppUtils.getVersionName(context));
        adRequestBeen.setApp(app);


//        OkHttp3Utils.getInstance().doGet("http://test.api.sjgo58.com/api/ads/advertisement/httreq?reqparam=" + requestId, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//            }
//        });
        return adRequestBeen;
    }

    public String makeRequestId(AdRequestBeen adRequestBeen) {
//        int currentTime = (int) (System.currentTimeMillis() / 1000);
//        String s = currentTime + "_" + CommonHelper.getStringRandom(11);
        return adRequestBeen.getChannelId() + adRequestBeen.getPositionId() + "_" + UUID.randomUUID();
    }

}
