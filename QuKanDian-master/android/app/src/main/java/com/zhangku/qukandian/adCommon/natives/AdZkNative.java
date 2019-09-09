package com.zhangku.qukandian.adCommon.natives;

import android.content.Context;
import android.os.Handler;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.NativeAdInfo;
import com.zhangku.qukandian.biz.adbeen.NiceNativeAdDataBeen;
import com.zhangku.qukandian.biz.adcore.ZkNativeAd;
import com.zhangku.qukandian.biz.adcore.NativeAdLoaderListener;
import com.zhangku.qukandian.biz.adcore.changjiang.ChangjiangNativeAdLoader;
import com.zhangku.qukandian.biz.adcore.changjiang.ChangjiangZkNativeAd;
import com.zhangku.qukandian.biz.adcore.huitoutiao.HuitoutiaoNativeAdLoader;
import com.zhangku.qukandian.biz.adcore.huitoutiao.HuitoutiaoZkNativeAd;
import com.zhangku.qukandian.biz.adcore.huzhong.HuzhongNativeAdLoader;
import com.zhangku.qukandian.biz.adcore.huzhong.HuzhongZkNativeAd;
import com.zhangku.qukandian.biz.adcore.maiyou.MaiyouNativeAdLoader;
import com.zhangku.qukandian.biz.adcore.maiyou.MaiyouZkNativeAd;
import com.zhangku.qukandian.biz.adcore.ruangao.RuangaoNativeAdLoader;
import com.zhangku.qukandian.biz.adcore.ruangao.RuangaoZkNativeAd;
import com.zhangku.qukandian.biz.adcore.saibo.SaiboNativeAdLoader;
import com.zhangku.qukandian.biz.adcore.saibo.SaiboZkNativeAd;
import com.zhangku.qukandian.biz.adcore.vlion.VlionNativeAdLoader;
import com.zhangku.qukandian.biz.adcore.vlion.VlionZkNativeAd;
import com.zhangku.qukandian.biz.adcore.yuemeng.YueMengNativeAdLoader;
import com.zhangku.qukandian.biz.adcore.yuemeng.YueMengZkNativeAd;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.interfaces.OnAdNativeRequestListener;
import com.zhangku.qukandian.utils.CommonHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 掌酷自主原生广告
 */
public class AdZkNative {
    private Context mContext;
    private String adFrom;

    private Handler mainHandler;

    public AdZkNative(Context context, @AnnoCon.AdverFrom String adFrom) {
        this.mContext = context;
        this.adFrom = adFrom;
        if (context != null) {
            this.mainHandler = new Handler(context.getMainLooper());
        }
    }

    /**
     * @param mAdvStr
     * @param adsRuleBean 广告实体类，主要是获取广告位id
     * @param listener
     */
    public void loadAds(@AnnoCon.AdverFrom String mAdvStr, AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean, NativeAdLoaderListener listener) {
        switch (mAdvStr) {
            case AnnoCon.AD_TYPT_HUITOUTIAO:
                new HuitoutiaoNativeAdLoader().loadAds(listener, adsRuleBean);
                break;
            case AnnoCon.AD_TYPT_VLION:
                new VlionNativeAdLoader().loadAds(listener, adsRuleBean);
                break;
            case AnnoCon.AD_TYPT_HUZHONG:
                new HuzhongNativeAdLoader().loadAds(mContext, adsRuleBean, listener);
                break;
            case AnnoCon.AD_TYPT_YUEMENG:
                new YueMengNativeAdLoader().loadAds(adsRuleBean, listener);
                break;
            case AnnoCon.AD_TYPT_CHANGJIAN:
                new ChangjiangNativeAdLoader().loadAds(adsRuleBean, listener);
                break;
            case AnnoCon.AD_TYPT_SHAIBO:
                new SaiboNativeAdLoader().loadAds(adsRuleBean, listener);
                break;
            case AnnoCon.AD_TYPT_RUANGAO:
                new RuangaoNativeAdLoader().loadAds(adsRuleBean, listener);
                break;
            case AnnoCon.AD_TYPT_MAIYOU:
                new MaiyouNativeAdLoader().loadAds(adsRuleBean, listener);
                break;
        }
    }

