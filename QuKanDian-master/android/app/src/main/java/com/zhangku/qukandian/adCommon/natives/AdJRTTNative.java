package com.zhangku.qukandian.adCommon.natives;

import android.content.Context;
import android.text.TextUtils;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.NativeAdInfo;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.interfaces.OnAdNativeRequestListener;
import com.zhangku.qukandian.utils.CommonHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzuoning on 2017/12/28.
 */

public class AdJRTTNative extends AdRequestBase {

    public AdJRTTNative(Context context, @AnnoCon.AdverFrom String from) {
        super(context, from);
    }

    @Override
    public void getAdData(final AdLocationBeans.AdLocationsBean adLocationsBean, final AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean, final OnAdNativeRequestListener onAdNativeRequestListener) {
        String adsCode = adsRuleBean.getAdsCode();
        String s = TextUtils.isEmpty(adsCode) ? "901141542" : adsRuleBean.getAdsCode();
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(s)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(640, 320)
                .setAdCount(1)
                .build();
        QuKanDianApplication.ttAdManager.createAdNative(mContext).loadFeedAd(adSlot, new TTAdNative.FeedAdListener() {
            @Override
            public void onError(int code, String message) {
                if (null != onAdNativeRequestListener) {
                    onAdNativeRequestListener.onResponse(new ArrayList(), 2);
                }
            }

            @Override
            public void onFeedAdLoad(List<TTFeedAd> list) {
                if (list == null || list.isEmpty()) {
                    if (null != onAdNativeRequestListener) {
                        onAdNativeRequestListener.onResponse(new ArrayList(), 2);
                    }
                    return;
                }
                if (null != onAdNativeRequestListener) {
                    List<NativeAdInfo> list1 = new ArrayList();
                    int adlocId = adLocationsBean.getId();//广告位id
                    int index = adLocationsBean.getPageIndex();//广告在列表的位置
                    String belong = adsRuleBean.getBelong();//广告位置 埋点
                    int clickGold = adsRuleBean.getClickGold();//点击金币
                    for (int i = 0; i < list.size(); i++) {
                        NativeAdInfo bean = new NativeAdInfo();
                        TTFeedAd response = list.get(i);
                        // TODO　response.getAdLogo();　　
                        bean.setAdlocId(adlocId);
                        bean.setOrigin(response);
                        bean.setClickGold(clickGold);
                        bean.setAdsRuleBean(adsRuleBean);
                        bean.setAdFromName(from);
                        bean.setIndex(index);
                        bean.setBelong(belong);
                        if(null != response.getImageList() && response.getImageList().size() > 0){
                            bean.setImageUrl(response.getImageList().get(0).getImageUrl());
                            //默认单图模式
                            List<String> imgs = new ArrayList<>();
                            imgs.add(response.getImageList().get(0).getImageUrl());
                            bean.setImageUrls(imgs);
                        }

//                        if (null != response.getImageList() && response.getImageList().size() > 0) {
//                            for (int j = 0; j < response.getImageList().size(); j++) {
//                                imgs.add(response.getImageList().get(j).getImageUrl());
//                            }
//                            if (imgs.size() > 1) {
//                                bean.setImageUrls(imgs);
//                            }
//                        }
                        bean.setCurrentData(CommonHelper.formatTimeYMD(System.currentTimeMillis(), true));
                        bean.setDescription(response.getDescription());
                        bean.setIconUrl(response.getIcon().getImageUrl());
                        bean.setTitle(response.getTitle());
                        list1.add(bean);
                    }
                    onAdNativeRequestListener.onResponse(list1, 2);
                }
            }
        });
    }
}
