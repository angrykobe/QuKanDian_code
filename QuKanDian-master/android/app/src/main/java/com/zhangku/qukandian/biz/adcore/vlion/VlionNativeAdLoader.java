package com.zhangku.qukandian.biz.adcore.vlion;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.CityBean;
import com.zhangku.qukandian.bean.DeviceInfoBeen;
import com.zhangku.qukandian.bean.NetworkBeen;
import com.zhangku.qukandian.biz.adbeen.vlion.VlionAdRequestBean;
import com.zhangku.qukandian.biz.adbeen.vlion.VlionAdResponseBean;
import com.zhangku.qukandian.biz.adbeen.vlion.UserInfo;
import com.zhangku.qukandian.biz.adcore.AdConfig;
import com.zhangku.qukandian.biz.adcore.NativeAdLoaderListener;
import com.zhangku.qukandian.biz.adcore.ZkNativeAd;
import com.zhangku.qukandian.interfaces.ApplicationGetIp;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.AppUtils;
import com.zhangku.qukandian.utils.DeviceUtil;
import com.zhangku.qukandian.utils.OkHttp3Utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 瑞狮广告
 */
public class VlionNativeAdLoader {

    public VlionNativeAdLoader(){
    }

    public void loadAds(final NativeAdLoaderListener listener, final AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean) {
        QuKanDianApplication.getCityBeanFirst(new ApplicationGetIp() {
            @Override
            public void getIp(CityBean cityBean) {
                String url = getRequestUrl(cityBean.getCip(), adsRuleBean);
                OkHttp3Utils.getInstance().doGet(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                            listener.onAdLoadFailed(1, "" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try{
                            String string = response.body().string();
                            VlionAdResponseBean bean = new Gson().fromJson(string, VlionAdResponseBean.class);
                            if(bean!=null && (bean.getStatus() == 0 || bean.getStatus() == 101)){
                                ArrayList<ZkNativeAd> zkNativeAds = new ArrayList<ZkNativeAd>();

                                VlionZkNativeAd nativeAd = new VlionZkNativeAd();
                                nativeAd.makeNiceNativeAdDataBeen(bean);
                                zkNativeAds.add(nativeAd);
                                listener.onAdLoadSuccess(zkNativeAds);
                            }else{
                                listener.onAdLoadFailed(1, "");
                            }
                        }catch (Exception e){
                            listener.onAdLoadFailed(1, "" + e.getMessage());
                        }
                    }
                });
            }
        });
    }

    public String getRequestUrl(String ip, AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean) {
        try{
            Context context = QuKanDianApplication.getAppContext();
            DeviceInfoBeen deviceInfoBeen = DeviceUtil.getDeviceInfo(context);
            VlionAdRequestBean adRequestBeen = new VlionAdRequestBean();

            if (deviceInfoBeen != null) {
                try {
                    adRequestBeen.setUa(URLEncoder.encode(deviceInfoBeen.user_agent, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                adRequestBeen.setSw(deviceInfoBeen.width);
                adRequestBeen.setSh(deviceInfoBeen.heigth);
                adRequestBeen.setImei(deviceInfoBeen.imei);
                adRequestBeen.setOsv(deviceInfoBeen.version_release);
                adRequestBeen.setModel(deviceInfoBeen.model);
                adRequestBeen.setMake(deviceInfoBeen.brand);
                adRequestBeen.setMac(deviceInfoBeen.mac);//非必填
                adRequestBeen.setAnid(deviceInfoBeen.android_id);
                adRequestBeen.setOs(1);
                adRequestBeen.setDevicetype(deviceInfoBeen.device_type + 1);//设备类型 瑞狮：1：手机  2：平板   自定义的手机类型跟瑞狮定义的不一样， 所以+1
            }

            double[] latlng = DeviceUtil.getLatlng(context);
            if (latlng != null && latlng.length == 2) {
                adRequestBeen.setLon("" + latlng[0]);//非必填
                adRequestBeen.setLat("" + latlng[1]);//非必填
            }

            NetworkBeen networkBeen = DeviceUtil.getNetwork(context);
            adRequestBeen.setIp(ip);//瑞狮要求获取外网ip
            adRequestBeen.setConn(networkBeen.connectionType);//0：未知 1：wifi 2：2G 3：3G 4：4G
            adRequestBeen.setCarrier(networkBeen.operatorType);//运营商  0：其他1：移动2：联通3：电信
//        adRequestBeen.setApp_down_nextcur();//非必填
//        adRequestBeen.setNews_pagesize();//非必填
//        adRequestBeen.setNews_pageindex();//非必填
//        adRequestBeen.setNews_cat();//非必填
            //用户信息， base64编码
            UserInfo info = new UserInfo();
            if (UserManager.getInst().hadLogin() && UserManager.getInst().getUserBeam().getWechatUser() != null) {
                info.setSex(UserManager.getInst().getUserBeam().getWechatUser().getSex() / 2 + "");//性别，0-女，1-男
            }
            String encodedString = Base64.encodeToString(new Gson().toJson(info).getBytes(), Base64.DEFAULT);
            adRequestBeen.setUinfo(encodedString);//非必填
            adRequestBeen.setIdfa("");//对于Android设备，填空
            adRequestBeen.setAppid(AdConfig.vlion_appid);

            String adsCode = adsRuleBean.getAdsCode();
            String id = TextUtils.isEmpty(adsCode) ? AdConfig.vlion_tagid : adsCode;//获取广告位id，后台有配置取后台，没有取默认广告位id
            adRequestBeen.setTagid(id);
            adRequestBeen.setAdt(3);//创意类型 0：banner，1：插屏，2：开屏，3：原生  11：前贴片，12：中贴片，13：后贴片， 20：内容联盟；30：APP下载列表
            adRequestBeen.setAppname(AppUtils.getAppName(context));
            adRequestBeen.setPkgname(AppUtils.getPackName(context));
            adRequestBeen.setAppversion(AppUtils.getVersionName(context));

            return AdConfig.vlion_address + adRequestBeen.toString();
        }catch (Exception e){
            return AdConfig.vlion_address;
        }
    }
}
