package com.zhangku.qukandian.biz.adcore.wangmai;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.CityBean;
import com.zhangku.qukandian.bean.DeviceInfoBeen;
import com.zhangku.qukandian.bean.NetworkBeen;
import com.zhangku.qukandian.biz.adbeen.wangmai.WangMaiReqBean;
import com.zhangku.qukandian.biz.adbeen.wangmai.WangMaiResBean;
import com.zhangku.qukandian.biz.adcore.AdConfig;
import com.zhangku.qukandian.biz.adcore.NativeAdLoaderListener;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.interfaces.ApplicationGetIp;
import com.zhangku.qukandian.utils.AppUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.DeviceUtil;
import com.zhangku.qukandian.utils.OkHttp3Utils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 创建者          xuzhida
 * 创建日期        2018/12/12
 * 你不注释一下？
 */
public class WangMaiNativeAdLoader {


    public WangMaiNativeAdLoader() {

    }

    public void loadAds(final AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean, final NativeAdLoaderListener listener) {
        QuKanDianApplication.getCityBeanFirst(new ApplicationGetIp() {
            @Override
            public void getIp(CityBean cityBean) {
                OkHttp3Utils.getInstance().doPostJson(AdConfig.wangmai_address, getReqBean(adsRuleBean, cityBean), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        listener.onAdLoadFailed(1, "" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            String string = response.body().string();
                            WangMaiResBean bean = new Gson().fromJson(string, WangMaiResBean.class);
                        } catch (Exception e) {
                        }
                    }
                });
            }
        });
    }

    public String getReqBean(AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean, CityBean cityBean) {
        WangMaiReqBean wangMaiReqBean = new WangMaiReqBean();
        try{
            String key = "79d4d45c8c19f571";
            String apptoken = "f2stmeqwmq";
            wangMaiReqBean.setApptoken("f2stmeqwmq");
            wangMaiReqBean.setSign(CommonHelper.md5(key + apptoken));

            WangMaiReqBean.DataBean dataBean = new WangMaiReqBean.DataBean();
            WangMaiReqBean.DataBean.AppBean.AppVersionBean appVersionBean = new WangMaiReqBean.DataBean.AppBean.AppVersionBean();
            appVersionBean.setMajor(AppUtils.getVersionCode(QuKanDianApplication.getmContext()));
            WangMaiReqBean.DataBean.AppBean app = new WangMaiReqBean.DataBean.AppBean();
            app.setApp_version(appVersionBean);

            WangMaiReqBean.DataBean.AdslotBean.AdslotSizeBean adslotSizeBean = new WangMaiReqBean.DataBean.AdslotBean.AdslotSizeBean();
            adslotSizeBean.setHeight(Config.SCREEN_HEIGHT);
            adslotSizeBean.setWidth(Config.SCREEN_WIDTH);
            WangMaiReqBean.DataBean.AdslotBean adslot = new WangMaiReqBean.DataBean.AdslotBean();

            String adsCode = adsRuleBean.getAdsCode();
            String id = TextUtils.isEmpty(adsCode) ? "168863" : adsCode;//获取广告位id，后台有配置取后台，没有取默认广告位id
            adslot.setAdslot_id(id);
            adslot.setAdslot_size(adslotSizeBean);

            NetworkBeen networkBeen = DeviceUtil.getNetwork(QuKanDianApplication.getmContext());
            WangMaiReqBean.DataBean.NetworkBean network = new WangMaiReqBean.DataBean.NetworkBean();
            if (networkBeen != null) {
                network.setConnection_type(networkBeen.connectionType);
//          network.setIpv4(cityBean.getCip());
                network.setIpv4(networkBeen.ip);
                network.setOperator_type(networkBeen.operatorType);
            }
            WangMaiReqBean.DataBean.DeviceBean device = new WangMaiReqBean.DataBean.DeviceBean();
            DeviceInfoBeen deviceInfoBeen = DeviceUtil.getDeviceInfo(QuKanDianApplication.getmContext());
            device.setDevice_type(1);
            device.setOs_type(1);
            device.setVendor(deviceInfoBeen.brand);
            device.setModel(android.os.Build.MODEL);
            device.setUser_agent(deviceInfoBeen.user_agent);

            WangMaiReqBean.DataBean.DeviceBean.ScreenSizeBean screenSizeBean = new WangMaiReqBean.DataBean.DeviceBean.ScreenSizeBean();
            screenSizeBean.setHeight(Config.SCREEN_HEIGHT);
            screenSizeBean.setWidth(Config.SCREEN_WIDTH);
            WangMaiReqBean.DataBean.DeviceBean.OsVersionBean aosVersionBean = new WangMaiReqBean.DataBean.DeviceBean.OsVersionBean();
            aosVersionBean.setMajor(deviceInfoBeen.version_sdk);

            WangMaiReqBean.DataBean.DeviceBean.UdidBean udidBean = new WangMaiReqBean.DataBean.DeviceBean.UdidBean();
            udidBean.setAndroid_id(deviceInfoBeen.android_id);
            udidBean.setImei("" + deviceInfoBeen.imei);
            udidBean.setMac(deviceInfoBeen.mac);

            device.setOs_version(aosVersionBean);
            device.setScreen_size(screenSizeBean);
            device.setUdid(udidBean);

            dataBean.setAdslot(adslot);
            dataBean.setApp(app);
            dataBean.setDevice(device);
            dataBean.setNetwork(network);
            wangMaiReqBean.setData(dataBean);
        }catch (Exception e){
        }
        return new Gson().toJson(wangMaiReqBean);
    }
}
