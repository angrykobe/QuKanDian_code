package com.zhangku.qukandian.adCommon.banner;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.felink.adSdk.AdListener;
import com.felink.adSdk.BannerAD;
import com.zhangku.qukandian.biz.adcore.AdConfig;

/**
 * author : 王锋
 * e-mail : wangfengandroid@163.com
 * date   : 2019/7/1718:59
 * desc   :
 */

public class AdFelinkBanner extends AdBannerRequest {
    public AdFelinkBanner(Context context) {
        super(context);
    }

    @Override
    public void getAdData(ViewGroup view) {
        AdListener bannerAdListener = new AdListener() {

            @Override
            public void onAdPresent() {

            }

            @Override
            public void onAdDismissed() {

            }

            @Override
            public void onAdFailed(String msg) {
                Log.e("xxx", " onAdFailed: " + msg);
                //Toast.makeText( mContext, "加载广告失败" +msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClick() {

            }

            @Override
            public boolean onFelinkAdClickCallBack(String url, Object jsonObject) {

                return false;
            }
        };
        new BannerAD((Activity)mContext, bannerAdListener,view, AdConfig.felink_bannerid);
    }
}
