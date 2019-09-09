package com.zhangku.qukandian.adCommon.splash;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashAdListener;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.interfaces.OnAdSplashListener;
import com.zhangku.qukandian.utils.AdsLogUpUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.AdsRecordUtils;

/**
 * Created by yuzuoning on 2017/10/17.
 */

public class AdMyBaiduSplash extends AdSplashRequest {
    public AdMyBaiduSplash(Context context) {
        super(context);
    }

    @Override
    public void getAdData(ViewGroup view, ViewGroup skipView, final AdLocationBeans.AdLocationsBean.ClientAdvertisesBean name, final OnAdSplashListener onAdSplashListener) {
        SplashAdListener listener = new SplashAdListener() {
            @Override
            public void onAdDismissed() {
            }

            @Override
            public void onAdFailed(String arg0) {
                onAdSplashListener.adFail();
            }

            @Override
            public void onAdPresent() {
                int temp = AdsRecordUtils.getInstance().getInt(Constants.AD_SPLASH + name.getAdverName());
                temp++;
                AdsRecordUtils.getInstance().putInt(Constants.AD_SPLASH + name.getAdverName(), temp);
                onAdSplashListener.adSuccess();

                //保存广告日志
                AdsLogUpUtils.saveAds(name);
            }

            @Override
            public void onAdClick() {
                onAdSplashListener.adClick();
            }
        };
        String adsCode = name.getAdsCode();
        String s = TextUtils.isEmpty(adsCode) ? "4338626" : name.getAdsCode();//重要：请填上您的广告位ID，代码位错误会导致无法请求到广告
        new SplashAd(mContext, view, listener, s, true);
    }
}
