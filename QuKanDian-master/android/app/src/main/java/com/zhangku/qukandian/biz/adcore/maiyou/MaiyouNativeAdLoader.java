package com.zhangku.qukandian.biz.adcore.maiyou;

import android.content.Context;
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
import com.zhangku.qukandian.biz.adbeen.maiyou.MaiyouReqBean;
import com.zhangku.qukandian.biz.adbeen.maiyou.MaiyouResBean;
import com.zhangku.qukandian.biz.adcore.AdConfig;
import com.zhangku.qukandian.biz.adcore.NativeAdLoaderListener;
import com.zhangku.qukandian.biz.adcore.ZkNativeAd;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.interfaces.ApplicationGetIp;
import com.zhangku.qukandian.utils.AppUtils;
import com.zhangku.qukandian.utils.DeviceUtil;
import com.zhangku.qukandian.utils.OkHttp3Utils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MaiyouNativeAdLoader {

    public void loadAds(final AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean, final NativeAdLoaderListener listener) {

        QuKanDianApplication.getCityBeanFirst(new ApplicationGetIp() {
            @Override
            public void getIp(CityBean cityBean) {
                String url = getRequestUrl(adsRuleBean, cityBean.getCip());
                OkHttp3Utils.getInstance().doPostJson(AdConfig.maiyou_address, url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        listener.onAdLoadFailed(1, "" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        try {
                            String string = response.body().string();

                            JSONObject object = new JSONObject(string);
                            String body = object.optString("body");
                            MaiyouResBean bean = new Gson().fromJson(body, MaiyouResBean.class);

                            if (bean != null && bean.getCode() == 0 && bean.getAds() != null && bean.getAds().size() != 0) {
                                ArrayList<ZkNativeAd> zkNativeAds = new ArrayList<ZkNativeAd>();

                                MaiyouZkNativeAd nativeAd = new MaiyouZkNativeAd();
                                nativeAd.makeNiceNativeAdDataBeen(bean.getAds().get(0));
                                zkNativeAds.add(nativeAd);
                                listener.onAdLoadSuccess(zkNativeAds);
                            } else {
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

    public String getRequestUrl(AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean, String ip) {
        try {
            Context context = QuKanDianApplication.getAppContext();
            DeviceInfoBeen deviceInfoBeen = DeviceUtil.getDeviceInfo(context);
            NetworkBeen networkBeen = DeviceUtil.getNetwork(context);
            MaiyouReqBean.BodyBean adRequestBeen = new MaiyouReqBean.BodyBean();


            MaiyouReqBean.BodyBean.TiBean tiBean = new MaiyouReqBean.BodyBean.TiBean();
            tiBean.setIp(ip);
            if (deviceInfoBeen != null) {
                tiBean.setUa(deviceInfoBeen.user_agent);
                tiBean.setMac(deviceInfoBeen.mac);
                tiBean.setHt(deviceInfoBeen.model);
                tiBean.setEi(deviceInfoBeen.imei);
                tiBean.setAndid(deviceInfoBeen.android_id);
                tiBean.setHm(android.os.Build.MANUFACTURER);
                tiBean.setBn(deviceInfoBeen.brand);
            }
            tiBean.setSw("" + Config.SCREEN_WIDTH);
            tiBean.setSi("" + deviceInfoBeen.imsi);
            tiBean.setSh("" + Config.SCREEN_HEIGHT);
            tiBean.setOv(android.os.Build.VERSION.RELEASE);
            tiBean.setOs("0");
            switch (networkBeen.connectionType) {
                case 0:
                    tiBean.setNt("4");
                    break;
                case 1:
                    tiBean.setNt("3");
                    break;
                case 2:
                    tiBean.setNt("1");
                    break;
                case 3:
                    tiBean.setNt("2");
                    break;
                case 4:
                    tiBean.setNt("5");
                    break;
                case 5:
                    tiBean.setNt("6");
                    break;
            }

            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            tiBean.setSmd("" + metrics.density);//设备屏幕密度，String
//            tiBean.setDpi(""+ metrics.density);//手机 dpi, int
            tiBean.setCh("xmzkkj");
//    包名：com.zt.xxgg
//    appid:82e8bd4aee4e12e937f6f2fb1e57c378
//    开屏：Dj6AO2zF
//    插屏：PxAlTJG9
//    banner：5BWhzPT4
//    信息流：itqaUeQy
            tiBean.setPkg(AppUtils.getPackName(context));
            tiBean.setAppid(AdConfig.maiyou_appid);
            tiBean.setApnm("" + AppUtils.getAppName(context));//应用名称，String，必填
            //  7 开屏  27 首页信息流  4 频道页信息流
            if ("开屏页".equals(adsRuleBean.getBelong())) {
                adRequestBeen.setAdsh("980");
                adRequestBeen.setAdsw("720");
            } else {
                adRequestBeen.setAdsh("100");
                adRequestBeen.setAdsw("150");
            }
            String adsCode = adsRuleBean.getAdsCode();
            String id = TextUtils.isEmpty(adsCode) ? AdConfig.maiyou_codeid : adsCode;//获取广告位id，后台有配置取后台，没有取默认广告位id
            adRequestBeen.setAid(id);//广告位ID

            adRequestBeen.setAv("1.0");
            adRequestBeen.setIsapi("1");
            adRequestBeen.setTi(tiBean);

            MaiyouReqBean bean = new MaiyouReqBean();
            bean.setBody(new Gson().toJson(adRequestBeen));
            bean.setHead(new Gson().toJson(new MaiyouReqBean.HeadBean()));

            return new Gson().toJson(bean);
        } catch (Exception e) {
            return AdConfig.maiyou_address;
        }
    }
}
