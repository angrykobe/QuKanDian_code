package com.zhangku.qukandian.biz.adcore.xiaodu;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.DeviceInfoBeen;
import com.zhangku.qukandian.bean.NetworkBeen;
import com.zhangku.qukandian.biz.adbeen.xiaodu.XiaoDuReqBean;
import com.zhangku.qukandian.biz.adcore.AdConfig;
import com.zhangku.qukandian.biz.adcore.NativeAdLoaderListener;
import com.zhangku.qukandian.utils.AppUtils;
import com.zhangku.qukandian.utils.DeviceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class XiaoduNativeAdLoader {
    public void loadAds(Context context, AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean, final NativeAdLoaderListener listener){

    }
    public String getRequestUrl(AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean,String ip){
        try {
            Context context = QuKanDianApplication.getAppContext();
            DeviceInfoBeen deviceInfoBeen = DeviceUtil.getDeviceInfo(context);
            NetworkBeen networkBeen = DeviceUtil.getNetwork(context);
            XiaoDuReqBean adRequestBeen = new XiaoDuReqBean();
            //获取广告位id，后台有配置取后台，没有取默认广告位id
        /*    String adsCode = adsRuleBean.getAdsCode();
            String id = TextUtils.isEmpty(adsCode) ? AdConfig.huitoutiao_pids : adsCode;*/
            adRequestBeen.setId("dd2fb82d-26a5-48d6-abf9-6d8ea7889b3f");
            XiaoDuReqBean.AppBean app = new XiaoDuReqBean.AppBean();
            app.setBundle(AppUtils.getPackName(context));//app包名
            app.setName(AppUtils.getAppName(context));//app名称
            app.setId("6068");//直接填写sspid即可
            app.setVer(AppUtils.getVersionName(context));
            adRequestBeen.setApp(app);
            XiaoDuReqBean.DeviceBean device = new XiaoDuReqBean.DeviceBean();
            device.setCarrier(networkBeen.operatorType);//运营商
            device.setConnectiontype(networkBeen.connectionType);//网络类型
            device.setDeviceType(4);//设备类型  4为phone
            device.setDid(deviceInfoBeen.imei);//设备imei
            device.setDpid(deviceInfoBeen.android_id);//androidid
            device.setUa(deviceInfoBeen.user_agent);//浏览器头
            device.setIp(ip);//公网ip
            device.setMac(deviceInfoBeen.mac);//mac地址
            device.setMake(deviceInfoBeen.brand);//制造商
            device.setModel(deviceInfoBeen.model);//手机型号
            device.setOs("Android");//操作系统
            device.setOsv(deviceInfoBeen.version_release);//操作系统版本号
            device.setH(deviceInfoBeen.heigth);//屏幕高度
            device.setW(deviceInfoBeen.width);//屏幕宽度
            XiaoDuReqBean.DeviceBean.GeoBean geo = new XiaoDuReqBean.DeviceBean.GeoBean();
            double[] latlng = DeviceUtil.getLatlng(context);
            geo.setLat(latlng[0]);
            geo.setLon(latlng[1]);
            device.setGeo(geo);//经纬度信息
            List<XiaoDuReqBean.ImpBean> imp = new ArrayList<>();
            XiaoDuReqBean.ImpBean impBean=new XiaoDuReqBean.ImpBean();
            impBean.setId("1965");
            XiaoDuReqBean.ImpBean.NativeBean nativeBean=new XiaoDuReqBean.ImpBean.NativeBean();
            List<XiaoDuReqBean.ImpBean.NativeBean.AssetsBean> assetsBeanList=new ArrayList<>();
            XiaoDuReqBean.ImpBean.NativeBean.AssetsBean assetsBean=new XiaoDuReqBean.ImpBean.NativeBean.AssetsBean();
            XiaoDuReqBean.ImpBean.NativeBean.AssetsBean.TitleBean titleBean=new XiaoDuReqBean.ImpBean.NativeBean.AssetsBean.TitleBean();
            XiaoDuReqBean.ImpBean.NativeBean.AssetsBean.ImgBean imgBean=new XiaoDuReqBean.ImpBean.NativeBean.AssetsBean.ImgBean();
            XiaoDuReqBean.ImpBean.NativeBean.AssetsBean.DataBean dataBean=new XiaoDuReqBean.ImpBean.NativeBean.AssetsBean.DataBean();
            Random random=new Random();
            int i = random.nextInt(2)+1;
            if (i==1||i==2){
                assetsBean.setId(0);
                assetsBean.setRequired(1);
                titleBean.setLen(50);
                assetsBean.setTitle(titleBean);
                assetsBean.setId(1);
                assetsBean.setRequired(1);
                imgBean.setH(300);
                imgBean.setW(500);
                imgBean.setType(3);
                assetsBean.setImg(imgBean);
                assetsBean.setId(2);
                assetsBean.setRequired(0);
                dataBean.setType(2);
                assetsBean.setData(dataBean);
                assetsBeanList.add(assetsBean);
                nativeBean.setAssets(assetsBeanList);
                impBean.setNativeX(nativeBean);
                imp.add(impBean);
                adRequestBeen.setImp(imp);
            }else {
                assetsBean.setId(0);
                assetsBean.setRequired(1);
                titleBean.setLen(50);
                assetsBean.setTitle(titleBean);
                assetsBean.setId(1);
                assetsBean.setRequired(1);
                imgBean.setH(300);
                imgBean.setW(500);
                imgBean.setType(3);
                assetsBean.setImg(imgBean);
                assetsBean.setId(2);
                assetsBean.setRequired(0);
                dataBean.setType(2);
                assetsBean.setData(dataBean);
                assetsBean.setId(3);
                assetsBean.setRequired(1);
                imgBean.setH(300);
                imgBean.setW(500);
                imgBean.setType(3);
                assetsBean.setImg(imgBean);
                assetsBean.setId(4);
                assetsBean.setRequired(1);
                imgBean.setH(300);
                imgBean.setW(500);
                imgBean.setType(3);
                assetsBean.setImg(imgBean);
                assetsBeanList.add(assetsBean);
                nativeBean.setAssets(assetsBeanList);
                impBean.setNativeX(nativeBean);
                imp.add(impBean);
                adRequestBeen.setImp(imp);
            }
            return new Gson().toJson(adRequestBeen);
        } catch (Exception e) {
            return AdConfig.saibo_address;
        }
    }
}
