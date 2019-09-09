package com.zhangku.qukandian.biz.adcore.yomob;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.DeviceInfoBeen;
import com.zhangku.qukandian.bean.NetworkBeen;
import com.zhangku.qukandian.biz.adbeen.yomob.YomobReqBean;
import com.zhangku.qukandian.biz.adbeen.yomob.YomobResBean;
import com.zhangku.qukandian.biz.adcore.AdConfig;
import com.zhangku.qukandian.biz.adcore.NativeAdLoaderListener;
import com.zhangku.qukandian.utils.DeviceUtil;
import com.zhangku.qukandian.utils.DisplayUtils;
import com.zhangku.qukandian.utils.OkHttp3Utils;
import com.zhangku.qukandian.utils.ToastUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 创建者          xuzhida
 * 创建日期        2018/12/12
 * 你不注释一下？
 */
public class YomobNativeAdLoader {
    public void loadAds(final Context context) {
        OkHttp3Utils.getInstance().doPostJson(AdConfig.yomob_address, getRequestUrl(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtils.showLongToast(context, "加载失败");
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String string = response.body().string();
                    YomobResBean yomobResBean = new Gson().fromJson(string, YomobResBean.class);
                    if (yomobResBean.getStatus() == 0 && yomobResBean.getGoal_type() == 2) {
                        List<YomobResBean.AdsBean> ads = yomobResBean.getAds();
                        if (ads != null && ads.size() > 0) {
                            YomobResBean.AdsBean adsBean = ads.get(0);
                            //打开小程序
                            WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
                            req.userName = adsBean.getMini_program_id(); // 填小程序原始id
                            req.path = adsBean.getMini_program_path();   //拉起小程序页面的可带参路径，不填默认拉起小程序首页
                            req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
                            QuKanDianApplication.wxApi.sendReq(req);

                            YomobResBean.AdsBean.TrackingEventsBean tracking_events = adsBean.getTracking_events();
                            //上报点击
                            List<String> clc = tracking_events.getClick();
                            if (clc != null && clc.size() > 0) {
                                for (final String item : clc) {
                                    OkHttp3Utils.getInstance().doGetWithUA(context, item, new Callback() {
                                        @Override
                                        public void onFailure(Call call, IOException e) {

                                        }

                                        @Override
                                        public void onResponse(Call call, Response response) throws IOException {

                                        }
                                    });
                                }
                            }
                        }
                    } else {
                        ToastUtils.showLongToast(context, "加载失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public String getRequestUrl() {
        YomobReqBean bean = new YomobReqBean();
        try {
            YomobReqBean.AppBean appBean = new YomobReqBean.AppBean();
            appBean.setApp_id("03iXe1J88erS2p5855xu");
            appBean.setApp_version("" + QuKanDianApplication.getCode());
            appBean.setBundle("com.zhangku.qukandian");
            appBean.setName("趣看视界");
            appBean.setStore_url("http://sj.qq.com/myapp/detail.htm?apkName=com.zhangku.qukandian");

            DeviceInfoBeen deviceInfoBeen = DeviceUtil.getDeviceInfo(QuKanDianApplication.getContext());
            NetworkBeen networkBeen = DeviceUtil.getNetwork(QuKanDianApplication.getContext());
            YomobReqBean.DeviceBean deviceBean = new YomobReqBean.DeviceBean();
            if (deviceBean != null) {
                deviceBean.setImsi("" + networkBeen.operatorType);
                deviceBean.setConntype(networkBeen.connectionType);
                deviceBean.setIp(networkBeen.ip);
            }
            if (deviceInfoBeen != null) {
                deviceBean.setImei(deviceInfoBeen.imei);
                deviceBean.setAnid(deviceInfoBeen.android_id);
                deviceBean.setMac(deviceInfoBeen.mac);
                deviceBean.setBrand(deviceInfoBeen.brand);
                deviceBean.setOs_version(deviceInfoBeen.version_release);
                deviceBean.setScreen_width(deviceInfoBeen.width);
                deviceBean.setScreen_height(deviceInfoBeen.heigth);
                deviceBean.setUa(deviceInfoBeen.user_agent);
                bean.setAdw(deviceInfoBeen.width);

            }
            deviceBean.setType(1);
            deviceBean.setLanguage("zh");
            deviceBean.setMaker(android.os.Build.MANUFACTURER);
            deviceBean.setModel(android.os.Build.MODEL);
            deviceBean.setDevice_name(android.os.Build.PRODUCT);
            deviceBean.setOs(1);

            DisplayMetrics mDisplayMetrics = new DisplayMetrics();//屏幕分辨率容器
            WindowManager wm = (WindowManager) QuKanDianApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(mDisplayMetrics);
            deviceBean.setDensity(mDisplayMetrics.density);//密度

            deviceBean.setOrientation(1);

            bean.setAd_count(1);
            bean.setAd_type(3);
            bean.setAdh(DisplayUtils.dip2px(QuKanDianApplication.getContext(), 100.0f));
            bean.setGoal_type(2);
            bean.setIntegration("client");
            bean.setVersion("1.0.0");

            bean.setApp(appBean);
            bean.setDevice(deviceBean);
            bean.setUser(new YomobReqBean.UserBean());
        } catch (Exception e) {

        }
        return new Gson().toJson(bean);
    }
}
