package com.zhangku.qukandian.adCommon.natives;

import android.content.Context;

import com.ak.android.engine.nav.NativeAd;
import com.ak.android.engine.nav.NativeAdLoaderListener;
import com.ak.android.engine.navbase.NativeAdLoader;
import com.ak.android.shell.AKAD;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.NativeAdInfo;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.interfaces.OnAdNativeRequestListener;
import com.zhangku.qukandian.utils.CommonHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by yuzuoning on 2017/10/26.
 */

public class Ad360Native extends AdRequestBase {
    //                                      信息流小图     信息流大图     信息流小图
    private String[] mAdType = new String[]{"kFkmQSVeiW", "kaPH68gTig", "PkkR7ALEHR"};
    private NativeAdLoader mNativeLoader;
    private int mType = 1;

    public Ad360Native(Context context, @AnnoCon.AdverFrom String from) {
        super(context, from);
    }

    @Override
    public void getAdData(final AdLocationBeans.AdLocationsBean adLocationsBean, final AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean, final OnAdNativeRequestListener onAdNativeRequestListener) {
        if (null == mNativeLoader) {
            mType = new Random().nextInt(3);
            mNativeLoader = AKAD.getNativeAdLoader(mContext, mAdType[mType], new NativeAdLoaderListener() {
                @Override
                public void onAdLoadSuccess(ArrayList<NativeAd> arrayList) {
                    int adlocId = adLocationsBean.getId();//广告位id
                    int index = adLocationsBean.getPageIndex();//广告在列表的位置
                    String belong = adsRuleBean.getBelong();//广告位置 埋点
                    int clickGold = adsRuleBean.getClickGold();//点击金币

                    List<NativeAdInfo> list1 = new ArrayList();
                    for (int i = 0; i < arrayList.size(); i++) {
                        NativeAdInfo bean = new NativeAdInfo();
                        NativeAd response = arrayList.get(i);
                        JSONObject adContent = response.getContent();
                        bean.setAdlocId(adlocId);
                        bean.setOrigin(response);
                        bean.setClickGold(clickGold);
                        bean.setAdsRuleBean(adsRuleBean);
                        bean.setAdFromName(from);
                        bean.setIndex(index);
                        bean.setBelong(belong);
                        bean.setImageUrls(new ArrayList<String>());
                        bean.setCurrentData(CommonHelper.formatTimeYMD(System.currentTimeMillis(), true));
                        bean.setDescription(adContent.optString("desc"));
                        bean.setImageUrl(adContent.optString("contentimg"));
                        bean.setTitle(adContent.optString("title"));
                        list1.add(bean);
                    }
                    onAdNativeRequestListener.onResponse(list1, 2);
                }

                @Override
                public void onAdLoadFailed(int i, String s) {
                    onAdNativeRequestListener.onFail();
                }
            });
        }
        mNativeLoader.loadAds();
    }
}
