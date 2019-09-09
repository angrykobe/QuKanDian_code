package com.zhangku.qukandian.biz.adcore.yuemeng;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.qq.e.comm.util.Md5Util;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.CityBean;
import com.zhangku.qukandian.bean.DeviceInfoBeen;
import com.zhangku.qukandian.bean.NetworkBeen;
import com.zhangku.qukandian.biz.adbeen.yuemeng.YueMengReqBean;
import com.zhangku.qukandian.biz.adbeen.yuemeng.YueMengResBean;
import com.zhangku.qukandian.biz.adcore.AdConfig;
import com.zhangku.qukandian.biz.adcore.NativeAdLoaderListener;
import com.zhangku.qukandian.biz.adcore.ZkNativeAd;
import com.zhangku.qukandian.interfaces.ApplicationGetIp;
import com.zhangku.qukandian.utils.AppUtils;
import com.zhangku.qukandian.utils.DeviceUtil;
import com.zhangku.qukandian.utils.OkHttp3Utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.zhangku.qukandian.biz.adcore.AdConfig.yuemeng_address;

/**
 * 阅盟广告
 */
public class YueMengNativeAdLoader {


    public YueMengNativeAdLoader() {

    }

    public void loadAds(final AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean, final NativeAdLoaderListener listener) {
        QuKanDianApplication.getCityBeanFirst(new ApplicationGetIp() {
            @Override
            public void getIp(CityBean cityBean) {
                OkHttp3Utils.getInstance().doPostJson(yuemeng_address, getReqBean(adsRuleBean, cityBean.getCip()), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                            listener.onAdLoadFailed(1, "" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try{
                            String string = response.body().string();
                            YueMengResBean bean = new Gson().fromJson(string, YueMengResBean.class);
                            List<YueMengResBean.AdsBean> ads = bean.getAds();
                            if ("0".equals(bean.getResultCode()) && ads != null && ads.size() > 0) {
                                ArrayList<ZkNativeAd> zkNativeAds = new ArrayList<ZkNativeAd>();
                                YueMengZkNativeAd nativeAd = new YueMengZkNativeAd();

                                nativeAd.makeNiceNativeAdDataBeen(ads.get(0));
                                zkNativeAds.add(nativeAd);
                                listener.onAdLoadSuccess(zkNativeAds);
                            }else{
                                listener.onAdLoadFailed(1, "数据请求出错");
                            }
                        }catch (Exception e){

                        }
                    }
                });
            }
        });
    }

    public String getReqBean(AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean, String ip) {
        YueMengReqBean adRequestBeen = new YueMengReqBean();
        try {
            Context context = QuKanDianApplication.getAppContext();
            DeviceInfoBeen deviceInfoBeen = DeviceUtil.getDeviceInfo(context);
            NetworkBeen networkBeen = DeviceUtil.getNetwork(context);
            long timeStamp = System.currentTimeMillis() / 1000;

            String adsCode = adsRuleBean.getAdsCode();
            String id = TextUtils.isEmpty(adsCode) ? AdConfig.yuemeng_appid_for_code : adsCode;//获取广告位id，后台有配置取后台，没有取默认广告位id

            adRequestBeen.setCode(id);
            adRequestBeen.setAdChannalCode(AdConfig.yuemeng_for_channal_small_img);
            adRequestBeen.setAndroidId(deviceInfoBeen.android_id);
            adRequestBeen.setAppversion(AppUtils.getVersionName(context));
            adRequestBeen.setBoard(android.os.Build.BOARD);
            adRequestBeen.setVersion("2.1");
            adRequestBeen.setUa(deviceInfoBeen.user_agent);
            adRequestBeen.setTotalRom(showROMInfo());
            adRequestBeen.setTotalRam(showRAMInfo(context));
            adRequestBeen.setTimeStamp(timeStamp);
            adRequestBeen.setScreenWidth("" + deviceInfoBeen.width);
            adRequestBeen.setScreenHeight("" + deviceInfoBeen.heigth);
            adRequestBeen.setRelease(android.os.Build.VERSION.RELEASE);
            adRequestBeen.setOs(1);
            adRequestBeen.setNetwork(networkBeen.connectionType);
            adRequestBeen.setModel(android.os.Build.MODEL);
            adRequestBeen.setManufacturer(android.os.Build.MANUFACTURER);
            adRequestBeen.setMac(deviceInfoBeen.mac);
            adRequestBeen.setImsi("" + networkBeen.operatorType);
            adRequestBeen.setImei("" + deviceInfoBeen.imei);
            adRequestBeen.setHardware(android.os.Build.HARDWARE);
            adRequestBeen.setBrand(deviceInfoBeen.brand);
            adRequestBeen.setIp(ip);

            YueMengReqBean.UserObjBean userBean = new YueMengReqBean.UserObjBean();
            double[] latlng = DeviceUtil.getLatlng(context);
            if (latlng != null && latlng.length == 2) {
                userBean.setLat(latlng[0]);
                userBean.setLon(latlng[1]);
            }
            adRequestBeen.setUserObj(userBean);
            adRequestBeen.setValidateCode(Md5Util.encode(timeStamp + adRequestBeen.getCode()));//校验码（timeStamp+code 的32 位 MD5 小写值）
            adRequestBeen.setDisplay(Build.DISPLAY);//display  显示屏参数

            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);

            adRequestBeen.setScreenInches("" + getScreenInch(context));//屏幕尺寸(英寸)
            adRequestBeen.setNum(1);
        }catch (Exception e){

        }
        return new Gson().toJson(adRequestBeen);
    }

    /*显示RAM的可用和总容量，RAM相当于电脑的内存条*/
    private String showRAMInfo(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        String[] total = fileSize(mi.totalMem);
        return total[0] + total[1];
    }

    /*显示ROM的可用和总容量，ROM相当于电脑的C盘*/
    private String showROMInfo() {
        File file = Environment.getDataDirectory();
        StatFs statFs = new StatFs(file.getPath());
        long blockSize = statFs.getBlockSize();
        long totalBlocks = statFs.getBlockCount();

        String[] total = fileSize(totalBlocks * blockSize);
        return total[0] + total[1];
    }

    /*返回为字符串数组[0]为大小[1]为单位KB或者MB*/
    private String[] fileSize(long size) {
        String str = "";
        if (size >= 1000) {
            str = "KB";
            size /= 1000;
            if (size >= 1000) {
                str = "MB";
                size /= 1000;
            }
        }
        /*将每3个数字用,分隔如:1,000*/
        DecimalFormat formatter = new DecimalFormat();
        formatter.setGroupingSize(3);
        String result[] = new String[2];
        result[0] = formatter.format(size);
        result[1] = str;
        return result;
    }

    private static double mInch = 0;

    /**
     * 获取屏幕尺寸
     *
     * @param context
     * @return
     */
    public static double getScreenInch(Context context) {
        if (mInch != 0.0d) {
            return mInch;
        }
        try {
            int realWidth = 0, realHeight = 0;
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
//            Display display = context.getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            if (android.os.Build.VERSION.SDK_INT >= 17) {
                Point size = new Point();
                display.getRealSize(size);
                realWidth = size.x;
                realHeight = size.y;
            } else if (android.os.Build.VERSION.SDK_INT < 17
                    && android.os.Build.VERSION.SDK_INT >= 14) {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                Method mGetRawW = Display.class.getMethod("getRawWidth");
                realWidth = (Integer) mGetRawW.invoke(display);
                realHeight = (Integer) mGetRawH.invoke(display);
            } else {
                realWidth = metrics.widthPixels;
                realHeight = metrics.heightPixels;
            }

            mInch = formatDouble(Math.sqrt((realWidth / metrics.xdpi) * (realWidth / metrics.xdpi) + (realHeight / metrics.ydpi) * (realHeight / metrics.ydpi)), 1);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return mInch;
    }

    /**
     * Double类型保留指定位数的小数，返回double类型（四舍五入）
     * newScale 为指定的位数
     */
    private static double formatDouble(double d, int newScale) {
        BigDecimal bd = new BigDecimal(d);
        return bd.setScale(newScale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
