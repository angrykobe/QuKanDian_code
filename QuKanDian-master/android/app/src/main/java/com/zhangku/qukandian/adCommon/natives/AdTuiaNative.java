package com.zhangku.qukandian.adCommon.natives;

import android.content.Context;
import android.text.TextUtils;

import com.db.ta.sdk.NonStandardTm;
import com.db.ta.sdk.NsTmListener;
import com.google.gson.Gson;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.AdTuiABean;
import com.zhangku.qukandian.bean.NativeAdInfo;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.interfaces.OnAdNativeRequestListener;
import com.zhangku.qukandian.utils.CommonHelper;

import java.util.ArrayList;

/**
 * Created by yuzuoning on 2018/4/10.
 */

public class AdTuiaNative extends AdRequestBase {
    private NonStandardTm mNonStandardTm;

    public AdTuiaNative(Context context, @AnnoCon.AdverFrom String from) {
        super(context, from);
    }

    @Override
    public void getAdData(final AdLocationBeans.AdLocationsBean adLocationsBean, final AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean, final OnAdNativeRequestListener onAdNativeRequestListener) {
        String adsCode = adsRuleBean.getAdsCode();
        String s = TextUtils.isEmpty(adsCode) ? "9270" : adsRuleBean.getAdsCode();
        mNonStandardTm = new NonStandardTm(mContext);
        mNonStandardTm.loadAd(Integer.valueOf(s));
        mNonStandardTm.setAdListener(new NsTmListener() {
            @Override
            public void onReceiveAd(String result) {
                int adlocId = adLocationsBean.getId();//广告位id
                int index = adLocationsBean.getPageIndex();//广告在列表的位置
                String belong = adsRuleBean.getBelong();//广告位置 埋点
                int clickGold = adsRuleBean.getClickGold();//点击金币
                Gson gson = new Gson();
                AdTuiABean bean1 = gson.fromJson(result, AdTuiABean.class);
                ArrayList<NativeAdInfo> nativeAdInfos = new ArrayList<>();
                NativeAdInfo nativeAdInfo = new NativeAdInfo();
                nativeAdInfo.setNonStandardTm(mNonStandardTm);
                nativeAdInfo.setIndex(index);
                nativeAdInfo.setAdlocId(adlocId);
                nativeAdInfo.setAdsRuleBean(adsRuleBean);
                nativeAdInfo.setClickGold(clickGold);
                nativeAdInfo.setOrigin(bean1);
                nativeAdInfo.setAdFromName(from);
                nativeAdInfo.setIndex(index);
                nativeAdInfo.setBelong(belong);
                nativeAdInfo.setCurrentData(CommonHelper.formatTimeYMD(System.currentTimeMillis(), true));
                nativeAdInfo.setDescription(bean1.getDescription());
                nativeAdInfo.setIconUrl(bean1.getAd_icon());
                nativeAdInfo.setImageUrl(bean1.getImg_url());
                nativeAdInfo.setTitle(bean1.getAd_title());
                nativeAdInfos.add(nativeAdInfo);
                onAdNativeRequestListener.onResponse(nativeAdInfos, 2);
            }

            @Override
            public void onFailedToReceiveAd() {
                onAdNativeRequestListener.onFail();
            }
        });
    }
}
