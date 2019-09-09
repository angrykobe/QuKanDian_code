package com.zhangku.qukandian.biz.adcore.huzhong;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.DeviceInfoBeen;
import com.zhangku.qukandian.bean.NetworkBeen;
import com.zhangku.qukandian.biz.adbeen.huzhong.HuzhongAdRequestBean;
import com.zhangku.qukandian.biz.adbeen.huzhong.HuzhongAdResponseBean;
import com.zhangku.qukandian.biz.adcore.AdConfig;
import com.zhangku.qukandian.biz.adcore.NativeAdLoaderListener;
import com.zhangku.qukandian.biz.adcore.ZkNativeAd;
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
 * 互众广告
 */
public class HuzhongNativeAdLoader {


    public HuzhongNativeAdLoader() {

    }

    /**
     *
     * @param context
     * @param adsRuleBean
     * @param listener
     */
    public void loadAds(Context context, AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean,final NativeAdLoaderListener listener) {
        String url = getRequestUrl(adsRuleBean);
        //互众要求请求ua
        OkHttp3Utils.getInstance().doGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                    listener.onAdLoadFailed(1, ""+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String string = response.body().string();
                    HuzhongAdResponseBean bean = new Gson().fromJson(string, HuzhongAdResponseBean.class);

                    ArrayList<ZkNativeAd> zkNativeAds = new ArrayList<ZkNativeAd>();

                    HuzhongZkNativeAd nativeAd = new HuzhongZkNativeAd();
                    if(bean != null && bean.getAd()!=null && bean.getAd().size()>0){
                        nativeAd.makeNiceNativeAdDataBeen(bean.getAd().get(0));
                        zkNativeAds.add(nativeAd);
                        listener.onAdLoadSuccess(zkNativeAds);
                    }else{
                        listener.onAdLoadFailed(1,"无广告");
                    }
                }catch (Exception e){

                    listener.onAdLoadFailed(1, "" + e.getMessage());
                }
            }
        });
    }

    public String getRequestUrl(AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean) {
        try {
            Context context = QuKanDianApplication.getAppContext();
            DeviceInfoBeen deviceInfoBeen = DeviceUtil.getDeviceInfo(context);
            NetworkBeen networkBeen = DeviceUtil.getNetwork(context);

            HuzhongAdRequestBean adRequestBeen = new HuzhongAdRequestBean();
            HuzhongAdRequestBean.Device device = new HuzhongAdRequestBean.Device();

            String adsCode = adsRuleBean.getAdsCode();
            String id = TextUtils.isEmpty(adsCode) ? AdConfig.huzhong_appid : adsCode;//获取广告位id，后台有配置取后台，没有取默认广告位id

            adRequestBeen.setSi(URLEncoder.encode(id, "UTF-8"));
            adRequestBeen.setPackage_name(URLEncoder.encode(AppUtils.getPackName(context), "UTF-8"));
            adRequestBeen.setApp_version(URLEncoder.encode(AppUtils.getVersionName(context), "UTF-8"));
            adRequestBeen.setV(URLEncoder.encode("1.4.1", "UTF-8"));
            //多个mime类型用逗号分隔。  txt-文字链，icon-图文，c-富媒体 img-等价于jpg,gif,png,webp	jpg,gif,png,mp4,webp,flv,swf,txt,icon,c
            adRequestBeen.setMimes(URLEncoder.encode("jpg,png,txt,icon", "UTF-8"));
            adRequestBeen.setIp(URLEncoder.encode("client", "UTF-8"));

            device.setOs("1");
            if (deviceInfoBeen != null) {
                adRequestBeen.setUa(URLEncoder.encode(deviceInfoBeen.user_agent, "UTF-8"));
                device.setOs_version(deviceInfoBeen.version_release);
                device.setVendor(deviceInfoBeen.brand);
                device.setWidth("" + deviceInfoBeen.width);
                device.setHeight("" + deviceInfoBeen.heigth);
                device.setModel(deviceInfoBeen.model);
                device.setMac(deviceInfoBeen.mac);
                device.setAndroid_id(deviceInfoBeen.android_id);
                device.setIdentify_type("imei");
                device.setUdid(deviceInfoBeen.imei);
            }
            if(networkBeen != null){
                device.setNetwork("" + networkBeen.connectionType);//0: 其它 1: WIFI  2: 2G  3: 3G  4: 4G
                device.setOperator(""+networkBeen.operatorType);
            }
            adRequestBeen.setDevice(device);

            return AdConfig.huzhong_address + adRequestBeen.toString();
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

}
