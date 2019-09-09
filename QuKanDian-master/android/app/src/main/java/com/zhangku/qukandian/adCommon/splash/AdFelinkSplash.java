package com.zhangku.qukandian.adCommon.splash;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.felink.adSdk.AdListener;
import com.felink.adSdk.SplashAD;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.biz.adcore.AdConfig;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.interfaces.OnAdSplashListener;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.AdsLogUpUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.AdsRecordUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

/**
 * author : 王锋
 * e-mail : wangfengandroid@163.com
 * date   : 2019/7/1717:09
 * desc   :
 */

public class AdFelinkSplash extends AdSplashRequest {

    public AdFelinkSplash(Context context) {
        super(context);
    }

    @Override
    public void getAdData(ViewGroup view, final ViewGroup skipView, final AdLocationBeans.AdLocationsBean.ClientAdvertisesBean name, final OnAdSplashListener onAdSplashListener) {
        AdListener splashAdListener = new AdListener() {

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
            public void onAdDismissed() {

            }

            @Override
            public void onAdFailed(String msg) {
                Log.e("xxx", " onAdFailed: " + msg);
                //Toast.makeText( mContext, "加载广告失败" +msg, Toast.LENGTH_SHORT).show();
                onAdSplashListener.adFail();
            }

            @Override
            public void onAdClick() {

            }

            @Override
            public boolean onFelinkAdClickCallBack(String url, Object jsonObject) {
                return false;
            }
        };
        new SplashAD((Activity) mContext, splashAdListener,view, AdConfig.felink_spalshid);
    }
}
