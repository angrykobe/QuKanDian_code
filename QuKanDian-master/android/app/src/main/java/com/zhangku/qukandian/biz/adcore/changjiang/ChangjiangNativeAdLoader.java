package com.zhangku.qukandian.biz.adcore.changjiang;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.CityBean;
import com.zhangku.qukandian.bean.DeviceInfoBeen;
import com.zhangku.qukandian.bean.NetworkBeen;
import com.zhangku.qukandian.biz.adbeen.changjiang.ChangjiangReqBean;
import com.zhangku.qukandian.biz.adbeen.changjiang.ChangjiangResBean;
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
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.DeviceUtil;
import com.zhangku.qukandian.utils.DisplayUtils;
import com.zhangku.qukandian.utils.OkHttp3Utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChangjiangNativeAdLoader {

    public void loadAds(final AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean ,final NativeAdLoaderListener listener) {

        QuKanDianApplication.getCityBeanFirst(new ApplicationGetIp() {
            @Override
            public void getIp(CityBean cityBean) {
                String url = getRequestUrl(adsRuleBean,cityBean.getCip());
                OkHttp3Utils.getInstance().doPostJson(AdConfig.changjiang_address,url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        listener.onAdLoadFailed(1, "" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        try {
                            String string = response.body().string();
                            ChangjiangResBean bean = new Gson().fromJson(string, ChangjiangResBean.class);

                            if(bean != null && bean.getCode() == 1){

                                ArrayList<ZkNativeAd> zkNativeAds = new ArrayList<ZkNativeAd>();

                                ChangjiangZkNativeAd nativeAd = new ChangjiangZkNativeAd();
                                nativeAd.makeNiceNativeAdDataBeen(bean);
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
            ChangjiangReqBean adRequestBeen = new ChangjiangReqBean();
            ChangjiangReqBean.DeviceBean deviceBean = new ChangjiangReqBean.DeviceBean();

            deviceBean.setApp_version(CommonHelper.getVersionName(context));
            deviceBean.setHeight("" + DisplayUtils.dip2px(context, 100.0f));
            deviceBean.setWidth("" + Config.SCREEN_WIDTH);
            deviceBean.setScreen_width("" + Config.SCREEN_WIDTH);
            deviceBean.setScreen_height("" + Config.SCREEN_HEIGHT);
            deviceBean.setOs(1);
            deviceBean.setIp(ip);
            deviceBean.setPackage_name(AppUtils.getPackName(context));

            if (deviceInfoBeen != null) {
                deviceBean.setModel(deviceInfoBeen.model);
                deviceBean.setMac(deviceInfoBeen.mac);
                deviceBean.setImsi(deviceInfoBeen.imsi);
                deviceBean.setImei(deviceInfoBeen.imei);
                deviceBean.setVendor(deviceInfoBeen.brand);
                deviceBean.setUser_agent(deviceInfoBeen.user_agent);
                deviceBean.setDevice_id(deviceInfoBeen.android_id);
                deviceBean.setOs_version(deviceInfoBeen.version_release);
            }
            if (networkBeen != null) {
                deviceBean.setNet_type(networkBeen.connectionType);
                deviceBean.setOperator(networkBeen.operatorType);
            }

            double[] latlng = DeviceUtil.getLatlng(context);
            if (latlng != null && latlng.length == 2) {
                deviceBean.setLongitude("" + latlng[0]);
                deviceBean.setLatitude("" + latlng[1]);
            }else{
                deviceBean.setLongitude("" + new Random().nextInt(90));
                deviceBean.setLatitude("" + new Random().nextInt(90));
            }

            String adsCode = adsRuleBean.getAdsCode();
            String id = TextUtils.isEmpty(adsCode) ? AdConfig.changjiang_id : adsCode;//获取广告位id，后台有配置取后台，没有取默认广告位id
            adRequestBeen.setAppKey(AdConfig.changjiang_key);
            adRequestBeen.setSlotId(id);
            adRequestBeen.setDevice(deviceBean);
//            return AdConfig.changjiang_address + adRequestBeen.toString();
            return new Gson().toJson(adRequestBeen);
        } catch (Exception e) {
            return AdConfig.changjiang_address;
        }
    }
}
