package com.zhangku.qukandian.adCommon.natives;

import android.content.Context;
import android.text.TextUtils;

import com.hmob.hmsdk.ads.nativ.HMNative;
import com.hmob.hmsdk.ads.nativ.NativeListener;
import com.hmob.hmsdk.entity.NativeResource;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.NativeAdInfo;
import com.zhangku.qukandian.biz.adcore.AdConfig;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.interfaces.OnAdNativeRequestListener;
import com.zhangku.qukandian.utils.CommonHelper;

import java.util.ArrayList;
import java.util.List;

public class AdYitanNative extends AdRequestBase {

    public AdYitanNative(Context context, @AnnoCon.AdverFrom String from) {
        super(context, from);
    }

    @Override
    public void getAdData(final AdLocationBeans.AdLocationsBean adLocationsBean, final AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean, final OnAdNativeRequestListener onAdNativeRequestListener) {
        String adsCode = adsRuleBean.getAdsCode();
        String s = TextUtils.isEmpty(adsCode) ? AdConfig.yitan_codeid : adsRuleBean.getAdsCode();
        //1、创建广告对象
        HMNative hmNative = new HMNative(mContext, Integer.valueOf(s), new NativeListener() {
            @Override
            public void onADReceived(List<NativeResource> list) {
                if (list == null || list.size() == 0) return;
                NativeResource nativeResource = list.get(0);

                int adlocId = adLocationsBean.getId();//广告位id
                int index = adLocationsBean.getPageIndex();//广告在列表的位置
                String belong = adsRuleBean.getBelong();//广告位置 埋点
                int clickGold = adsRuleBean.getClickGold();//点击金币

                List<NativeAdInfo> list1 = new ArrayList();
                NativeAdInfo bean = new NativeAdInfo();
                bean.setAdlocId(adlocId);
                bean.setOrigin(nativeResource);
                bean.setClickGold(clickGold);
                bean.setAdsRuleBean(adsRuleBean);
                bean.setAdFromName(from);
                bean.setIndex(index);
                bean.setBelong(belong);
                bean.setImageUrls(nativeResource.getImage());
                bean.setCurrentData(CommonHelper.formatTimeYMD(System.currentTimeMillis(), true));
                bean.setTitle(nativeResource.getTitle());
                bean.setDescription(nativeResource.getDescription());
                if (nativeResource.getImage() != null && nativeResource.getImage().size() > 0)
                    bean.setImageUrl(nativeResource.getImage().get(0));
                list1.add(bean);
                onAdNativeRequestListener.onResponse(list1, 2);
            }

            @Override
            public void onNoAD(com.hmob.hmsdk.ads.AdError adError) {
                onAdNativeRequestListener.onFail();
            }

            @Override
            public void onXXListener(int i) {

            }
        });
        //2、加载广告，可预加载多条，范围1-5，可能有重复广告
        hmNative.load(1);
    }
}
