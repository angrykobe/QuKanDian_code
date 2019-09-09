package com.zhangku.qukandian.adCommon.splash;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.hmob.hmsdk.ads.AdError;
import com.hmob.hmsdk.ads.splash.HMSplash;
import com.hmob.hmsdk.ads.splash.SplashListener;
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
 * Created by yuzuoning on 2017/10/17.
 */

public class AdYitanSplash extends AdSplashRequest {
    public AdYitanSplash(Context context) {
        super(context);
    }

    @Override
    public void getAdData(ViewGroup view, final ViewGroup skipView, final AdLocationBeans.AdLocationsBean.ClientAdvertisesBean name, final OnAdSplashListener onAdSplashListener) {
        /**
         * @param placementId    开屏广告位id
         * @param container      要展示广告的容器,container的parent必须是ViewGroup
         * @param displayTime    展示广告时长：取值范围[3000, 7000],设为0表示使用默认时长
         * @param splashListener 广告监听回调接口
         */
        String adsCode = name.getAdsCode();
        String s = TextUtils.isEmpty(adsCode) ? AdConfig.yitan_splash_codeid : name.getAdsCode();
        //1、创建广告对象
        HMSplash hmSplash = new HMSplash(Integer.valueOf(s), view, 5000, new SplashListener() {
            /**
             * 广告加载失败，error对象包含了错误码和错误信息，错误码的详细内容可以参考文档
             */
            @Override
            public void onNoAd(AdError error) {
                onAdSplashListener.adFail();
            }

            /**
             * @param prior 是否优先展示
             * @return 是否立即展示，无特殊需求请返回true
             */
            @Override
            public boolean onReceived(boolean prior) {
                return true;
            }

            /**
             * 广告成功展示时调用，成功展示不等于满足计费条件（如展示时长尚未满足）
             */
            @Override
            public void onADPresent() {
                int temp = AdsRecordUtils.getInstance().getInt(Constants.AD_SPLASH + name.getAdverName());
                temp++;
                AdsRecordUtils.getInstance().putInt(Constants.AD_SPLASH + name.getAdverName(), temp);
                onAdSplashListener.adSuccess();

                //保存广告日志
                AdsLogUpUtils.saveAds(name);
            }

            /**
             * 广告关闭时调用，可能是用户关闭、展示时间到或用户点击了广告。
             * 此时一般需要跳过开屏的 Activity，进入应用内容页面
             */
            @Override
            public void onADDismissed() {
                if (skipView.getVisibility() != View.VISIBLE) {
                    //判断是否第一只登录，或者已经登录，或者登录超过14填
                    if (UserSharedPreferences.getInstance().getBoolean(Constants.FIRST_LOGIN, true)) {
                        ActivityUtils.startToGuideActivity(mContext);
                    } else if (UserManager.getInst().hadLogin()
                            && (System.currentTimeMillis() - UserSharedPreferences.getInstance().getLong(Constants.TOKEN_RECORD_IN)) / 1000 / 60 / 60 / 24 > 14) {
                        UserManager.getInst().logout(mContext);
                    } else {
                        ActivityUtils.startToMainActivity(mContext, Constants.TAB_INFORMATION, 0);
                    }
                }
            }

            /**
             * 广告被点击时调用，不代表满足计费条件（如点击时网络异常）
             */
            @Override
            public void onADClicked() {
                onAdSplashListener.adClick();
            }

            /**
             * 倒计时回调，返回广告还将被展示的剩余时间。
             * @param time 剩余毫秒数
             */
            @Override
            public void onADTick(int time) {
            }
        });
        //2、加载广告
        hmSplash.load(mContext);
    }
}
