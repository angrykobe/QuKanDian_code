package com.zhangku.qukandian.adCommon.util;

import android.content.Context;
import android.os.Handler;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.adCommon.natives.Ad360Native;
import com.zhangku.qukandian.adCommon.natives.AdGdtNative;
import com.zhangku.qukandian.adCommon.natives.AdJRTTNative;
import com.zhangku.qukandian.adCommon.natives.AdJiguangNative;
import com.zhangku.qukandian.adCommon.natives.AdTuiaNative;
import com.zhangku.qukandian.adCommon.natives.AdYitanNative;
import com.zhangku.qukandian.adCommon.natives.AdZhiZuNative;
import com.zhangku.qukandian.adCommon.natives.AdZkNative;
import com.zhangku.qukandian.adCommon.natives.MyAdBaiduNative;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.interfaces.OnAdNativeRequestListener;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetAdsListForTypeProtocol;
import com.zhangku.qukandian.utils.LogUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 */
public class AdUtil {
    public static int mAdsCnt = 0;
    public static Set<String> mAdsCache = new HashSet<String>();
    // 补量
    public static AdLocationBeans.AdLocationsBean.ClientAdvertisesBean mAdFillBean;

    //无频道过滤广告
    public static void fetchAd(Context context, @AnnoCon.LocAdverType int adSignStr, final AdCallBack callBack) {
        fetchAd(context, adSignStr, -1, callBack);
    }

    /**
     * @param adSignStr 广告位置标识
     * @param channelID 渠道id
     * @param callBack  回调
     */

    public static void fetchAd(Context context, List<AdLocationBeans.AdLocationsBean.ClientAdvertisesBean> response,
                               @AnnoCon.LocAdverType int adSignStr, Integer channelID, AdCallBack callBack) {
        if (response == null) {
            fetchAd(context, adSignStr, channelID, callBack);
        } else {
            fetchEach(context, response, adSignStr, channelID, callBack);
        }
    }

    public static void fetchAd(final Context context, final @AnnoCon.LocAdverType int adSignStr, final Integer channelID, final AdCallBack callBack) {
        new GetAdsListForTypeProtocol(context, adSignStr, channelID, new BaseModel.OnResultListener<List<AdLocationBeans.AdLocationsBean.ClientAdvertisesBean>>() {
            @Override
            public void onResultListener(List<AdLocationBeans.AdLocationsBean.ClientAdvertisesBean> response) {
                fetchEach(context, response, adSignStr, channelID, callBack);
            }

            @Override
            public void onFailureListener(int code, String error) {

            }
        }).postRequest();
    }

    /**
     * 循环获取广告
     */
    public static void fetchEach(Context context, List<AdLocationBeans.AdLocationsBean.ClientAdvertisesBean> response,
                                 @AnnoCon.LocAdverType int adSignStr, Integer channelID, AdCallBack callBack) {
        if (response == null || response.size() == 0) {
            return;
        }
        for (int i = 0; i < response.size(); i++) {
            fetchAd(context, response.get(i), adSignStr, channelID, true, callBack);
        }
    }

    private static void fetchAd(Context context, AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adBean,
                                @AnnoCon.LocAdverType int adSignStr, Integer channelID, boolean isAgain,
                                AdCallBack callBack) {
        final AdLocationBeans.AdLocationsBean adLocationsBean = new AdLocationBeans.AdLocationsBean();
        adLocationsBean.setPageIndex(adBean.getPageIndex());
        adLocationsBean.setId(adBean.getAdLocId());
        adLocationsBean.setAdPageLocationId(adBean.getAdPageLocationId());
        LogUtils.LogW("adad:" + adLocationsBean.getPageIndex());
        requestAdContent(
                context,
                adLocationsBean,
                adBean
                , new OnAdNativeRequest(context, adBean, adSignStr, channelID, isAgain, callBack));
    }

