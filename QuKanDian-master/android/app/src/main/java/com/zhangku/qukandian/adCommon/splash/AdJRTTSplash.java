package com.zhangku.qukandian.adCommon.splash;

import android.content.Context;
import android.support.annotation.MainThread;
import android.view.View;
import android.view.ViewGroup;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTSplashAd;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.interfaces.OnAdSplashListener;
import com.zhangku.qukandian.utils.AdsLogUpUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.AdsRecordUtils;

public class AdJRTTSplash extends AdSplashRequest {

    private static final int AD_TIME_OUT = 2000;

    public AdJRTTSplash(Context context) {
        super(context);
    }

    @Override
    public void getAdData(final ViewGroup viewGroup, final ViewGroup skipView, final AdLocationBeans.AdLocationsBean.ClientAdvertisesBean name, final OnAdSplashListener onAdSplashListener) {

        //step3:创建开屏广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId("801141843")
                .setSupportDeepLink(true)
                .setImageAcceptedSize(1080, 1920)
                .build();
        //step4:请求广告，调用开屏广告异步请求接口，对请求回调的广告作渲染处理
        QuKanDianApplication.ttAdManager.createAdNative(mContext).loadSplashAd(adSlot, new TTAdNative.SplashAdListener() {
            @Override
            @MainThread
            public void onError(int code, String message) {
                onAdSplashListener.adFail();
            }

            @Override
            @MainThread
            public void onTimeout() {
                onAdSplashListener.adFail();
            }

            @Override
            @MainThread
            public void onSplashAdLoad(TTSplashAd ad) {
                if (ad == null) {
                    return;
                }
                //获取SplashView
                View view = ad.getSplashView();
                //把SplashView 添加到ViewGroup中,注意开屏广告view：width >=70%屏幕宽；height >=50%屏幕宽
                viewGroup.addView(view);
                //设置不开启开屏广告倒计时功能以及不显示跳过按钮,如果这么设置，您需要自定义倒计时逻辑
                ad.setNotAllowSdkCountdown();
//                ad.setSplashInteractionListener();
                //设置SplashView的交互监听器
                ad.setSplashInteractionListener(new TTSplashAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int type) {
                        onAdSplashListener.adClick();
                    }

                    @Override
                    public void onAdShow(View view, int type) {
                    }

                    @Override
                    public void onAdSkip() {
                    }

                    @Override
                    public void onAdTimeOver() {
                    }
                });
                int temp = AdsRecordUtils.getInstance().getInt(Constants.AD_SPLASH + name.getAdverName());
                temp++;
                AdsRecordUtils.getInstance().putInt(Constants.AD_SPLASH + name.getAdverName(), temp);
                onAdSplashListener.adSuccess();

                //保存广告日志
                AdsLogUpUtils.saveAds(name);
            }
        }, AD_TIME_OUT);
    }
}
