package com.zhangku.qukandian.adCommon.splash;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;

import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.util.AdError;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.interfaces.OnAdSplashListener;
import com.zhangku.qukandian.utils.AdsLogUpUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.AdsRecordUtils;

/**
 * Created by yuzuoning on 2017/10/17.
 */

public class AdGdtSplash extends AdSplashRequest {
    private SplashAD splashAD;

    public AdGdtSplash(Context context) {
        super(context);
    }

    @Override
    public void getAdData(ViewGroup view, final ViewGroup skipView, final AdLocationBeans.AdLocationsBean.ClientAdvertisesBean name, final OnAdSplashListener onAdSplashListener) {
        splashAD = new SplashAD((Activity) mContext, view, skipView, "1106304292", "1010424474759657", new SplashADListener() {
            @Override
            public void onADDismissed() {
            }

            @Override
            public void onNoAD(AdError adError) {
                onAdSplashListener.adFail();
            }

            @Override
            public void onADPresent() {
                int temp = AdsRecordUtils.getInstance().getInt(Constants.AD_SPLASH + name.getAdverName());
                temp++;
                AdsRecordUtils.getInstance().putInt(Constants.AD_SPLASH + name.getAdverName(), temp);
                onAdSplashListener.adSuccess();

                //保存广告日志
                AdsLogUpUtils.saveAds(name);
            }

            @Override
            public void onADClicked() {
                onAdSplashListener.adClick();
            }

            @Override
            public void onADTick(long l) {

            }

            @Override
            public void onADExposure() {

            }
        }, 0);
    }


}
