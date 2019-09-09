package com.zhangku.qukandian.adCommon.natives;

import android.content.Context;
import android.text.TextUtils;

import com.qq.e.ads.nativ.NativeAD;
import com.qq.e.ads.nativ.NativeADDataRef;
import com.qq.e.comm.util.AdError;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.NativeAdInfo;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.interfaces.OnAdNativeRequestListener;
import com.zhangku.qukandian.utils.CommonHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzuoning on 2017/10/17.
 */

public class AdGdtNative extends AdRequestBase {
    private NativeAD nativeAD;
    private int mCount = 2;

    public AdGdtNative(Context context, @AnnoCon.AdverFrom String from) {
        super(context, from);
    }

    @Override
    public void getAdData(final AdLocationBeans.AdLocationsBean adLocationsBean, final AdLocationBeans.AdLocationsBean.ClientAdvertisesBean clientBean,final OnAdNativeRequestListener onAdNativeRequestListener) {
        if(null == nativeAD){
            String adsCode = clientBean.getAdsCode();
            String s = TextUtils.isEmpty(adsCode) ? "6070628497792355" : clientBean.getAdsCode();
            nativeAD = new NativeAD(mContext, "1106304292", s, new NativeAD.NativeAdListener() {
                @Override
                public void onADLoaded(List<NativeADDataRef> list) {
                    if (null != onAdNativeRequestListener) {
                        int adlocId = adLocationsBean.getId();//广告位id
                        int index = adLocationsBean.getPageIndex();//广告在列表的位置
                        String name = clientBean.getAdverName();//广告名称
                        String belong = clientBean.getBelong();//广告位置 埋点
                        int clickGold = clientBean.getClickGold();//点击金币

                        List<NativeAdInfo> list1 = new ArrayList();
                        if(null != list){
                            for (int i = 0; i < list.size(); i++) {
                                NativeAdInfo bean = new NativeAdInfo();
                                NativeADDataRef response = list.get(i);
                                bean.setAdlocId(adlocId);
                                bean.setAdsRuleBean(clientBean);
                                bean.setClickGold(clickGold);
                                bean.setOrigin(response);
                                bean.setAdFromName(from);
                                bean.setImageUrls(response.getImgList());
                                bean.setIndex(index);
                                bean.setBelong(belong);
                                bean.setCurrentData(CommonHelper.formatTimeYMD(System.currentTimeMillis(),true));
                                bean.setDescription(response.getDesc());
                                bean.setIconUrl(response.getIconUrl());
                                bean.setImageUrl(response.getImgUrl());
                                bean.setTitle(response.getTitle());
                                list1.add(bean);
                            }
                        }
                        onAdNativeRequestListener.onResponse(list1,2);
                    }
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
                    onAdNativeRequestListener.onFail();
                }
            });
        }
        nativeAD.loadAD(mCount);
    }
}