    /**
     * 某个广告来自哪里 （埋点使用）
     *
     * @param adSignStr
     * @return
     */
    public static String getBelong(int pageIndex, int adSignStr) {
        String belong;
        switch (adSignStr) {
            case AnnoCon.AD_TYPE_HOME://首页
                belong = "首页";
                break;
            case AnnoCon.AD_TYPE_VIDEO_DETAILS://2视频详情页
                belong = pageIndex == 0 ? "视频详情页大图" : "视频详情页列表";
                break;
            case AnnoCon.AD_TYPE_NEWS_DETAILS://3文章详情页
                belong = pageIndex == 0 ? "文章详情页大图" : "文章详情页列表";
                break;
            case AnnoCon.AD_TYPE_VIDEO://视频列表页
                belong = "视频列表页";
                break;
            case AnnoCon.AD_TYPE_OPEN://开屏页
                belong = "开屏页";
                break;
            case AnnoCon.AD_TYPE_SIGN://签到弹框
                belong = "签到弹框";
                break;
            case AnnoCon.AD_TYPE_CHAI://拆宝箱弹框
                belong = "拆宝箱弹框";
                break;
            case AnnoCon.AD_TYPE_AGAIN://
                belong = "补量";
                break;
            case AnnoCon.AD_TYPE_READ_PROGRESS://
                belong = "阅读进度";
                break;
            default:
                belong = "未知的位置";
                break;
        }
        return belong;
    }

