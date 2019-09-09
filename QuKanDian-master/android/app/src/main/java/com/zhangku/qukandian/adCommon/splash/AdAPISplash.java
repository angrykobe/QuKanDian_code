package com.zhangku.qukandian.adCommon.splash;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.adCommon.util.AdUtil;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.NativeAdInfo;
import com.zhangku.qukandian.interfaces.OnAdNativeRequestListener;
import com.zhangku.qukandian.interfaces.OnAdSplashListener;
import com.zhangku.qukandian.utils.GlideUtils;

import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2019/3/11
 * api欢迎页
 */
public class AdAPISplash extends AdSplashRequest {

    private AdLocationBeans.AdLocationsBean mBbean;

    public AdAPISplash(Context context, AdLocationBeans.AdLocationsBean bean) {
        super(context);
        mBbean = bean;
    }

    @Override
    public void getAdData(final ViewGroup view, final ViewGroup skipView, AdLocationBeans.AdLocationsBean.ClientAdvertisesBean clientAdvertisesBean, final OnAdSplashListener onAdSplashListener) {
        AdUtil.requestAdContent(mContext, mBbean, clientAdvertisesBean, new OnAdNativeRequestListener() {
            @Override
            public void onResponse(List list, int adType) {
                if (list != null && list.size() > 0 && view instanceof FrameLayout) {
                    if (onAdSplashListener != null)
                        onAdSplashListener.adSuccess();
                    FrameLayout frameLayout = (FrameLayout) view;
                    final NativeAdInfo nativeAdInfo = (NativeAdInfo) list.get(0);
                    GlideUtils.displayImageForBg(view.getContext(), nativeAdInfo.getImageUrl(), frameLayout);
                    nativeAdInfo.onDisplay(mContext, frameLayout);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            nativeAdInfo.onClick(mContext, v, nativeAdInfo.getAdsRuleBean());
                            if (onAdSplashListener != null)
                                onAdSplashListener.adClick();
                        }
                    });
                } else {
                    if (onAdSplashListener != null)
                        onAdSplashListener.adFail();
                }
            }

            @Override
            public void onFail() {
                try{
                    new Handler(mContext.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            AdLocationBeans.AdLocationsBean.ClientAdvertisesBean clientAdvertisesBean = new AdLocationBeans.AdLocationsBean.ClientAdvertisesBean();
                            clientAdvertisesBean.setPageIndex(mBbean.getPageIndex());
                            clientAdvertisesBean.setAdverName("百度");
                            new AdMyBaiduSplash(mContext).getAdData(view, skipView, clientAdvertisesBean, onAdSplashListener);
                        }
                    });
                }catch (Exception e){
//                    MobclickAgent.reportError(mContext, e.toString() + " \n\n AdAPISplash 73");
                }
            }
        });

    }
}
