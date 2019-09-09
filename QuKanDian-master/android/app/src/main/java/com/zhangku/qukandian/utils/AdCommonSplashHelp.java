package com.zhangku.qukandian.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.adCommon.splash.AdAPISplash;
import com.zhangku.qukandian.adCommon.splash.AdFelinkSplash;
import com.zhangku.qukandian.adCommon.splash.AdGdtSplash;
import com.zhangku.qukandian.adCommon.splash.AdJRTTSplash;
import com.zhangku.qukandian.adCommon.splash.AdMyBaiduSplash;
import com.zhangku.qukandian.adCommon.splash.AdYitanSplash;
import com.zhangku.qukandian.adCommon.util.AdUtil;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.interfaces.OnAdSplashListener;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetAdsListForTypeProtocol;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.AdsRecordUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuzuoning on 2017/10/17.
 */
public class AdCommonSplashHelp {
    private static AdCommonSplashHelp mAdCommonSplashHelp = null;

    private AdCommonSplashHelp() {
    }

    public static AdCommonSplashHelp getInstance() {
        if (null == mAdCommonSplashHelp) {
            synchronized (AdCommonSplashHelp.class) {
                if (null == mAdCommonSplashHelp) {
                    mAdCommonSplashHelp = new AdCommonSplashHelp();
                }
            }
        }
        return mAdCommonSplashHelp;
    }

    /**
     * @param context
     * @param view
     * @param skipView
     * @param onAdSplashListener
     */
    public void getAdResult(final Context context, final FrameLayout view, final ViewGroup skipView, final OnAdSplashListener onAdSplashListener) {
        new GetAdsListForTypeProtocol(context, AnnoCon.AD_TYPE_OPEN, -1, new BaseModel.OnResultListener<List<AdLocationBeans.AdLocationsBean.ClientAdvertisesBean>>() {
            @Override
            public void onResultListener(List<AdLocationBeans.AdLocationsBean.ClientAdvertisesBean> response) {
                AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adBean = null;
                if (response != null && response.size() > 0) {
                    adBean = response.get(0);
                }
                if (response != null && response.size() > 1) {
                    AdUtil.mAdFillBean = response.get(1);
                }
                getAdResult(context, adBean, onAdSplashListener, view, skipView);
            }

            @Override
            public void onFailureListener(int code, String error) {
                getAdResult(context, null, onAdSplashListener, view, skipView);
            }
        }).postRequest();
    }

    private void getAdResult(final Context context, AdLocationBeans.AdLocationsBean.ClientAdvertisesBean bean, final OnAdSplashListener onAdSplashListener, final FrameLayout view, final ViewGroup skipView) {
        if (bean == null) {
            bean = new AdLocationBeans.AdLocationsBean.ClientAdvertisesBean();
            bean.setAdverName("百度");
        }
        LogUtils.LogW("getAdResult:"+bean.getAdverName());
        Map<String, String> map = new HashMap<>();
        map.put("request", "" + bean.getAdverName());
        MobclickAgent.onEvent(context, "Splash", map);

        AdLocationBeans.AdLocationsBean adLocationsBean = new AdLocationBeans.AdLocationsBean();
        adLocationsBean.setId(bean.getAdLocId());
        adLocationsBean.setPageIndex(bean.getPageIndex());


        OnAdSplashListener onAdSplashListenerInner = new OnAdSplashListener() {
            AdLocationBeans.AdLocationsBean.ClientAdvertisesBean bean;

            public OnAdSplashListener setBean(AdLocationBeans.AdLocationsBean.ClientAdvertisesBean bean) {
                this.bean = bean;
                return this;
            }

            @Override
            public void adSuccess() {
                LogUtils.LogW("getAdResult:adSuccess"+bean.getAdverName());
                Map<String, String> map = new HashMap<>();
                map.put("show", "" + bean.getAdverName());
                MobclickAgent.onEvent(context, "Splash", map);
                onAdSplashListener.adSuccess();
            }

            @Override
            public void adFail() {
                LogUtils.LogW("getAdResult:adFail"+bean.getAdverName());
                AdLocationBeans.AdLocationsBean.ClientAdvertisesBean fillBean = AdUtil.mAdFillBean;
                if (fillBean == null) {
                    fillBean = new AdLocationBeans.AdLocationsBean.ClientAdvertisesBean();
                    fillBean.setAdverName("百度");
                }
                new AdMyBaiduSplash(context).getAdData(view, skipView, fillBean, onAdSplashListener);
            }

            @Override
            public void adClick() {
                Map<String, String> map = new HashMap<>();
                map.put("onClick", "" + bean.getAdverName());
                MobclickAgent.onEvent(context, "Splash", map);
                onAdSplashListener.adClick();
            }
        }.setBean(bean);

        if ("百度".equals(bean.getAdverName())) {
           new AdMyBaiduSplash(context).getAdData(view, skipView, bean, onAdSplashListenerInner);
            //new AdFelinkSplash(context).getAdData(view,skipView,bean,onAdSplashListenerInner);
        } else if ("广点通".equals(bean.getAdverName())) {
            new AdGdtSplash(context).getAdData(view, skipView, bean, onAdSplashListenerInner);
        } else if (AnnoCon.AD_TYPT_JRTT.equals(bean.getAdverName())) {//今日头条
            new AdJRTTSplash(context).getAdData(view, skipView, bean, onAdSplashListenerInner);
        } else if (AnnoCon.AD_TYPT_YITAN.equals(bean.getAdverName())) {//今日头条
            skipView.setVisibility(View.INVISIBLE);
            new AdYitanSplash(context).getAdData(view, skipView, bean, onAdSplashListenerInner);
        }else if (AnnoCon.AD_TYPT_FENGLING.equals(bean.getAdverName())){
            new AdFelinkSplash(context).getAdData(view,skipView,bean,onAdSplashListenerInner);
        }
        else {
            int temp = AdsRecordUtils.getInstance().getInt(Constants.AD_SPLASH + bean.getAdverName());
            temp++;
            AdsRecordUtils.getInstance().putInt(Constants.AD_SPLASH + bean.getAdverName(), temp);
            new AdAPISplash(context, adLocationsBean).getAdData(view, skipView, bean, onAdSplashListenerInner);
        }
    }
}