    /**
     * @param context
     * @param bean
     * @param onAdNativeRequestListener
     */
    public static void requestAdContent(Context context, AdLocationBeans.AdLocationsBean adLocationsBean
            , AdLocationBeans.AdLocationsBean.ClientAdvertisesBean bean
            , OnAdNativeRequestListener onAdNativeRequestListener) {
        try {
            String name = bean.getAdverName();//广告名称
            String belong = bean.getBelong();
            int pageIndex = adLocationsBean.getPageIndex();//广告列表下标
            Map<String, String> map = new HashMap<>();
            if (AnnoCon.AD_TYPT_BAIDU.equals(name)) {//百度
                map.put("AD_TYPT_BAIDU", "count_" + belong + "_" + pageIndex);
                new MyAdBaiduNative(context, AnnoCon.AD_TYPT_BAIDU).getAdData(adLocationsBean, bean, onAdNativeRequestListener);
            } else if (AnnoCon.AD_TYPT_GDT.equals(name)) {//广点通
                map.put("AD_TYPT_GDT", "count_" + belong + "_" + pageIndex);
                new AdGdtNative(context, AnnoCon.AD_TYPT_GDT).getAdData(adLocationsBean, bean, onAdNativeRequestListener);
            } else if (AnnoCon.AD_TYPT_360.equals(name)) {//360
                map.put("360", "count_" + belong + "_" + pageIndex);
                new Ad360Native(context, AnnoCon.AD_TYPT_360).getAdData(adLocationsBean, bean, onAdNativeRequestListener);
            } else if (AnnoCon.AD_TYPT_JRTT.equals(name)) {//今日头条
                map.put("AD_TYPT_JRTT", "count_" + belong + "_" + pageIndex);
                new AdJRTTNative(context, AnnoCon.AD_TYPT_JRTT).getAdData(adLocationsBean, bean, onAdNativeRequestListener);
            } else if (AnnoCon.AD_TYPT_XW.equals(name)) {//推啊
                map.put("AD_TYPT_XW", "count_" + belong + "_" + pageIndex);
                new AdTuiaNative(context, AnnoCon.AD_TYPT_XW).getAdData(adLocationsBean, bean, onAdNativeRequestListener);
            } else if (AnnoCon.AD_TYPT_HUITOUTIAO.equals(name)) {//惠头条API
                map.put("AD_TYPT_HUITOUTIAO", "count_" + belong + "_" + pageIndex);
                new AdZkNative(context, AnnoCon.AD_TYPT_HUITOUTIAO).getAdData(adLocationsBean, bean, onAdNativeRequestListener);
            } else if (AnnoCon.AD_TYPT_VLION.equals(name)) {//瑞狮API
                map.put("AD_TYPT_VLION", "count_" + belong + "_" + pageIndex);
                new AdZkNative(context, AnnoCon.AD_TYPT_VLION).getAdData(adLocationsBean, bean, onAdNativeRequestListener);
            } else if (AnnoCon.AD_TYPT_HUZHONG.equals(name)) {//互众API
                map.put("AD_TYPT_HUZHONG", "count_" + belong + "_" + pageIndex);
                new AdZkNative(context, AnnoCon.AD_TYPT_HUZHONG).getAdData(adLocationsBean, bean, onAdNativeRequestListener);
            } else if (AnnoCon.AD_TYPT_YUEMENG.equals(name)) {//阅盟API
                map.put("AD_TYPT_YUEMENG", "count_" + belong + "_" + pageIndex);
                new AdZkNative(context, AnnoCon.AD_TYPT_YUEMENG).getAdData(adLocationsBean, bean, onAdNativeRequestListener);
            } else if (AnnoCon.AD_TYPT_JIGUANG.equals(name)) {//极光
                map.put("AD_TYPT_JIGUANG", "count_" + belong + "_" + pageIndex);
                new AdJiguangNative(context, AnnoCon.AD_TYPT_JIGUANG).getAdData(adLocationsBean, bean, onAdNativeRequestListener);
            } else if (AnnoCon.AD_TYPT_CHANGJIAN.equals(name)) {//畅江
                map.put("AD_TYPT_CHANGJIAN", "count_" + belong + "_" + pageIndex);
                new AdZkNative(context, AnnoCon.AD_TYPT_CHANGJIAN).getAdData(adLocationsBean, bean, onAdNativeRequestListener);
            } else if (AnnoCon.AD_TYPT_SHAIBO.equals(name)) {//
                map.put("AD_TYPT_SHAIBO", "count_" + belong + "_" + pageIndex);
                new AdZkNative(context, AnnoCon.AD_TYPT_SHAIBO).getAdData(adLocationsBean, bean, onAdNativeRequestListener);
            } else if (AnnoCon.AD_TYPT_RUANGAO.equals(name)) {//
                map.put("AD_TYPT_RUANGAO", "count_" + belong + "_" + pageIndex);
                new AdZkNative(context, AnnoCon.AD_TYPT_RUANGAO).getAdData(adLocationsBean, bean, onAdNativeRequestListener);
            } else if (AnnoCon.AD_TYPT_MAIYOU.equals(name)) {//
                map.put("AD_TYPT_MAIYOU", "count_" + belong + "_" + pageIndex);
                new AdZkNative(context, AnnoCon.AD_TYPT_MAIYOU).getAdData(adLocationsBean, bean, onAdNativeRequestListener);
            } else if (AnnoCon.AD_TYPT_YITAN.equals(name)) {//
                map.put("AD_TYPT_YITAN", "count_" + belong + "_" + pageIndex);
                new AdYitanNative(context, AnnoCon.AD_TYPT_YITAN).getAdData(adLocationsBean, bean, onAdNativeRequestListener);
            }/*else if (AnnoCon.AD_TYPT_FENGLING.equals(name)){
                map.put("AD_TYPT_FENGLING","count_" + belong + "_" + pageIndex);
            }*/
            else if (bean.getAdType() != 2) {//自主广告或者合作链接
                map.put("zhizu", "count_" + belong + "_" + pageIndex);
                new AdZhiZuNative(context, AnnoCon.AD_TYPT_MYSELF).getAdData(adLocationsBean, bean, onAdNativeRequestListener);
            } else {
                if (onAdNativeRequestListener != null) onAdNativeRequestListener.onFail();
            }
            //统计  290
            MobclickAgent.onEvent(context, "AdRequestCount", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class OnAdNativeRequest implements OnAdNativeRequestListener {
        private Context context;
        private AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adBean;
        private int adSignStr;
        private Integer channelID;
        private boolean isAgain;
        private AdCallBack callBack;

        public OnAdNativeRequest(Context context, AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adBean,
                                 @AnnoCon.LocAdverType int adSignStr, Integer channelID, boolean isAgain,
                                 AdCallBack callBack) {
            this.context = context;
            this.adBean = adBean;
            this.adSignStr = adSignStr;
            this.isAgain = isAgain;
            this.callBack = callBack;
        }

        @Override
        public void onResponse(final List list, int adType) {
            if (list != null && list.size() > 0)
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.getAdContent(list.get(0), adBean.getPageIndex());
                    }
                });
        }

        @Override
        public void onFail() {
            AdLocationBeans.AdLocationsBean.ClientAdvertisesBean fillBean = AdUtil.mAdFillBean;
            if (fillBean == null) {
                fillBean = new AdLocationBeans.AdLocationsBean.ClientAdvertisesBean();
                fillBean.setAdverName("百度");
            }
            fillBean.setPageIndex(adBean.getPageIndex());
            if (adSignStr != AnnoCon.AD_TYPE_OPEN && isAgain) {
                fetchAd(context, fillBean, adSignStr, channelID, false, callBack);
            }
        }
    }

    public interface AdCallBack {
        void getAdContent(Object object, int adIndex);
    }
}
