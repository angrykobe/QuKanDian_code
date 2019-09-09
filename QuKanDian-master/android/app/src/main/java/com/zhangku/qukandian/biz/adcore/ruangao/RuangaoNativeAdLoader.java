package com.zhangku.qukandian.biz.adcore.ruangao;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.CityBean;
import com.zhangku.qukandian.bean.DeviceInfoBeen;
import com.zhangku.qukandian.bean.NetworkBeen;
import com.zhangku.qukandian.biz.adbeen.huitoutiao.Location;
import com.zhangku.qukandian.biz.adbeen.ruangao.RuangaoReqBean;
import com.zhangku.qukandian.biz.adbeen.ruangao.RuangaoResBean;
import com.zhangku.qukandian.biz.adcore.AdConfig;
import com.zhangku.qukandian.biz.adcore.NativeAdLoaderListener;
import com.zhangku.qukandian.biz.adcore.ZkNativeAd;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.interfaces.ApplicationGetIp;
import com.zhangku.qukandian.utils.AppUtils;
import com.zhangku.qukandian.utils.DeviceUtil;
import com.zhangku.qukandian.utils.OkHttp3Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RuangaoNativeAdLoader {

    public void loadAds(final AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean ,final NativeAdLoaderListener listener) {

        QuKanDianApplication.getCityBeanFirst(new ApplicationGetIp() {
            @Override
            public void getIp(CityBean cityBean) {
                String url = getRequestUrl(adsRuleBean,cityBean.getCip());
                OkHttp3Utils.getInstance().doPostJson(AdConfig.ruangao_address,url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        listener.onAdLoadFailed(1, "" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        try {
                            String string = response.body().string();
                            RuangaoResBean bean = new Gson().fromJson(string, RuangaoResBean.class);

                            if(bean != null && bean.getAd_url() != null){
                                ArrayList<ZkNativeAd> zkNativeAds = new ArrayList<ZkNativeAd>();

                                RuangaoZkNativeAd nativeAd = new RuangaoZkNativeAd();
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
            RuangaoReqBean adRequestBeen = new RuangaoReqBean();

            RuangaoReqBean.SiteBean siteBean = new RuangaoReqBean.SiteBean();
            siteBean.setId(AdConfig.ruangao_siteid);
            siteBean.setName("趣看视界");
            adRequestBeen.setSite(siteBean);
            /////////////////////
            RuangaoReqBean.AppBean appBean = new RuangaoReqBean.AppBean();
            appBean.setId(""+AppUtils.getPackName(context));
            appBean.setName(""+context.getString(R.string.app_name));
            appBean.setVer(""+AppUtils.getVersionCode(context));
            adRequestBeen.setApp(appBean);
            ///////////////////////
            RuangaoReqBean.ImpBean impBean = new RuangaoReqBean.ImpBean();
            RuangaoReqBean.ImpBean.BannerBean bannerBean = new RuangaoReqBean.ImpBean.BannerBean();
            //  7 开屏  27 首页信息流  4 频道页信息流
            if("开屏页".equals(adsRuleBean.getBelong())){
                bannerBean.setPos(7);
                bannerBean.setH(980);
                bannerBean.setW(720);
            }else{
                bannerBean.setPos(4);
                bannerBean.setH(100);
                bannerBean.setW(150);
            }
            String adsCode = adsRuleBean.getAdsCode();
            String tagid = TextUtils.isEmpty(adsCode) ? AdConfig.ruangao_tagid : adsCode;//获取广告位id，后台有配置取后台，没有取默认广告位id
            impBean.setBanner(bannerBean);
            impBean.setTagid(""+tagid);
            impBean.setId(UUID.randomUUID()+"");
            adRequestBeen.setImp(impBean);
            /////////////////////////////
            RuangaoReqBean.DeviceBean deviceBean = new RuangaoReqBean.DeviceBean();

            deviceBean.setW(""+Config.SCREEN_WIDTH);
            deviceBean.setH(""+Config.SCREEN_HEIGHT);

            if (deviceInfoBeen != null) {
                deviceBean.setUa(deviceInfoBeen.user_agent);
                deviceBean.setMac(deviceInfoBeen.mac);
                deviceBean.setModel(deviceInfoBeen.model);
                deviceBean.setId(deviceInfoBeen.android_id);
                deviceBean.setDpid(deviceInfoBeen.imei);//"IMEI"
            }
            deviceBean.setDevicetype(3);
            deviceBean.setScreenorientation(1);
            deviceBean.setOsv(android.os.Build.VERSION.RELEASE);
            deviceBean.setOs("android");
            deviceBean.setConnectiontype(networkBeen.connectionType>2?2:networkBeen.connectionType);
            deviceBean.setIp(ip);

            RuangaoReqBean.DeviceBean.GeoBean geo = new RuangaoReqBean.DeviceBean.GeoBean();
            double[] latlng = DeviceUtil.getLatlng(context);
            if (latlng != null && latlng.length == 2) {
                geo.setLat(latlng[0]);
                geo.setLon(latlng[1]);
            }

            deviceBean.setGeo(geo);
            adRequestBeen.setDevice(deviceBean);

            adRequestBeen.setId(UUID.randomUUID()+"");
            adRequestBeen.setVersion("1.6.3");

            /////////////////////////////
            return new Gson().toJson(adRequestBeen);
        } catch (Exception e) {
            return "";
        }
    }
}
