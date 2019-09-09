package com.zhangku.qukandian.adCommon.natives;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.NativeAdInfo;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.interfaces.OnAdNativeRequestListener;
import com.zhangku.qukandian.utils.CommonHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import cn.jiguang.adsdk.api.AdError;
import cn.jiguang.adsdk.api.nati.NativeAD;
import cn.jiguang.adsdk.api.nati.NativeADDataRef;
import cn.jiguang.adsdk.api.nati.NativeAdListener;

public class AdJiguangNative extends AdRequestBase {

    public AdJiguangNative(Context context, @AnnoCon.AdverFrom String from) {
        super(context, from);
    }

    @Override
    public void getAdData(final AdLocationBeans.AdLocationsBean adLocationsBean, final AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean, final OnAdNativeRequestListener onAdNativeRequestListener) {
        String adsCode = adsRuleBean.getAdsCode();
        String s = TextUtils.isEmpty(adsCode) ? "0852466151718481" : adsRuleBean.getAdsCode();
        if(!(mContext instanceof Activity)) return;
        NativeAD nativeAD = new NativeAD((Activity) mContext, s, new NativeAdListener() {
            @Override
            public void onADLoaded(List<NativeADDataRef> list) {
                if (list == null || list.size() == 0) return;
                NativeADDataRef nativeADDataRef = list.get(0);

                int adlocId = adLocationsBean.getId();//广告位id
                int index = adLocationsBean.getPageIndex();//广告在列表的位置
                String belong = adsRuleBean.getBelong();//广告位置 埋点
                int clickGold = adsRuleBean.getClickGold();//点击金币

                List<NativeAdInfo> list1 = new ArrayList();
                NativeAdInfo bean = new NativeAdInfo();
                bean.setAdlocId(adlocId);
                bean.setOrigin(nativeADDataRef);
                bean.setClickGold(clickGold);
                bean.setAdsRuleBean(adsRuleBean);
                bean.setAdFromName(from);
                bean.setIndex(index);
                bean.setBelong(belong);
                List<String> resultList = new ArrayList<>(nativeADDataRef.getImgUrls().length);
                Collections.addAll(resultList, nativeADDataRef.getImgUrls());
                bean.setImageUrls(resultList);
                bean.setCurrentData(CommonHelper.formatTimeYMD(System.currentTimeMillis(), true));
                bean.setTitle(nativeADDataRef.getTitle());
                bean.setDescription(nativeADDataRef.getDesc());
                if (resultList != null && resultList.size() > 0)
                    bean.setImageUrl(resultList.get(0));
                list1.add(bean);
                onAdNativeRequestListener.onResponse(list1, 2);
            }

            @Override
            public void onNoAD(AdError adError) {
                onAdNativeRequestListener.onFail();
            }

            @Override
            public void onADStatusChanged(NativeADDataRef nativeADDataRef) {

            }

            @Override
            public void onADError(NativeADDataRef nativeADDataRef, AdError adError) {

            }
        });
        nativeAD.loadAD(1);
    }
}