    /**
     * @param adsRuleBean
     * @param onAdNativeRequestListener
     */
    public void getAdData(final AdLocationBeans.AdLocationsBean adLocationsBean, final AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean, final OnAdNativeRequestListener onAdNativeRequestListener) {
        try {
            loadAds(adFrom, adsRuleBean, new NativeAdLoaderListener() {
                @Override
                public void onAdLoadSuccess(ArrayList<ZkNativeAd> zkNativeAds) {
                    final List<NativeAdInfo> nativeAdInfos = new ArrayList();
                    try {
                        if (zkNativeAds != null && zkNativeAds.size() > 0) {
                            int adlocId = adLocationsBean.getId();//广告位id
                            int index = adLocationsBean.getPageIndex();//广告在列表的位置
                            String name = adsRuleBean.getAdverName();//广告名称
                            String belong = adsRuleBean.getBelong();//广告位置 埋点
                            int clickGold = adsRuleBean.getClickGold();//点击金币
                            for (int i = 0; i < zkNativeAds.size(); i++) {
                                NativeAdInfo bean = new NativeAdInfo();
                                ZkNativeAd zkNativeAd = zkNativeAds.get(i);
                                bean.setAdlocId(adlocId);
                                bean.setOrigin(zkNativeAd);
                                bean.setClickGold(clickGold);
                                bean.setAdsRuleBean(adsRuleBean);
                                bean.setIndex(index);
                                bean.setBelong(belong);
                                bean.setCurrentData(CommonHelper.formatTimeYMD(System.currentTimeMillis(), true));
                                bean.setAdFromName(adFrom);

                                Map<String, String> map = new HashMap<>();
                                if (zkNativeAd instanceof HuitoutiaoZkNativeAd) {
                                    map.put("AD_TYPT_HUITOUTIAO", "success_" + belong + "_" + index);
                                    HuitoutiaoZkNativeAd nativeAdNice = (HuitoutiaoZkNativeAd) zkNativeAd;
                                    if (nativeAdNice != null && nativeAdNice.getNiceDataBeen() != null) {
                                        NiceNativeAdDataBeen niceNativeAdDataBeen = nativeAdNice.getNiceDataBeen();

                                        bean.setTemplateType(niceNativeAdDataBeen.getTemplateType());
                                        bean.setImageUrl(niceNativeAdDataBeen.getImageUrl());
                                        bean.setImageUrls(niceNativeAdDataBeen.getImageUrls());
                                        bean.setDescription(niceNativeAdDataBeen.getDescription());
                                        bean.setIconUrl(niceNativeAdDataBeen.getIconUrl());
                                        bean.setTitle(niceNativeAdDataBeen.getTitle());
                                    }
                                } else if (zkNativeAd instanceof VlionZkNativeAd) {
                                    map.put("AD_TYPT_VLION", "success_" + belong + "_" + index);
                                    VlionZkNativeAd nativeAdNice = (VlionZkNativeAd) zkNativeAd;
                                    NiceNativeAdDataBeen niceDataBeen = nativeAdNice.getNiceDataBeen();

                                    bean.setTemplateType(niceDataBeen.getTemplateType());
                                    bean.setImageUrl(niceDataBeen.getImageUrl());
                                    bean.setImageUrls(niceDataBeen.getImageUrls());
                                    bean.setDescription(niceDataBeen.getDescription());
                                    bean.setIconUrl(niceDataBeen.getIconUrl());
                                    bean.setTitle(niceDataBeen.getTitle());
                                } else if (zkNativeAd instanceof HuzhongZkNativeAd) {
                                    map.put("AD_TYPT_HUZHONG", "success_" + belong + "_" + index);
                                    HuzhongZkNativeAd nativeAdNice = (HuzhongZkNativeAd) zkNativeAd;
                                    NiceNativeAdDataBeen niceDataBeen = nativeAdNice.getNiceDataBeen();

                                    bean.setTemplateType(niceDataBeen.getTemplateType());
                                    bean.setImageUrl(niceDataBeen.getImageUrl());
                                    bean.setImageUrls(niceDataBeen.getImageUrls());
                                    bean.setDescription(niceDataBeen.getDescription());
                                    bean.setIconUrl(niceDataBeen.getIconUrl());
                                    bean.setTitle(niceDataBeen.getTitle());
                                } else if (zkNativeAd instanceof YueMengZkNativeAd) {
                                    map.put("AD_TYPT_YUEMENG", "success_" + belong + "_" + index);
                                    YueMengZkNativeAd nativeAdNice = (YueMengZkNativeAd) zkNativeAd;
                                    NiceNativeAdDataBeen niceDataBeen = nativeAdNice.getNiceDataBeen();

                                    bean.setTemplateType(niceDataBeen.getTemplateType());
                                    bean.setImageUrl(niceDataBeen.getImageUrl());
                                    bean.setImageUrls(niceDataBeen.getImageUrls());
                                    bean.setDescription(niceDataBeen.getDescription());
                                    bean.setIconUrl(niceDataBeen.getIconUrl());
                                    bean.setTitle(niceDataBeen.getTitle());
                                } else if (zkNativeAd instanceof ChangjiangZkNativeAd) {
                                    map.put("AD_TYPT_CHANGJIAN", "success_" + belong + "_" + index);
                                    ChangjiangZkNativeAd nativeAdNice = (ChangjiangZkNativeAd) zkNativeAd;
                                    NiceNativeAdDataBeen niceDataBeen = nativeAdNice.getNiceDataBeen();

                                    bean.setTemplateType(niceDataBeen.getTemplateType());
                                    bean.setImageUrl(niceDataBeen.getImageUrl());
                                    bean.setImageUrls(niceDataBeen.getImageUrls());
                                    bean.setDescription(niceDataBeen.getDescription());
                                    bean.setIconUrl(niceDataBeen.getIconUrl());
                                    bean.setTitle(niceDataBeen.getTitle());
                                } else if (zkNativeAd instanceof SaiboZkNativeAd) {
                                    map.put("AD_TYPT_SHAIBO", "success_" + belong + "_" + index);
                                    SaiboZkNativeAd nativeAdNice = (SaiboZkNativeAd) zkNativeAd;
                                    NiceNativeAdDataBeen niceDataBeen = nativeAdNice.getNiceDataBeen();

                                    bean.setTemplateType(niceDataBeen.getTemplateType());
                                    bean.setImageUrl(niceDataBeen.getImageUrl());
                                    bean.setImageUrls(niceDataBeen.getImageUrls());
                                    bean.setDescription(niceDataBeen.getDescription());
                                    bean.setIconUrl(niceDataBeen.getIconUrl());
                                    bean.setTitle(niceDataBeen.getTitle());
                                } else if (zkNativeAd instanceof RuangaoZkNativeAd) {
                                    map.put("AD_TYPT_RUANGAO", "success_" + belong + "_" + index);
                                    RuangaoZkNativeAd nativeAdNice = (RuangaoZkNativeAd) zkNativeAd;
                                    NiceNativeAdDataBeen niceDataBeen = nativeAdNice.getNiceDataBeen();

                                    bean.setTemplateType(niceDataBeen.getTemplateType());
                                    bean.setImageUrl(niceDataBeen.getImageUrl());
                                    bean.setImageUrls(niceDataBeen.getImageUrls());
                                    bean.setDescription(niceDataBeen.getDescription());
                                    bean.setIconUrl(niceDataBeen.getIconUrl());
                                    bean.setTitle(niceDataBeen.getTitle());
                                } else if (zkNativeAd instanceof MaiyouZkNativeAd) {
                                    map.put("AD_TYPT_MAIYOU", "success_" + belong + "_" + index);
                                    MaiyouZkNativeAd nativeAdNice = (MaiyouZkNativeAd) zkNativeAd;
                                    NiceNativeAdDataBeen niceDataBeen = nativeAdNice.getNiceDataBeen();

                                    bean.setTemplateType(niceDataBeen.getTemplateType());
                                    bean.setImageUrl(niceDataBeen.getImageUrl());
                                    bean.setImageUrls(niceDataBeen.getImageUrls());
                                    bean.setDescription(niceDataBeen.getDescription());
                                    bean.setIconUrl(niceDataBeen.getIconUrl());
                                    bean.setTitle(niceDataBeen.getTitle());
                                }
                                ////////////////////统计  290
                                MobclickAgent.onEvent(mContext, "AdRequestCount", map);

                                nativeAdInfos.add(bean);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (mContext != null && mainHandler == null) {
                        mainHandler = new Handler(mContext.getMainLooper());
                    }
                    if (mainHandler != null) {
                        // 回到主线程，更新UI
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onAdNativeRequestListener.onResponse(nativeAdInfos, 2);
                            }
                        });
                    }
                }

                @Override
                public void onAdLoadFailed(int code, String msg) {
                    onAdNativeRequestListener.onFail();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
